package com.trade.core.controller;

import com.trade.core.entity.ShopEntity;
import com.trade.core.service.IShopService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 店铺 controller
 *
 * @author lx
 * @since 2018-6-13 10:06:14
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/shop")
public class ShopController extends BaseController {
    private static final String FN = "店铺";
    @Autowired
    private IShopService<ShopEntity> service;

    /**
     * 我的店铺
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/shop/myShop.do
     */
    @RequestMapping(value = "/myShop" + BaseConfig.API_SUFFIX)
    public void myShop(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service,"myShop", FN);
    }

    /**
     * 店铺-保存
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/shop/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 店铺-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/shop/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 店铺-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/shop/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 店铺-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/shop/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
