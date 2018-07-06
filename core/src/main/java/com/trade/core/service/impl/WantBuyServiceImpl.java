package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.AccountEntity;
import com.trade.core.entity.WantBuyEntity;
import com.trade.core.mapper.IWantBuyMapper;
import com.trade.core.service.IWantBuyService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.BaseConfig;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ValidateUtil;

import java.util.List;

/**
 * 求购 serviceImpl
 *
 * @author lx
 * @since 2018-06-29 17:42:15
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class WantBuyServiceImpl<E> extends BaseServiceImpl implements IWantBuyService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IWantBuyMapper mapper;

    /**
     * 求购--保存
     */
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) throws Exception {
        //【参数、验证】
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        ValidateUtil.required(jsonEntity, "product_category", "product_variety", "min_weight", "max_weight", "delivery_type", "buy_num", "unit_price", "valid_date", "province", "city", "area", "buy_explain", "contacts", "contact_phone", "address");
        jsonEntity.put("account_id", account.getId());
        jsonEntity.put("price_unit", "生猪".equals(jsonEntity.getString("product_category")) ? "元/公斤" : "元/头");//价格单位（单位：生猪 元/公斤  仔猪or种猪 元/头）
        return super._save(mapper, jsonEntity, null, WantBuyEntity.class, snowflakeIdUtil);
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
        String sql = SqlConfig.DEFAULT_QUERY_CONDITION;
        sql += andEqual("product_category",jo);
        sql += andEqual("product_variety",jo);
        sql += andEqual("province",jo);
        sql += andEqual("city",jo);
        sql += andEqual("area",jo);
        return sql;
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
