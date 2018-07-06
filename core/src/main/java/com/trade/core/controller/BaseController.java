package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import constants.BaseConfig;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.SessionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import util.LogUtil;
import util.ObjectUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * controller基类
 *
 * @author lx
 * @since 2018-5-30 11:06:46
 */
@SuppressWarnings("unused")
@Component
@Slf4j
public class BaseController<E> {

    /**
     * 获取参数（getIsParams）
     *
     * @param request 请求
     * @author lx
     */
    public JSONObject getIsParams(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        InputStream is;
        try {
            is = request.getInputStream();
            String str = IOUtils.toString(is, "utf-8");
            if (StringUtil.isNotEmpty(str)) {
                obj = JSON.parseObject(str);//请求参数
            }
        } catch (Exception e) {
            LogUtil.error("获取参数（getIsParams）", e);
        }
        return obj;
    }

    /**
     * 获取扁平参数（get/post）
     *
     * @param request 请求
     * @return JSONObject 参数集合
     * @author lx
     * @since 2017年9月25日 下午1:35:21
     */
    public JSONObject getFlatParams(HttpServletRequest request) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            Map<String, Object> mapParams = new HashMap<>();
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                mapParams.put(key, values[0]);
            }
            return new JSONObject(mapParams);
        } catch (Exception e) {
            LogUtil.error("获取扁平参数", e);
        }
        return new JSONObject();
    }

    /**
     * 返回数据--lx--2018-4-18 17:05:19
     *
     * @param request  请求
     * @param response 返回
     * @param data     返回数据
     */
    public void returnData(HttpServletRequest request, HttpServletResponse response, String data) {
        try {
            String encoding = request.getHeader("Accept-Encoding");
            //判断请求是否压缩
            if (encoding.toLowerCase().contains("gzip")) {
                byte[] datas = data.getBytes(BaseConfig.ENCODING);
                byte[] gzipData = gzip(datas);
                response.setContentType(BaseConfig.CONTENT_TYPE_DEFAULT);//默认内容类型
                response.setHeader("Content-Encoding", "gzip");
                response.setContentLength(gzipData.length);
                OutputStream out = response.getOutputStream();
                out.write(gzipData);
                out.flush();
                out.close();
            } else {
                response.setContentType(BaseConfig.CONTENT_TYPE_JSON);//json格式内容类型
                PrintWriter pw = response.getWriter();
                pw.print(data);
                pw.close();
            }
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            } catch (Exception e1) {
                LogUtil.error("返回数据", e1);
            }
        }
    }

    /***
     * GZIP 压缩 数据
     * @param data 压缩前数据
     * @return byte[] 压缩后数据
     */
    private byte[] gzip(byte[] data) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 压缩
            GZIPOutputStream gos = new GZIPOutputStream(baos);
            gos.write(data, 0, data.length);
            gos.finish();
            byte[] output = baos.toByteArray();
            baos.flush();
            baos.close();
            return output;
        } catch (Exception e) {
            LogUtil.error("GZIP 压缩 数据", e);
        }
        return data;
    }

    /**
     * 获取客户端请求真实IP地址
     *
     * @param request 请求
     * @return String 请求ip
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip是真实ip
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 【Session】获取账号信息
     *
     * @param request 请求实体
     * @throws SessionException 抛出的异常
     */
    @SuppressWarnings("unchecked")
    public <S> S getSA(HttpServletRequest request) throws SessionException {
        S object = (S) request.getSession().getAttribute(BaseConfig.SESSION_ACCOUNT);
        if (ObjectUtil.isEmpty(object)) {
            throw new SessionException("请求超时，请重新登录");
        } else {
            return object;
        }
    }

    /**
     * 【Session】保存账号信息
     *
     * @param request 请求实体
     * @param o       保存内容
     */
    public <S> void setSA(HttpServletRequest request, S o) {
        request.getSession().setAttribute(BaseConfig.SESSION_ACCOUNT, o);//session 保存内容
        request.getSession().setMaxInactiveInterval(BaseConfig.SESSION_ACCOUNT_LIMIT);//超时时间
    }

    /**
     * 【Session】账号信息鉴权
     *
     * @param request 请求实体
     * @throws SessionException 抛出的异常
     */
    public void authSA(HttpServletRequest request) throws SessionException {
        if (ObjectUtil.isEmpty(request.getSession().getAttribute(BaseConfig.SESSION_ACCOUNT))) {
//			throw new SessionException("请求超时，请重新登录");
        }
    }

    /**
     * 执行业务层
     *
     * @param request       请求
     * @param response      响应
     * @param service       业务层
     * @param serviceMethod 业务层调用方法
     * @param FN            方法名称
     * @author lx
     * @since 2018-6-8 16:53:27
     */
    @SuppressWarnings("unchecked")
    public <S> void _doService(HttpServletRequest request, HttpServletResponse response, S service, String serviceMethod, String FN) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            jo.put(BaseConfig.SESSION_ACCOUNT, getSA(request));//session 验证&&参数处理
            res = (ResponseData<Object>)service.getClass().getMethod(serviceMethod, JSONObject.class).invoke(service, jo);
        } catch (Exception e) {
            res.exception(FN + "-" + serviceMethod, jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 保存
     *
     * @param request  请求
     * @param response 响应
     * @param service  业务层
     * @param FN       方法名称
     * @author lx
     * @since 2018-6-5 16:03:45
     */
    @SuppressWarnings("unchecked")
    public <S> void _save(HttpServletRequest request, HttpServletResponse response, S service, String FN) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            authSA(request);//2、session验证
            res = (ResponseData<Object>) service.getClass().getMethod(SqlConfig.SAVE, JSONObject.class).invoke(service, jo);
        } catch (Exception e) {
            res.exception(FN + SqlConfig.SAVE_CN, jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 分页查询
     *
     * @param request  请求
     * @param response 响应
     * @param service  业务层
     * @param FN       方法名称
     * @author lx
     * @since 2018-6-5 16:03:45
     */
    @SuppressWarnings("unchecked")
    public <S> void _getPage(HttpServletRequest request, HttpServletResponse response, S service, String FN) {
        ResponseData<Page<Object>> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            res = (ResponseData<Page<Object>>) service.getClass().getMethod(SqlConfig.GET_PAGE, JSONObject.class).invoke(service, jo);
        } catch (Exception e) {
            res.exception(FN + SqlConfig.GET_PAGE_CN, jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 详情询查
     *
     * @param request  请求
     * @param response 响应
     * @param service  业务层
     * @param FN       方法名称
     * @author lx
     * @since 2018-6-5 16:03:45
     */
    public <S> void _detail(HttpServletRequest request, HttpServletResponse response, S service, String FN) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            String id = jo.getString("id");
            if (StringUtil.isEmpty(id)) {
                res.fail();
                return;
            }
            res.success(service.getClass().getMethod(SqlConfig.GET_BY_ID, String.class).invoke(service, id));
        } catch (Exception e) {
            res.exception(FN + SqlConfig.DETAIL_CN, jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 删除
     *
     * @param request      请求
     * @param response     响应
     * @param service      业务层
     * @param FN 方法名称
     * @author lx
     * @since 2018-6-5 16:23:07
     */
    public <S> void _deleteInIds(HttpServletRequest request, HttpServletResponse response, S service, String FN) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = getIsParams(request);//1、获取参数
        try {
            authSA(request);//2、session验证
            String ids = jo.getString("ids");//待删除id集合
            if (StringUtil.isEmpty(ids)) {
                res.fail();
                return;
            }
            res.operateRes((int) service.getClass().getMethod(SqlConfig.DELETE, String.class).invoke(service, ids));
        } catch (Exception e) {
            res.exception(FN + SqlConfig.DELETE_CN, jo, e);
        } finally {
            returnData(request, response, JSON.toJSONString(res));
        }
    }

}
