package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.VO.ProductPigVO;
import com.trade.core.entity.VO.SearchPigVO;
import entity.Page;
import entity.ResponseData;

import java.util.List;

/**
 * 猪源 service
 *
 * @author lx
 * @since 2018-6-20 14:39:04
 */
@SuppressWarnings("unused")
public interface IProductPigService<E> {

    ResponseData<Object> publish(JSONObject jo) throws Exception;//猪源-发布猪源

    ResponseData<Object> update(JSONObject jo) throws Exception;//猪源-编辑

    ResponseData<Object> changeState(JSONObject jo) throws Exception;//猪源-修改状态（上下架）

    ResponseData<Page<SearchPigVO>> searchPig(JSONObject jo);//猪源-搜猪源（买家）

    ResponseData<Object> saleDetail(JSONObject jo) throws Exception;//猪源-详情（卖家）

    ResponseData<Object> buyDetail(JSONObject jo) throws Exception;//猪源-详情（买家预订）

    ResponseData<Object> deleteProductPig(JSONObject jo) throws Exception;//猪源-删除

    ResponseData<Object> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<ProductPigVO>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}