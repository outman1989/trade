package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 公告 service
 *
 * @author lx
 * @since 2018-07-03 17:03:44
 */
public interface INoticeService<E> {
    ResponseData<Object> publish(JSONObject jo) throws Exception;//公告-发布公告

    ResponseData<Object> update(JSONObject jo);//公告-编辑

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}
