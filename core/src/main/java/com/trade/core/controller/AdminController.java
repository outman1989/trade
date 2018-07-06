package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.util.GenerateUtil;
import entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.StringUtil;
import util.ValidateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lx
 * @since 2018-07-03 16:14:35
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private static final String FN = "管理员接口";
    @Autowired
    private Environment env;

    /**
     * 生成代码
     * 本地接口地址：http://127.0.0.1:8660/api/trade/admin/generate?en=Notice&cn=公告
     * 访问限制：同步锁
     */
    @RequestMapping(value = "/generate")
    public synchronized void generate(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getFlatParams(request);//1、获取参数
        try {
            if ("dev".equals(env.getProperty("cfg.env"))) {
                String en = ValidateUtil.requiredSingle(jo, "en");
                String cn = ValidateUtil.requiredSingle(jo, "cn");
                ValidateUtil.requiredSingle(jo, "en");
                String generateRes = GenerateUtil.generateEntity(en);//生成实体类
                if (StringUtil.isNotEmpty(generateRes)) {
                    res.fail(generateRes);
                    return;
                }
                generateRes = GenerateUtil.makeBaseCode(en, cn);//生成基础代码：sqlbuilder, mapper, service, serviceImpl, controller
                if (StringUtil.isNotEmpty(generateRes)) {
                    res.fail(generateRes);
                    return;
                }
                res.success();
            } else {
                res.fail();
            }
        } catch (Exception e) {
            res.exception(FN + "-generate", jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }
}
