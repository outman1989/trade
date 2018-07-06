package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.ShopStateEnum;
import com.trade.core.Enum.ShopTypeEnum;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺表(shop)
 * 
 * @author lx
 * @since 2018-06-09
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "shop")
public class ShopEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4901083351093984837L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(ordinal = 1)
    private String id;

    /** 账号id */
    @Column(name = "account_id", length = 19)
    @JSONField(name = "account_id", ordinal = 10)
    private String accountId;

    /** 店铺名称 */
    @Column(name = "name", length = 30)
    @JSONField(name = "name", ordinal = 20)
    private String name;

    /** 店铺头像 */
    @Column(name = "icon", length = 100)
    @JSONField(name = "icon", ordinal = 30)
    private String icon;

    /** 店铺二维码 */
    @Column(name = "qr", length = 100)
    @JSONField(name = "qr", ordinal = 40)
    private String qr;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state", length = 10)
    @JSONField(name = "sys_state", ordinal = 50)
    private Integer sysState;

    /** 店铺类型（0=个人店铺，1=企业店铺） */
    @Column(name = "shop_type", length = 10)
    @JSONField(name = "shop_type", ordinal = 70)
    private Integer shopType;

    /** 店铺状态（0=未认证，1=普通认证，2=品牌认证） */
    @Column(name = "shop_state", length = 10)
    @JSONField(name = "shop_state", ordinal = 90)
    private Integer shopState;

    /** （猪场，屠宰场，贸易商） */
    @Column(name = "auth_type", length = 20)
    @JSONField(name = "auth_type", ordinal = 110)
    private String authType;

    /** 信誉值 */
    @Column(name = "reputation", length = 11)
    @JSONField(name = "reputation", ordinal = 120)
    private BigDecimal reputation;

    /** 所在省 */
    @Column(name = "province", length = 30)
    @JSONField(name = "province", ordinal = 130)
    private String province;

    /** 所在市 */
    @Column(name = "city", length = 30)
    @JSONField(name = "city", ordinal = 140)
    private String city;

    /** 所在县区 */
    @Column(name = "area", length = 30)
    @JSONField(name = "area", ordinal = 150)
    private String area;

    /** 详细地址 */
    @Column(name = "address", length = 100)
    @JSONField(name = "address", ordinal = 160)
    private String address;

    /** 营业执照图片 */
    @Column(name = "business_license", length = 100)
    @JSONField(name = "business_license", ordinal = 170)
    private String businessLicense;

    /** 企业许可证图片 */
    @Column(name = "allow_license", length = 100)
    @JSONField(name = "allow_license", ordinal = 180)
    private String allowLicense;

    /** 联系人 */
    @Column(name = "contacts", length = 20)
    @JSONField(name = "contacts", ordinal = 190)
    private String contacts;

    /** 联系人手机号 */
    @Column(name = "contact_phone", length = 20)
    @JSONField(name = "contact_phone", ordinal = 200)
    private String contactPhone;

    /** 创建时间 */
    @Column(name = "create_time")
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss",ordinal = 210)
    private Date createTime;

    /**
     * 店铺初始化
     */
    public ShopEntity init(String id,String accountId) {
        this.id = id;
        this.accountId = accountId;
        this.sysState = SysStateEnum.正常.getCode();//系统状态
        this.shopType = ShopTypeEnum.个人店铺.getCode();//店铺类型
        this.shopState = ShopStateEnum.未认证.getCode();//店铺状态
        this.reputation = new BigDecimal(0);//信誉值
        this.createTime = new Date();
        return this;
    }
}