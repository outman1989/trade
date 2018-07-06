package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.AuthTypeEnum;
import com.trade.core.entity.*;
import com.trade.core.entity.VO.AccountVO;
import com.trade.core.mapper.*;
import com.trade.core.service.IAccountService;
import com.trade.core.util.O2OUtil;
import com.trade.core.util.SnowflakeIdUtil;
import constants.BaseConfig;
import constants.ResponseEnum;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 账号 serviceImpl
 *
 * @author lx
 * @since 2018-6-6 11:47:38
 */
@Service
@Slf4j
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class AccountServiceImpl<E> extends BaseServiceImpl implements IAccountService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IAccountMapper mapper;
    @Autowired
    private IWalletMapper walletMapper;
    @Autowired
    private IShopMapper shopMapper;
    @Autowired
    private IPiggeryMapper piggeryMapper;
    @Autowired
    private IPiggeryIndexMapper piggeryIndexMapper;

    /**
     * 账号-注册
     *
     * @param jo 请求json实体
     * @return ResponseData<Object> 相应数据
     */
    @Transactional
    @Override
    public ResponseData<Object> register(JSONObject jo, HttpSession session) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【验证】
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        //1.1、参数验证
        ValidateUtil.required(jsonEntity, "login_name", "password", "phone", "sms_code", "province", "city", "area");//参数非空验证
        AccountEntity account = jsonEntity.toJavaObject(AccountEntity.class);
        // TODO: 2018/6/9  1.2、注册--验证短信码
        // 1.3、注册--唯一验证
        int resOnly = mapper.getCount(getQueryOnly(jsonEntity));//唯一验证
        if (resOnly > 0) return res.exists("该账号已被注册");//返回数据已存在
        //2、【注册账号】
        int resRegister = mapper.add(account.registerInit(snowflakeIdUtil.nextId()));//执行注册
        if (resRegister > 0) {
            // 3、【开通钱包】
            walletMapper.add(new WalletEntity().openAccount(snowflakeIdUtil.nextId(), account.getId()));//新增钱包
            //4、【注册成功--默认登录，写入用户至session】
            session.setAttribute(BaseConfig.SESSION_ACCOUNT, account);//保存账号信息至session
            session.setMaxInactiveInterval(BaseConfig.SESSION_ACCOUNT_LIMIT);//session超时时间：2小时
            return res.success(O2OUtil.copyObject(account.returnAccount(), AccountVO.class));//注册成功返回数据（隐藏一些属性）
        } else {
            return res.fail("注册账号失败");
        }
    }

    /**
     * 账号-登录
     *
     * @param jo 请求json实体
     * @return ResponseData<Object> 相应数据
     */
    @Transactional
    @Override
    public ResponseData<Object> login(JSONObject jo, HttpSession session) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【验证】
        ValidateUtil.required(jo, "login_name", "password");//参数非空验证
        //2、查询登录账号
        String query = whereEqual("login_name", jo) + andEqual("password", MD5Util.encodeAccPwd(jo.getString("login_name"), jo.getString("password")));
        List<AccountEntity> resLogin = mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, query);//查询登录账号
        if (ListUtil.isEmpty(resLogin)) return res.fail("账号或密码错误");
        session.setAttribute(BaseConfig.SESSION_ACCOUNT, resLogin.get(0));//保存账号信息至session
        session.setMaxInactiveInterval(BaseConfig.SESSION_ACCOUNT_LIMIT);//session超时时间：2小时
        // TODO: 2018/6/20 3、记录用户登录信息
        return res.success(O2OUtil.copyObject(resLogin.get(0).returnAccount(), AccountVO.class));//返回数据（隐藏一些属性）
    }

    /**
     * 账号-认证
     *
     * @param jo 请求json实体
     * @return ResponseData<Object> 相应数据
     */
    @Transactional
    @Override
    public ResponseData<Object> auth(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【验证】
        //非空验证:认证类型、店铺名称、所在省、所在市、所在区县、详细地址、联系人、联系人手机号、（可空参数：营业执照图片 business_license、企业许可证图片 allow_license）
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");//账号id
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        ValidateUtil.required(jsonEntity, "auth_type", "name", "province", "city", "area", "address", "contacts", "contact_phone");
        //2、【修改用户信息】（修改用户认证类型）
        int resUpdateAccount = mapper.updateBySQL(String.format(" SET auth_type = '%s' WHERE id = '%s'", jsonEntity.getString("auth_type"), accountId));
        if (resUpdateAccount <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        //3、【创建店铺】
        //3.1、【店铺唯一验证】
        int resShopCount = shopMapper.getCount(whereEqual("account_id", accountId));
        if (resShopCount > 0) throw new CustomException("已认证");
        ShopEntity shop = jsonEntity.toJavaObject(ShopEntity.class);//获取店铺信息
        shopMapper.add(shop.init(snowflakeIdUtil.nextId(), accountId));
        //3.1、【创建猪场】(认证类型=猪场)
        if (AuthTypeEnum.猪场.toString().equals(jsonEntity.getString("auth_type"))) {
            //【新增猪场】
            PiggeryEntity piggery = jsonEntity.toJavaObject(PiggeryEntity.class);
            String piggeryId = snowflakeIdUtil.nextId();
            piggeryMapper.add(piggery.init(piggeryId, accountId, shop.getId()));//新增猪场
            //【新增猪场指标】
            piggeryIndexMapper.add(new PiggeryIndexEntity(snowflakeIdUtil.nextId(), accountId, shop.getId(), piggeryId));//新增猪场指标
        }
        return res.success();//返回数据
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<E> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, AccountEntity.class, snowflakeIdUtil);
    }

    /**
     * 保存初始化
     *
     * @param jsonEntity 请求参数json实体
     */
    private void saveInit(JSONObject jsonEntity) {
        if (StringUtil.isEmpty(jsonEntity.getString("id")) && jsonEntity.getString("id").equals("0")) {
            jsonEntity.put("sys_state", 2);
            jsonEntity.put("create_time", new Date());
        }
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<Object>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, getQuery(jo));
    }

    /**
     * 获取查询条件
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuery(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        query += andEqual("login_name", jo);
        query += andEqual("phone", jo);
        query += andEqual("sys_state", jo);
        query += andEqual("auth_state", jo);
        return query;
    }

    /**
     * 获取唯一验证查询条件
     *
     * @param jsonEntity 请求参数json实体
     * @return query 查询条件
     */
    private String getQueryOnly(JSONObject jsonEntity) {
        return whereEqual("login_name", jsonEntity) + orEqual("phone", jsonEntity);
    }

    @Override
    public int getCount(String query) {
        return mapper.getCount(query);
    }


    @Override
    @SuppressWarnings("unchecked")
    public E getById(String id) {
        return (E) mapper.getById(id);
    }

    /**
     * 根据条件查询集合（默认限制每次最大查100条）
     *
     * @param jo 请求json
     * @return 响应结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<List<E>> getBySQL(JSONObject jo) {
        ResponseData<List<E>> res = new ResponseData<>();
        return res.success((List<E>) mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, getQuery(jo)));
    }

}
