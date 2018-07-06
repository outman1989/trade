package com.trade.core.controller;

import com.trade.core.entity.PiggeryIndexEntity;
import com.trade.core.service.IPiggeryIndexService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 猪场指标 controller
 *
 * @author lx
 * @since 2018-06-29 12:07:23
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/piggeryIndex")
public class PiggeryIndexController extends BaseController {
    private static final String FN = "猪场指标";
    @Autowired
    private IPiggeryIndexService<PiggeryIndexEntity> service;
    /**
     * 猪场指标-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggeryIndex/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 猪场指标-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggeryIndex/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 猪场指标-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggeryIndex/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 猪场指标-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggeryIndex/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
