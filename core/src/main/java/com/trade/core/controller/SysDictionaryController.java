package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.SysDictionaryEntity;
import com.trade.core.mapper.ISysDictionaryMapper;
import com.trade.core.service.ISysDictionaryService;
import com.trade.core.util.ExcelUtil;
import constants.BaseConfig;
import constants.SqlConfig;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.LogUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据字典 controller
 *
 * @author lx
 * @since 2018-6-5 14:51:39
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/sysDictionary")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class SysDictionaryController extends BaseController {
    private static final String FN = "数据字典";
    @Autowired
    private ISysDictionaryService<SysDictionaryEntity> service;
    @Autowired
    private ISysDictionaryMapper mapper;

    /**
     * 数据字典-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysDictionary/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request, response, service, FN);
    }

    /**
     * 数据字典-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysDictionary/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request, response, service, FN);
    }

    /**
     * 数据字典-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysDictionary/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request, response, service, FN);
    }

    /**
     * 数据字典-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysDictionary/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }

    /**
     * 数据字典-下载报表
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/sysDictionary/getReport.do
     * 访问限制：同步锁
     */
    @RequestMapping(value = "/getReport" + BaseConfig.API_SUFFIX)
    public synchronized void getReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<SysDictionaryEntity> list = mapper.getBySQL(" * ", "");
            ExcelUtil.exportExcel(list, null, "Sheet1", SysDictionaryEntity.class, "数据字典.xls", response);
        } catch (Exception e) {
            LogUtil.error(FN + "-下载报表", e);
            returnData(request, response, JSON.toJSONString(new ResponseData<>().fail()));
        }
    }

}
