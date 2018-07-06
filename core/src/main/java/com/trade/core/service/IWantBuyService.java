package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 求购 service
 *
 * @author lx
 * @since 2018-06-29 17:42:15
 */
public interface IWantBuyService<E> {

    ResponseData<Object> save(JSONObject jo)  throws Exception;//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}
