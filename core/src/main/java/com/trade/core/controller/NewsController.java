package com.trade.core.controller;

import com.trade.core.entity.NewsEntity;
import com.trade.core.service.INewsService;
import constants.BaseConfig;
import constants.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 消息 controller
 *
 * @author lx
 * @since 2018-07-03 17:08:04
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/news")
public class NewsController extends BaseController {
    private static final String FN = "消息";
    @Autowired
    private INewsService<NewsEntity> service;

    /**
     * 消息-我的消息
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/myNews.do
     */
    @RequestMapping(value = "/myNews" + BaseConfig.API_SUFFIX)
    public void myNews(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 消息-已读（修改消息为已读）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/read.do
     */
    @PostMapping(value = "/read" + BaseConfig.API_SUFFIX)
    public void read(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request,response,service,"read",FN);
    }

    /**
     * 消息-清除（修改消息为清除，清除后的消息不可见）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/clean.do
     */
    @PostMapping(value = "/clean" + BaseConfig.API_SUFFIX)
    public void clean(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request,response,service,"clean",FN);
    }

    /**
     * 消息-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 消息-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 消息-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 消息-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/news/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
