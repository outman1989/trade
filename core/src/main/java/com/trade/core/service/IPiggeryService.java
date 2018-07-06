package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 猪场 service
 *
 * @author lx
 * @since 2018-6-13 11:24:48
 */
@SuppressWarnings("unused")
public interface IPiggeryService<E> {

    ResponseData<Object> add(JSONObject jo) throws Exception;//猪场--添加

    ResponseData<Object> setTop(JSONObject jo) throws Exception;//猪场--置顶

    ResponseData<Object> deletePiggery(JSONObject jo) throws Exception;//猪场--删除

    ResponseData<Object> detail(JSONObject jo);//猪场--详情

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}