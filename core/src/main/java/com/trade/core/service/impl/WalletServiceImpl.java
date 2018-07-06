package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.WalletEntity;
import com.trade.core.mapper.IWalletMapper;
import com.trade.core.service.IWalletService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 钱包 serviceImpl
 *
 * @author lx
 * @since 2018-6-12 17:01:05
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class WalletServiceImpl<E> extends BaseServiceImpl implements IWalletService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IWalletMapper mapper;

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        return super._save(mapper, jsonEntity, whereEqual("account_id", jsonEntity), WalletEntity.class, snowflakeIdUtil);
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
        query += andEqual("account_id", jo);
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
     * @param jo 请求json
     * @return 响应结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<List<E>> getBySQL(JSONObject jo) {
        ResponseData<List<E>> res = new ResponseData<>();
        return res.success((List<E>) mapper.getBySQL(" c_key,c_value ", getQuery(jo)));
    }

}
