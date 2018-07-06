package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.AccountEntity;
import com.trade.core.service.IAccountService;
import constants.BaseConfig;
import constants.SqlConfig;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账号 controller
 *
 * @author lx
 * @since 2018-6-8 11:28:13
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/account")
public class AccountController extends BaseController {
    private static final String FN = "账号";
    @Autowired
    private IAccountService<AccountEntity> service;

    /**
     * 账号-注册
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/register.do
     */
    @PostMapping(value = "/register" + BaseConfig.API_SUFFIX)
    public void register(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            res = service.register(jo, request.getSession());//2、调用注册service
        } catch (Exception e) {
            res.exception(FN + "-注册", jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 账号-登录
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/login.do
     */
    @PostMapping(value = "/login" + BaseConfig.API_SUFFIX)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            res = service.login(jo, request.getSession());//2、调用注册service
        } catch (Exception e) {
            res.exception(FN + "-登录", jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 账号-认证
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/auth.do
     */
    @PostMapping(value = "/auth" + BaseConfig.API_SUFFIX)
    public void auth(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service,"auth", FN);
    }

    /**
     * 账号-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request, response, service, FN);
    }

    /**
     * 账号-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request, response, service, FN);
    }

    /**
     * 账号-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request, response, service, FN);
    }

    /**
     * 账号-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/account/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }

}
