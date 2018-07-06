package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.SysDictionaryEntity;
import com.trade.core.mapper.ISysDictionaryMapper;
import com.trade.core.service.ISysDictionaryService;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据字典 serviceImpl
 *
 * @author lx
 * @since 2018-6-6 15:14:07
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class SysDictionaryServiceImpl<E> extends BaseServiceImpl implements ISysDictionaryService<E> {
    @Autowired
    private ISysDictionaryMapper mapper;

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        return super._save(mapper, jsonEntity, getQueryOnly(jsonEntity), SysDictionaryEntity.class, null);
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
     * 获取唯一验证查询条件
     *
     * @param jsonEntity 请求参数json实体
     * @return query 查询条件
     */
    private String getQueryOnly(JSONObject jsonEntity) {
        return whereEqual("p_key", jsonEntity) + andEqual("c_key", jsonEntity);
    }

    /**
     * 获取查询条件
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuery(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        query += andEqual("p_key", jo);
        query += andLike("p_value", jo);
        query += andEqual("c_key", jo);
        query += andLike("c_value", jo);
        return query;
    }

    @Override
    public int getCount(String query) {
        return mapper.getCount(query);
    }


    @Override
    @SuppressWarnings("unchecked")
    public E getById(String id) {
        return (E) mapper.getById(Integer.parseInt(id));
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
        return res.success((List<E>) mapper.getBySQL(" c_key,c_value ", getQuery(jo)));
    }

}
