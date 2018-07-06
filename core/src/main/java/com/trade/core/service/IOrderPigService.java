package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 订单 service
 *
 * @author lx
 * @since 2018-06-26 10:37:44
 */
@SuppressWarnings("unused")
public interface IOrderPigService<E> {

    ResponseData<Object> booking(JSONObject jo) throws Exception;//订单--预订下单

    ResponseData<Object> myOrder(JSONObject jo);//订单--我的订单

    ResponseData<Object> cancelOrder(JSONObject jo) throws Exception;//订单--取消订单

    ResponseData<Object> deleteOrder(JSONObject jo) throws Exception;//订单--删除

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}
