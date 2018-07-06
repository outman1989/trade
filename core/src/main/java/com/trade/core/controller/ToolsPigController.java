package com.trade.core.controller;

import com.trade.core.entity.ToolsPigEntity;
import com.trade.core.service.IToolsPigService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收猪工具 controller
 *
 * @author lx
 * @since 2018-07-03 10:51:06
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/toolsPig")
public class ToolsPigController extends BaseController {
    private static final String FN = "收猪工具";
    @Autowired
    private IToolsPigService<ToolsPigEntity> service;
    /**
     * 收猪工具-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/toolsPig/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 收猪工具-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/toolsPig/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 收猪工具-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/toolsPig/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 收猪工具-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/toolsPig/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }

    /**
     * 收猪工具-删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/toolsPig/delete.do
     */
    @RequestMapping(value = "/delete" + BaseConfig.API_SUFFIX)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        super._deleteInIds(request,response, service,FN);
    }
}
