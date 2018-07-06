package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.*;
import constants.BaseConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表(order_pig)
 *
 * @author lx
 * @since 2018-06-26 13:39:36
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_pig")
public class OrderPigEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8762668828341467963L;

    /**
     * 订单id
     */
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(name = "id", ordinal = 10)
    private String id;

    /**
     * 买家状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除]
     */
    @Column(name = "sys_state_buy", length = 10)
    @JSONField(name = "sys_state_buy", ordinal = 20)
    private Integer sysStateBuy;

    /**
     * 卖家状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除]
     */
    @Column(name = "sys_state_sale", length = 10)
    @JSONField(name = "sys_state_sale", ordinal = 20)
    private Integer sysStateSale;

    /**
     * 订单状态（0=等待买家付款，1=买家已付款，2=买家已收货，3=交易完成，4=订单取消，5=买家申请退款，6=卖家已退款，7=交易关闭，9=订单异常）
     */
    @Column(name = "order_state", length = 10)
    @JSONField(name = "order_state", ordinal = 30)
    private Integer orderState;

    /**
     * 支付状态（0=未支付，1=已支付，2=退款中，3=已退款）
     */
    @Column(name = "pay_state", length = 10)
    @JSONField(name = "pay_state", ordinal = 40)
    private Integer payState;

    /**
     * 订单渠道（web，wap，android，ios，weixin，admin）
     */
    @Column(name = "order_channel", length = 10)
    @JSONField(name = "order_channel", ordinal = 50)
    private String orderChannel;

    /**
     * 订单总金额
     */
    @Column(name = "total_price", length = 10)
    @JSONField(name = "total_price", ordinal = 60)
    private BigDecimal totalPrice;

    /**
     * 支付总金额
     */
    @Column(name = "pay_price", length = 10)
    @JSONField(name = "pay_price", ordinal = 70)
    private BigDecimal payPrice;

    /**
     * 保险金额
     */
    @Column(name = "insure_price", length = 10)
    @JSONField(name = "insure_price", ordinal = 80)
    private BigDecimal insurePrice;

    /**
     * 配送费
     */
    @Column(name = "delivery_price", length = 10)
    @JSONField(name = "delivery_price", ordinal = 90)
    private BigDecimal deliveryPrice;

    /**
     * 优惠类型（0=无优惠，1=积分抵扣，2=优惠券）
     */
    @Column(name = "discount_type", length = 10)
    @JSONField(name = "discount_type", ordinal = 100)
    private Integer discountType;

    /**
     * 优惠总金额
     */
    @Column(name = "discount_price", length = 10)
    @JSONField(name = "discount_price", ordinal = 110)
    private BigDecimal discountPrice;

    /**
     * 待支付金额
     */
    @Column(name = "unpaid_price", length = 10)
    @JSONField(name = "unpaid_price", ordinal = 120)
    private BigDecimal unpaidPrice;

    /**
     * 已支付金额
     */
    @Column(name = "paid_price", length = 10)
    @JSONField(name = "paid_price", ordinal = 130)
    private BigDecimal paidPrice;

    /**
     * 平台服务费
     */
    @Column(name = "service_price", length = 10)
    @JSONField(name = "service_price", ordinal = 140)
    private BigDecimal servicePrice;

    /**
     * 待分账金额
     */
    @Column(name = "unfz_price", length = 10)
    @JSONField(name = "unfz_price", ordinal = 150)
    private BigDecimal unfzPrice;

    /**
     * 已分账金额
     */
    @Column(name = "fz_price", length = 10)
    @JSONField(name = "fz_price", ordinal = 160)
    private BigDecimal fzPrice;

    /**
     * 单价
     */
    @Column(name = "single_price", length = 10)
    @JSONField(name = "single_price", ordinal = 170)
    private BigDecimal singlePrice;

    /**
     * 价格单位（单位：生猪 元/公斤  仔猪or种猪or白条 元/头）
     */
    @Column(name = "price_unit", length = 10)
    @JSONField(name = "price_unit", ordinal = 180)
    private String priceUnit;

    /**
     * 数量
     */
    @Column(name = "num", length = 10)
    @JSONField(name = "num", ordinal = 190)
    private Integer num;

    /**
     * 数量单位（头、份）
     */
    @Column(name = "num_unit", length = 10)
    @JSONField(name = "num_unit", ordinal = 200)
    private String numUnit;

    /**
     * 下单时间
     */
    @Column(name = "create_time", length = 26)
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 210)
    private Date createTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time", length = 26)
    @JSONField(name = "pay_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 220)
    private Date payTime;

    /**
     * 成交时间
     */
    @Column(name = "deal_time", length = 26)
    @JSONField(name = "deal_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 230)
    private Date dealTime;

    /**
     * 买家账号id
     */
    @Column(name = "buy_account_id", length = 18)
    @JSONField(name = "buy_account_id", ordinal = 240)
    private String buyAccountId;

    /**
     * 买家真实姓名
     */
    @Column(name = "buy_real_name", length = 30)
    @JSONField(name = "buy_real_name", ordinal = 250)
    private String buyRealName;

    /**
     * 买家联系人手机号
     */
    @Column(name = "buy_contact_phone", length = 20)
    @JSONField(name = "buy_contact_phone", ordinal = 260)
    private String buyContactPhone;

    /**
     * 买家备注
     */
    @Column(name = "buy_remark", length = 300)
    @JSONField(name = "buy_remark", ordinal = 270)
    private String buyRemark;

    /**
     * 卖家账号id
     */
    @Column(name = "sale_account_id", length = 18)
    @JSONField(name = "sale_account_id", ordinal = 280)
    private String saleAccountId;

    /**
     * 卖家店铺id
     */
    @Column(name = "sale_shop_id", length = 18)
    @JSONField(name = "sale_shop_id", ordinal = 290)
    private String saleShopId;

    /**
     * 卖家店铺名称
     */
    @Column(name = "sale_shop_name", length = 30)
    @JSONField(name = "sale_shop_name", ordinal = 300)
    private String saleShopName;

    /**
     * 卖家店铺类型（0=个人店铺，1=企业店铺）
     */
    @Column(name = "sale_shop_type", length = 10)
    @JSONField(name = "sale_shop_type", ordinal = 310)
    private Integer saleShopType;

    /**
     * 卖家店铺联系人
     */
    @Column(name = "sale_shop_contact", length = 20)
    @JSONField(name = "sale_shop_contact", ordinal = 320)
    private String saleShopContact;

    /**
     * 卖家店铺联系人手机号
     */
    @Column(name = "sale_shop_contact_phone", length = 20)
    @JSONField(name = "sale_shop_contact_phone", ordinal = 330)
    private String saleShopContactPhone;

    /**
     * 卖家备注
     */
    @Column(name = "sale_remark", length = 300)
    @JSONField(name = "sale_remark", ordinal = 340)
    private String saleRemark;

    /**
     * 卖家猪场id
     */
    @Column(name = "sale_piggery_id", length = 18)
    @JSONField(name = "sale_piggery_id", ordinal = 350)
    private String salePiggeryId;

    /**
     * 卖家猪场名称
     */
    @Column(name = "sale_piggery_name", length = 30)
    @JSONField(name = "sale_piggery_name", ordinal = 360)
    private String salePiggeryName;

    /**
     * 猪源id
     */
    @Column(name = "product_id", length = 18)
    @JSONField(name = "product_id", ordinal = 370)
    private String productId;

    /**
     * 猪源图片
     */
    @Column(name = "product_img", length = 100)
    @JSONField(name = "product_img", ordinal = 380)
    private String productImg;

    /**
     * 猪源品类（生猪，仔猪，种猪，白条）
     */
    @Column(name = "product_category", length = 10)
    @JSONField(name = "product_category", ordinal = 390)
    private String productCategory;

    /**
     * 猪源品种（外三元，内三元，土杂猪，外二元，内二元，藏香猪，二元母猪，长白母猪，长白公猪，大白母猪，大白公猪，杜洛克母猪，杜洛克公猪，皮特兰母猪，皮特兰公猪）
     */
    @Column(name = "product_variety", length = 20)
    @JSONField(name = "product_variety", ordinal = 400)
    private String productVariety;

    /**
     * 猪源估重（90-110公斤/头）
     */
    @Column(name = "product_weight", length = 20)
    @JSONField(name = "product_weight", ordinal = 410)
    private String productWeight;

    /**
     * 猪源所在地（江苏省徐州市泉山区）
     */
    @Column(name = "product_location", length = 50)
    @JSONField(name = "product_location", ordinal = 420)
    private String productLocation;

    /**
     * 初始化--预订下单
     *
     * @param id           订单id
     * @param productId    猪源id
     * @param num          购买数量
     * @param orderChannel 订单渠道
     * @param buyer        买家（即当前下单登录账号信息）
     */
    public OrderPigEntity initBooking(String id, String productId, Integer num, String orderChannel, AccountEntity buyer) {
        this.id = id;
        this.sysStateBuy = SysStateEnum.正常.getCode();//买家状态
        this.sysStateSale = SysStateEnum.正常.getCode();//卖家状态
        this.orderState = OrderStateEnum.等待买家付款.getCode();//订单状态
        this.payState = PayStateEnum.未支付.getCode();//支付状态
        this.productId = productId;
        this.num = num;
        this.orderChannel = orderChannel;
        this.totalPrice = this.singlePrice.multiply(new BigDecimal(num));//订单总价
        this.payPrice = this.totalPrice;//支付总金额=订单总价
        this.insurePrice = BaseConfig.DEFAULT_AMOUNT;//保险金额
        this.deliveryPrice = BaseConfig.DEFAULT_AMOUNT;//配送费
        this.discountType = DiscountTypeEnum.无优惠.getCode();//优惠类型
        this.discountPrice = BaseConfig.DEFAULT_AMOUNT;//优惠金额
        this.unpaidPrice = this.payPrice;//待支付金额=支付总金额
        this.paidPrice = BaseConfig.DEFAULT_AMOUNT;//已支付金额
        this.servicePrice = BaseConfig.DEFAULT_AMOUNT;//平台服务费
        this.unfzPrice = BaseConfig.DEFAULT_AMOUNT;//待分账金额
        this.fzPrice = BaseConfig.DEFAULT_AMOUNT;//已分账金额
        //this.singlePrice//单价（查询下单信息时从猪源获取）
        this.priceUnit = "生猪".equals(this.productCategory) ? "元/公斤" : "元/头"; //价格单位（单位：生猪 元/公斤  仔猪or种猪or白条 元/头）
        this.numUnit = "头";//数量单位（头、份）
        this.createTime = new Date();//下单时间
        this.buyAccountId = buyer.getId();//买家账号
        this.buyRealName = buyer.getRealName();//买家真实姓名
        this.buyContactPhone = buyer.getPhone();//买家手机号
        return this;
    }
}