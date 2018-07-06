package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;

import java.util.List;

/**
 * 店铺 service
 *
 * @author lx
 * @since 2018-6-13 10:05:35
 */
@SuppressWarnings({"unused"})
public interface IShopService<E> {

    ResponseData<Object> myShop(JSONObject jo) throws CustomException;//我的店铺

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}