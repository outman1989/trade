package com.trade.core.controller;

import com.trade.core.entity.ProductLogEntity;
import com.trade.core.service.IProductLogService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 猪源日志 controller
 *
 * @author lx
 * @since 2018-06-28 17:48:08
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/productLog")
public class ProductLogController extends BaseController {
    private static final String FN = "猪源日志";
    @Autowired
    private IProductLogService<ProductLogEntity> service;
    /**
     * 猪源日志-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productLog/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 猪源日志-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productLog/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 猪源日志-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productLog/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 猪源日志-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productLog/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
