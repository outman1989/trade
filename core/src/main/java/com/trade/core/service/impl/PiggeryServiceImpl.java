package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.PriorityEnum;
import com.trade.core.Enum.ProductStateEnum;
import com.trade.core.Enum.SysStateEnum;
import com.trade.core.entity.PiggeryEntity;
import com.trade.core.entity.PiggeryIndexEntity;
import com.trade.core.entity.ShopEntity;
import com.trade.core.mapper.IPiggeryIndexMapper;
import com.trade.core.mapper.IPiggeryMapper;
import com.trade.core.mapper.IProductPigMapper;
import com.trade.core.mapper.IShopMapper;
import com.trade.core.service.IPiggeryService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.ResponseEnum;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ListUtil;
import util.ObjectUtil;
import util.ValidateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 猪场 serviceImpl
 *
 * @author lx
 * @since 2018-6-13 11:09:41
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class PiggeryServiceImpl<E> extends BaseServiceImpl implements IPiggeryService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IPiggeryMapper mapper;
    @Autowired
    private IPiggeryIndexMapper piggeryIndexMapper;
    @Autowired
    private IShopMapper shopMapper;
    @Autowired
    private IProductPigMapper productPigMapper;

    /**
     * 猪场--添加
     */
    @Transactional
    @Override
    public ResponseData<Object> add(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //【参数验证】
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");//账号id
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        //非空验证:名称、所在省、所在市、所在区县、详细地址、联系人、联系人手机号、（可空参数：猪场图片 piggery_img 、营业执照图片 business_license、企业许可证图片 allow_license）
        ValidateUtil.required(jsonEntity, "name", "province", "city", "area", "address", "contacts", "contact_phone");
        PiggeryEntity entity = jsonEntity.toJavaObject(PiggeryEntity.class);//获取猪场信息
        //【查询店铺信息】
        List<ShopEntity> resShopList = shopMapper.getBySQL("id", whereEqual("account_id", accountId));
        if (ListUtil.isEmpty(resShopList)) return res.fail("查询店铺失败");
        //【新增猪场】
        String piggeryId = snowflakeIdUtil.nextId();
        mapper.add(entity.init(piggeryId, accountId, resShopList.get(0).getId()));//新增猪场
        //【新增猪场指标】
        piggeryIndexMapper.add(new PiggeryIndexEntity(snowflakeIdUtil.nextId(), accountId, resShopList.get(0).getId(), piggeryId));//新增猪场指标
        return res.success();
    }

    /**
     * 猪场--置顶
     */
    @Transactional
    @Override
    public ResponseData<Object> setTop(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        String id = ValidateUtil.requiredSingle(jo, "id");//猪场id
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");//账号id
        int resUpdateDefault = mapper.updateBySQL(String.format(" SET priority = '%s' WHERE account_id = '%s'", PriorityEnum.默认.getCode(), accountId));//把账号下所有猪场显示优先级调为--默认
        if (resUpdateDefault <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        int resUpdateSetTop = mapper.updateBySQL(String.format(" SET priority = '%s' WHERE id = '%s'", PriorityEnum.置顶.getCode(), id));//选择的猪场优先级调为--置顶
        if (resUpdateSetTop <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        return res.success();
    }

    /**
     * 猪场--删除
     */
    @Transactional
    @Override
    public ResponseData<Object> deletePiggery(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、参数验证
        String ids = ValidateUtil.requiredSingle(jo, "ids");//猪场id
        //2、查询该猪场的在售猪源
        int onSale = productPigMapper.getCount(whereIn("piggery_id", ids) + andEqual("product_state", ProductStateEnum.上架.getCode().toString()));
        if (onSale > 0) return res.fail("该猪场有在售猪源，需要先删除在售猪源，再执行本操作");
        //3、修改猪场状态为已删除
        return res.operateRes(mapper.updateBySQL(set("sys_state", SysStateEnum.已删除.getCode().toString()) + whereIn("id", ids)));
    }

    /**
     * 猪场--详情
     */
    @Transactional
    @Override
    public ResponseData<Object> detail(JSONObject jo) {
        ResponseData<Object> res = new ResponseData<>();
        Map<String, Object> map = new HashMap<>();
        //【猪场基础信息】
        PiggeryEntity piggery = mapper.getById(jo.getString("id"));
        if (ObjectUtil.isEmpty(piggery)) return res.fail();
        map.put("piggery", piggery);
        //【猪场指标】
        List<PiggeryIndexEntity> piggeryIndexList = piggeryIndexMapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, whereIn("piggery_id", piggery.getId()));
        if (ListUtil.isEmpty(piggeryIndexList)) return res.fail();
        map.put("piggeryIndex", piggeryIndexList.get(0));
        return res.success(map);
    }


    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, PiggeryEntity.class, snowflakeIdUtil);
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")

    public ResponseData<Page<Object>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, getQuery(jo), " ORDER BY priority DESC, id DESC ");
    }

    /**
     * 获取查询条件
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuery(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        query += (andEqual("account_id", jo));
        query += (andEqual("shop_id", jo));
        return query;
    }

    @Override
    public int getCount(String query) {
        return mapper.getCount(query);
    }


    @Override
    @SuppressWarnings("unchecked")
    public E getById(String id) {
        return (E) mapper.getById(id);
    }

    /**
     * 根据条件查询集合（默认限制每次最大查100条）
     *
     * @param jo 请求json
     * @return 响应结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<List<E>> getBySQL(JSONObject jo) {
        ResponseData<List<E>> res = new ResponseData<>();
        return res.success((List<E>) mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, getQuery(jo) + SqlConfig.DEFAULT_QUERY_LIMIT));
    }

}
