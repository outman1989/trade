package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.controller.BaseController;
import com.trade.core.util.SnowflakeIdUtil;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import util.ObjectUtil;
import util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * base serviceImpl
 *
 * @author lx
 * @since 2018-6-7 11:37:04
 */
@SuppressWarnings("unused")
public class BaseServiceImpl<E, T> extends BaseController {
    private final static String WHERE = " WHERE ";
    private final static String SET = " SET ";
    private final static String AND = " AND ";
    private final static String OR = " OR ";
    private final static String EQUAL = " = ";
    private final static String NOT_EQUAL = " != ";
    private final static String MORE_THAN = " >= ";
    private final static String LESS_THAN = " <= ";
    private final static String LIKE = " LIKE ";
    private final static String IN = " IN ";
    private final static String NOT_IN = " NOT IN ";
    private final static String EXPRESSION = " %s %s %s %s ";


    /**
     * 保存
     *
     * @param mapper          业务mapper
     * @param jsonEntity      保存json实体
     * @param query_only      验证唯一查询条件
     * @param clazz           实体类型
     * @param snowflakeIdUtil 全局id生成器
     * @return ResponseData<E> 保存结果
     */
    public ResponseData<E> _save(T mapper, JSONObject jsonEntity, String query_only, Class<E> clazz, SnowflakeIdUtil snowflakeIdUtil) {
        ResponseData<E> res = new ResponseData<>();
        if (ObjectUtil.isEmpty(jsonEntity)) return res.invalidParam();//参数验证
        String mapperMethod = SqlConfig.ADD;
        if (StringUtil.isNotEmpty(jsonEntity.getString("id")) && !jsonEntity.getString("id").equals("0")) {
            //修改
            if (StringUtil.isNotEmpty(query_only)) {
                query_only += andNotEqual("id", jsonEntity);
            }
            mapperMethod = SqlConfig.UPDATE;
        } else {
            //新增
            if (!ObjectUtil.isEmpty(snowflakeIdUtil)) jsonEntity.put("id", snowflakeIdUtil.nextId());
            if (ObjectUtil.haveField(clazz, "createTime") && ObjectUtil.isEmpty(jsonEntity.get("create_time"))) {
                jsonEntity.put("create_time", new Date());
            }
        }
        try {
            //唯一验证
            if (StringUtil.isNotEmpty(query_only)) {
                int resOnly = (int) mapper.getClass().getMethod(SqlConfig.GET_COUNT, String.class).invoke(mapper, query_only);//唯一查询
                if (resOnly > 0) return res.exists();//返回数据已存在
            }
            int res_save = (int) mapper.getClass().getMethod(mapperMethod, clazz).invoke(mapper, jsonEntity.toJavaObject(clazz));//保存
            return res.operateRes(res_save);
        } catch (Exception e) {
            return res.exception(SqlConfig.SAVE_CN, e);
        }
    }

    /**
     * 分页查询
     *
     * @param mapper 业务mapper
     * @param jo     请求json实体
     * @param query  查询条件
     * @return ResponseData<Page>分页数据
     */
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> _getPage(T mapper, JSONObject jo, String query) {
        return _getPage(mapper, jo, query, null);
    }

    /**
     * 分页查询
     *
     * @param mapper 业务mapper
     * @param jo     请求json实体
     * @param query  查询条件
     * @param order  排序条件
     * @return ResponseData<Page> 分页数据
     */
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> _getPage(T mapper, JSONObject jo, String query, String order) {
        ResponseData<Page<Object>> res = new ResponseData<>();
        try {
            int total = (int) mapper.getClass().getMethod(SqlConfig.GET_COUNT, String.class).invoke(mapper, query);//查询条数
            List<Object> list = new ArrayList<>();
            if (total > 0) {
                list = (List<Object>) mapper.getClass().getMethod(SqlConfig.GET_PAGE, Integer.class, Integer.class, String.class, String.class, String.class).invoke(mapper, SqlConfig.getPageNumber(jo), SqlConfig.getPageSize(jo), SqlConfig.DEFAULT_QUERY_FIELDS, query, order);//分页查询
            }
            return res.success(new Page(total, list));
        } catch (Exception e) {
            return res.exception(SqlConfig.GET_PAGE_CN, e);
        }
    }

    /**
     * 拼接sql条件where =
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String whereEqual(String key, String value) {
        return appendSql(key, value, WHERE, EQUAL);
    }

    /**
     * 拼接sql条件where =
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String whereEqual(String key, JSONObject jo) {
        return appendSql(key, jo, WHERE, EQUAL);
    }

    /**
     * 拼接sql条件and =
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String andEqual(String key, String value) {
        return appendSql(key, value, AND, EQUAL);
    }

    /**
     * 拼接sql条件and =
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andEqual(String key, JSONObject jo) {
        return appendSql(key, jo, AND, EQUAL);
    }

    /**
     * 拼接sql条件and >=
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String andMoreThan(String key, String value) {
        return appendSql(key, value, AND, MORE_THAN);
    }

    /**
     * 拼接sql条件and >=
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andMoreThan(String key, JSONObject jo) {
        return appendSql(key, jo, AND, MORE_THAN);
    }

    /**
     * 拼接sql条件and <=
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String andLessThan(String key, String value) {
        return appendSql(key, value, AND, MORE_THAN);
    }

    /**
     * 拼接sql条件and <=
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andLessThan(String key, JSONObject jo) {
        return appendSql(key, jo, AND, MORE_THAN);
    }

    /**
     * 拼接sql set =
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String set(String key, String value) {
        return appendSql(key, value, SET, EQUAL);
    }

    /**
     * 拼接sql set =
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String set(String key, JSONObject jo) {
        return appendSql(key, jo, SET, EQUAL);
    }

    /**
     * 拼接sql条件and in
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String andIn(String key, String value) {
        return appendSql(key, value, AND, IN);
    }

    /**
     * 拼接sql条件and in
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andIn(String key, JSONObject jo) {
        return appendSql(key, jo, AND, IN);
    }

    /**
     * 拼接sql条件where in
     *
     * @param key   条件
     * @param value 值
     * @return sql
     */
    static String whereIn(String key, String value) {
        return appendSql(key, value, WHERE, IN);
    }

    /**
     * 拼接sql条件where in
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String whereIn(String key, JSONObject jo) {
        return appendSql(key, jo, WHERE, IN);
    }

    /**
     * 拼接sql条件and =
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andNotEqual(String key, JSONObject jo) {
        return appendSql(key, jo, AND, NOT_EQUAL);
    }

    /**
     * 拼接sql条件or =
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String orEqual(String key, JSONObject jo) {
        return appendSql(key, jo, OR, EQUAL);
    }

    /**
     * 拼接sql条件and like
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String andLike(String key, JSONObject jo) {
        return appendSql(key, jo, AND, LIKE);
    }

    /**
     * 拼接sql条件or like
     *
     * @param key 条件
     * @param jo  请求json
     * @return sql
     */
    static String orLike(String key, JSONObject jo) {
        return appendSql(key, jo, OR, LIKE);
    }

    /**
     * 拼接sql条件
     *
     * @param key   条件
     * @param jo    请求json
     * @param type  [where,and,or]
     * @param match [=,!=,like,in,not in]
     * @return sql
     */
    static String appendSql(String key, JSONObject jo, String type, String match) {
        if (ObjectUtil.isEmpty(jo)) return "";
        return appendSql(key, jo.getString(key), type, match);
    }

    /**
     * 拼接sql条件
     *
     * @param key   条件
     * @param value 值
     * @param type  [where,and,or]
     * @param match [=,!=,like,in,not in]
     * @return sql
     */
    static String appendSql(String key, String value, String type, String match) {
        String sql = "";
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value) || StringUtil.isEmpty(type) || StringUtil.isEmpty(match)) {
            return sql;
        }
        switch (match) {
            case LIKE:
                sql = String.format(EXPRESSION, type, key, match, ("'%" + value + "%'"));
                break;
            case IN:
                sql = String.format(EXPRESSION, type, key, match, ("('" + value.replace(",","','") + "')"));
                break;
            case NOT_IN:
                sql = String.format(EXPRESSION, type, key, match, ("('" + value.replace(",","','") + "')"));
                break;
            default:
                sql = String.format(EXPRESSION, type, key, match, ("'" + value + "'"));
                break;
        }
        return sql;
    }
}
