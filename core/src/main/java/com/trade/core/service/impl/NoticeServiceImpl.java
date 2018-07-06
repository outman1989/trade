package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.AccountEntity;
import com.trade.core.entity.NoticeEntity;
import com.trade.core.entity.ProductLogEntity;
import com.trade.core.mapper.INoticeMapper;
import com.trade.core.service.INoticeService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.BaseConfig;
import constants.ResponseEnum;
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
 * 公告 serviceImpl
 *
 * @author lx
 * @since 2018-07-03 17:03:44
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class NoticeServiceImpl<E> extends BaseServiceImpl implements INoticeService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private INoticeMapper mapper;

    /**
     * 公告-发布公告
     */
    @Transactional
    @Override
    public ResponseData<Object> publish(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【参数获取、验证】
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        ValidateUtil.required(jsonEntity, "title", "content");//非空验证:公告标题、公告内容
        NoticeEntity entity = jsonEntity.toJavaObject(NoticeEntity.class);//获取公告信息
        //2、【新增公告】
        mapper.add(entity.init(snowflakeIdUtil.nextId(), account));
        return res.success();
    }

    /**
     * 公告-编辑
     */
    @Transactional
    @Override
    public ResponseData<Object> update(JSONObject jo) {
        ResponseData<Object> res = new ResponseData<>();
        //1、【参数获取、验证】
        NoticeEntity entity = jo.getJSONObject(SqlConfig.PARAM_ENTITY).toJavaObject(NoticeEntity.class);//获取公告信息
        //2、【编辑公告】
        return res.operateRes(mapper.update(entity));
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, NoticeEntity.class, snowflakeIdUtil);
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
        return SqlConfig.DEFAULT_QUERY_CONDITION;
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
