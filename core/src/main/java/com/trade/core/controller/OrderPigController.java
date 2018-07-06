package com.trade.core.controller;

import com.trade.core.entity.OrderPigEntity;
import com.trade.core.service.IOrderPigService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单 controller
 *
 * @author lx
 * @since 2018-06-26 10:37:44
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/orderPig")
public class OrderPigController extends BaseController {
    private static final String FN = "订单";
    @Autowired
    private IOrderPigService<OrderPigEntity> service;

    /**
     * 订单-预订下单
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/booking.do
     */
    @PostMapping(value = "/booking" + BaseConfig.API_SUFFIX)
    public void booking(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "booking", FN);
    }

    /**
     * 订单--我的订单
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/myOrder.do
     */
    @RequestMapping(value = "/myOrder" + BaseConfig.API_SUFFIX)
    public void myOrder(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "myOrder", FN);
    }

    /**
     * 订单--取消订单
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/cancelOrder.do
     */
    @PostMapping(value = "/cancelOrder" + BaseConfig.API_SUFFIX)
    public void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "cancelOrder", FN);
    }

    /**
     * 订单--删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/deleteOrder.do
     */
    @PostMapping(value = "/deleteOrder" + BaseConfig.API_SUFFIX)
    public void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "deleteOrder", FN);
    }

    /**
     * 订单-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 订单-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 订单-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 订单-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/orderPig/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
