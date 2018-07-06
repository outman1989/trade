package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 消息 service
 *
 * @author lx
 * @since 2018-07-03 17:08:04
 */
@SuppressWarnings("unused")
public interface INewsService<E> {

    ResponseData<Object> read(JSONObject jo) throws Exception;//消息-已读（修改消息为已读）

    ResponseData<Object> clean(JSONObject jo) throws Exception;//消息-清除（修改消息为清除，清除后的消息不可见）

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}
