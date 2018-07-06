package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.ShopEntity;
import com.trade.core.entity.VO.ShopVO;
import com.trade.core.mapper.IShopMapper;
import com.trade.core.service.IShopService;
import com.trade.core.util.O2OUtil;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ValidateUtil;

import java.util.List;

/**
 * 店铺 serviceImpl
 *
 * @author lx
 * @since 2018-6-13 10:05:48
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class ShopServiceImpl<E> extends BaseServiceImpl implements IShopService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IShopMapper mapper;

    @Override
    public ResponseData<Object> myShop(JSONObject jo) throws CustomException {
        ResponseData<Object> res = new ResponseData<>();
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");//账号id
        List<ShopEntity> list = mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, whereEqual("account_id", accountId));
        return res.success(O2OUtil.copyObject(list.get(0), ShopVO.class));
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, ShopEntity.class, snowflakeIdUtil);
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, getQuery(jo));
    }

    /**
     * 获取查询条件
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuery(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        query += (andLike("name", jo));
        query += (andEqual("auth_type", jo));
        query += (andEqual("shop_state", jo));
        query += (andEqual("account_id", jo));
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
