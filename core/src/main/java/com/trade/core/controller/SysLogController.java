package com.trade.core.controller;

import com.trade.core.entity.SysLogEntity;
import com.trade.core.service.ISysLogService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 controller
 *
 * @author lx
 * @since 2018-6-6 06:06:06
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/sysLog")
public class SysLogController extends BaseController {
    private static final String FN = "系统日志";
    @Autowired
    private ISysLogService<SysLogEntity> service;
    /**
     * 系统日志-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysLog/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 系统日志-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysLog/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 系统日志-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysLog/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 系统日志-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysLog/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
