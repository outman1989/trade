package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.OrderStateEnum;
import com.trade.core.Enum.SysStateEnum;
import com.trade.core.entity.AccountEntity;
import com.trade.core.entity.NewsEntity;
import com.trade.core.entity.OrderLogEntity;
import com.trade.core.entity.OrderPigEntity;
import com.trade.core.entity.VO.OrderPigVO;
import com.trade.core.mapper.INewsMapper;
import com.trade.core.mapper.IOrderLogMapper;
import com.trade.core.mapper.IOrderPigMapper;
import com.trade.core.service.IOrderPigService;
import com.trade.core.util.SnowflakeIdUtil;
import constants.BaseConfig;
import constants.ResponseEnum;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ListUtil;
import util.StringUtil;
import util.ValidateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单 serviceImpl
 *
 * @author lx
 * @since 2018-06-26 10:37:44
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class OrderPigServiceImpl<E> extends BaseServiceImpl implements IOrderPigService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IOrderPigMapper mapper;
    @Autowired
    private IOrderLogMapper orderLogMapper;
    @Autowired
    private INewsMapper newsMapper;
    @Autowired
    private Environment env;

    /**
     * 订单--预订下单
     */
    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> booking(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【获取参数、验证】
        String productId = ValidateUtil.requiredSingle(jo, "product_id");//猪源id
        Integer num = ValidateUtil.positiveInteger(jo, "num");//购买数量
        String orderChannel = ValidateUtil.requiredSingle(jo, "order_channel");//订单渠道

        //2、【获取订单信息】[买家、卖家(猪源、猪场、店铺、账号)、订单信息]、猪源状态、库存验证
        List<OrderPigEntity> orderList = mapper.getBookingInfo(getBookingSql(productId, num));
        if (ListUtil.isEmpty(orderList)) return res.fail("商品已下架");
        AccountEntity buyer = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//session 验证&&参数处理

        //3、【生成订单】
        OrderPigEntity orderPig = orderList.get(0).initBooking(snowflakeIdUtil.nextId(), productId, num, orderChannel, buyer);
        mapper.add(orderPig);
        //4、【记录订单日志】
        orderLogMapper.add(new OrderLogEntity(snowflakeIdUtil.nextId(), orderPig.getId(), "预订下单", buyer));
        // TODO: 2018/6/26 //5、【买家信誉】

        // TODO: 2018/6/26 //6、【卖家店铺信誉】

        //7.1、 创建消息
        String newsTitle = String.format("您下单了%s头%s", num, (orderPig.getProductVariety().contains("猪") ? orderPig.getProductVariety() : (orderPig.getProductVariety() + "生猪")));
        String newsContent = String.format(
                "您对卖家[%s]%s，请关注订单动态，按时收猪。订单号%s，卖家电话%s。如有问题，请咨询我们，客服电话%s",
                orderPig.getSaleShopName(), newsTitle.replace("您", ""), orderPig.getId(), orderPig.getSaleShopContactPhone(), env.getProperty("cfg.consult_phone")
        );
        NewsEntity news = new NewsEntity(snowflakeIdUtil.nextId(), buyer.getId(), newsTitle, newsContent, "进入订单详情>", "buy_order_detail");//买家消息
        newsMapper.add(news);//新增消息
        // TODO: 2018/7/5 7.2、 消息推送

        return res.success(orderList.get(0).getId());
    }

    /**
     * 订单--查询下单信息
     */
    private String getBookingSql(String productId, Integer num) {
        String sql = "SELECT pp.account_id AS sale_account_id,pp.shop_id AS sale_shop_id,s.name AS sale_shop_name,s.shop_type AS sale_shop_type,";
        sql += " s.contacts AS sale_shop_contact,s.contact_phone AS sale_shop_contact_phone,pp.piggery_id AS sale_piggery_id,p.name AS sale_piggery_name,";
        sql += " pp.id AS product_id,pp.product_img AS product_img,pp.product_category AS product_category, pp.product_variety AS product_variety,";
        sql += " concat(pp.min_weight ,'-',pp.max_weight,'公斤/头') AS product_weight,concat(p.province,p.city,p.area) AS product_location,pp.unit_price AS singlePrice";
        sql += " FROM product_pig pp INNER JOIN piggery p ON pp.piggery_id = p.id INNER JOIN shop s ON pp.shop_id = s.id INNER JOIN account a ON pp.account_id = a.id";
        sql += String.format(" WHERE pp.id = '%s' AND pp.product_state = '1' AND (pp.supply_type = '1' or pp.surplus_stock > %s )", productId, num);
        return sql;
    }

    /**
     * 订单--我的订单
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> myOrder(JSONObject jo) {
        ResponseData<Object> res = new ResponseData<>();
        String query = getQuery(jo);
        int total = mapper.getCount(query);//查询条数
        List<OrderPigVO> list = new ArrayList<>();
        if (total > 0) {
            list = mapper.myOrder(SqlConfig.getPageNumber(jo), SqlConfig.getPageSize(jo), SqlConfig.DEFAULT_QUERY_FIELDS, query, null);
        }
        return res.success(new Page(total, list));
    }

    /**
     * 订单--取消订单
     */
    @Transactional
    @Override
    public ResponseData<Object> cancelOrder(JSONObject jo) throws Exception {
        String orderId = ValidateUtil.requiredSingle(jo, "order_id");
        ResponseData<Object> res = new ResponseData<>();
        AccountEntity buyer = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//session 验证&&参数处理
        //1、修改订单状态为：订单取消
        int updateRes = mapper.updateBySQL(String.format(" SET order_state=" + OrderStateEnum.订单取消.getCode() + " WHERE id = %s ", orderId));
        if (updateRes <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        //2、记录订单日志
        orderLogMapper.add(new OrderLogEntity(snowflakeIdUtil.nextId(), orderId, "取消订单", buyer));
        return res.operateRes(updateRes);
    }

    /**
     * 订单--删除
     */
    @Transactional
    @Override
    public ResponseData<Object> deleteOrder(JSONObject jo) throws Exception {
        String orderId = ValidateUtil.requiredSingle(jo, "order_id");
        String accountType = ValidateUtil.requiredSingle(jo, "account_type");
        ResponseData<Object> res = new ResponseData<>();
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        //1、修改订单的系统状态为：已删除
        if (accountType.equals("buy") || accountType.equals("sale")) {
            //根据用户类型判断买家 or 卖家
            String updateSql = String.format(" SET sys_state_%s = %s WHERE id = '%s' AND %s_account_id = '%s' AND order_state = %s ", accountType, SysStateEnum.已删除.getCode(), orderId, accountType, account.getId(), OrderStateEnum.订单取消.getCode());
            int updateRes = mapper.updateBySQL(updateSql);
            if (updateRes <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
            //2、记录订单日志
            orderLogMapper.add(new OrderLogEntity(snowflakeIdUtil.nextId(), orderId, "删除", account));
            return res.operateRes(updateRes);
        } else {
            return res.invalidParam();
        }
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, OrderPigEntity.class, snowflakeIdUtil);
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
        //买家查询订单
        if (StringUtil.isNotEmpty(jo.getString("buy_account_id"))) {
            query += (andEqual("buy_account_id", jo));//买家查询订单
            query += (andEqual("sys_state_buy", SysStateEnum.正常.getCode().toString()));//买家状态（删除后的订单不可见）
        }
        //卖家查询订单
        if (StringUtil.isNotEmpty(jo.getString("sale_account_id"))) {
            query += (andEqual("sale_account_id", jo));//卖家查询订单
            query += (andEqual("sys_state_sale", SysStateEnum.正常.getCode().toString()));//卖家状态（删除后的订单不可见）
        }
        query += (andEqual("order_state", jo));//订单状态
        return query;
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
        return res.success((List<E>) mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, getQuery(jo) + SqlConfig.DEFAULT_QUERY_LIMIT));
    }

}
