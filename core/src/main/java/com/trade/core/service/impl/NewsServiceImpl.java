package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.NewsStateEnum;
import com.trade.core.entity.NewsEntity;
import com.trade.core.mapper.INewsMapper;
import com.trade.core.service.INewsService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ValidateUtil;

import java.util.List;

/**
 * 消息 serviceImpl
 *
 * @author lx
 * @since 2018-07-03 17:08:04
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class NewsServiceImpl<E> extends BaseServiceImpl implements INewsService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private INewsMapper mapper;

    /**
     * 消息-（修改消息为已读）
     */
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> read(JSONObject jo) throws Exception {
        String id = ValidateUtil.requiredSingle(jo, "id");
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");
        ResponseData<Object> res = new ResponseData<>();
        return res.operateRes(mapper.updateBySQL(set("news_state", NewsStateEnum.已读.getCode().toString()) + whereEqual("id", id) + andEqual("accepter_account_id", accountId)));
    }

    /**
     * 消息-（修改消息为清除，清除后的消息不可见）
     */
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> clean(JSONObject jo) throws Exception {
        String id = ValidateUtil.requiredSingle(jo, "id");
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");
        ResponseData<Object> res = new ResponseData<>();
        return res.operateRes(mapper.updateBySQL(set("news_state", NewsStateEnum.清除.getCode().toString()) + whereEqual("id", id) + andEqual("accepter_account_id", accountId)));
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, NewsEntity.class, snowflakeIdUtil);
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, whereEqual("accepter_account_id", jo.getString("account_id")));
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
