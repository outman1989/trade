package com.trade.core.service;

import com.alibaba.fastjson.JSONObject;
import entity.Page;
import entity.ResponseData;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 账号 service
 *
 * @author lx
 * @since 2018-6-7 16:31:05
 */
@SuppressWarnings({"unused"})
public interface IAccountService<E> {

    ResponseData<Object> register(JSONObject jo, HttpSession session) throws Exception;//注册

    ResponseData<Object> login(JSONObject jo, HttpSession session) throws Exception;//登录

    ResponseData<Object> auth(JSONObject jo) throws Exception;//认证

    ResponseData<E> save(JSONObject jo);//保存

    int delete(String ids);//删除

    E getById(String id);//根据id查询

    ResponseData<Page<Object>> getPage(JSONObject jo);//分页查询

    int getCount(String query);//查询条数

    ResponseData<List<E>> getBySQL(JSONObject jo);//根据条件查询

}