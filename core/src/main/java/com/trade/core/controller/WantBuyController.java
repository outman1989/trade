package com.trade.core.controller;

import com.trade.core.entity.WantBuyEntity;
import com.trade.core.service.IWantBuyService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 求购 controller
 *
 * @author lx
 * @since 2018-06-29 17:42:15
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/wantBuy")
public class WantBuyController extends BaseController {
    private static final String FN = "求购";
    @Autowired
    private IWantBuyService<WantBuyEntity> service;
    /**
     * 求购-发布求购
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/save.do
     */
    @PostMapping(value = "/publish" + BaseConfig.API_SUFFIX)
    public void publish(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request,response,service,"save",FN);
    }

    /**
     * 求购-编辑
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/update.do
     */
    @PostMapping(value = "/update" + BaseConfig.API_SUFFIX)
    public void update(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request,response,service,"save",FN);
    }

    /**
     * 求购-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 求购-我的求购
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/myWant.do
     */
    @RequestMapping(value = "/myWant" + BaseConfig.API_SUFFIX)
    public void myWant(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 求购-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 求购-删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/delete.do
     */
    @RequestMapping(value = "/delete" + BaseConfig.API_SUFFIX)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        super._deleteInIds(request,response, service,FN);
    }

    /**
     * 求购-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 求购-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wantBuy/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
