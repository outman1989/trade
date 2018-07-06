package com.trade.core.controller;

import com.trade.core.entity.WalletEntity;
import com.trade.core.service.IWalletService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 钱包 controller
 *
 * @author lx
 * @since 2018-6-12 17:08:44
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/wallet")
public class WalletController extends BaseController {
    private static final String FN = "钱包";
    @Autowired
    private IWalletService<WalletEntity> service;

    /**
     * 钱包-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wallet/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 钱包-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wallet/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 钱包-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/wallet/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
