package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.SysLogEntity;
import com.trade.core.mapper.ISysLogMapper;
import com.trade.core.service.ISysLogService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统日志 serviceImpl
 *
 * @author lx
 * @since 2018-6-6 06:06:06
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class SysLogServiceImpl<E> extends BaseServiceImpl implements ISysLogService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private ISysLogMapper mapper;

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, SysLogEntity.class, snowflakeIdUtil);
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, null);
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
        return res.success((List<E>) mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, SqlConfig.DEFAULT_QUERY_LIMIT));
    }

}
