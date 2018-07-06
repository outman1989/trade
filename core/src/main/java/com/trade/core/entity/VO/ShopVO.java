package com.trade.core.entity.VO;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.ShopStateEnum;
import com.trade.core.Enum.ShopTypeEnum;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺VO(shop)
 * 
 * @author lx
 * @since 2018-06-09
 */
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("unused")
public class ShopVO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4901083351093984837L;

    @JSONField(ordinal = 1)
    private String id;

    /** 账号id */
    @JSONField(name = "account_id", ordinal = 10)
    private String accountId;

    /** 店铺名称 */
    @JSONField(name = "name", ordinal = 20)
    private String name;

    /** 店铺头像 */
    @JSONField(name = "icon", ordinal = 30)
    private String icon;

    /** 店铺二维码 */
    @JSONField(name = "qr", ordinal = 40)
    private String qr;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @JSONField(name = "sys_state", ordinal = 50)
    private Integer sysState;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @JSONField(name = "sys_state_cn", ordinal = 60)
    private String sysStateCn;

    /** 店铺类型（0=个人店铺，1=企业店铺） */
    @JSONField(name = "shop_type", ordinal = 70)
    private Integer shopType;

    /** 店铺类型（0=个人店铺，1=企业店铺） */
    @JSONField(name = "shop_type_cn", ordinal = 80)
    private String shopTypeCn;

    /** 店铺状态（0=未认证，1=普通认证，2=品牌认证） */
    @JSONField(name = "shop_state", ordinal = 90)
    private Integer shopState;

    /** 店铺状态（0=未认证，1=普通认证，2=品牌认证） */
    @JSONField(name = "shop_state_cn", ordinal = 100)
    private String shopStateCn;

    /** （猪场，屠宰场，贸易商） */
    @JSONField(name = "auth_type", ordinal = 110)
    private String authType;

    /** 信誉值 */
    @JSONField(name = "reputation", ordinal = 120)
    private BigDecimal reputation;

    /** 所在省 */
    @JSONField(name = "province", ordinal = 130)
    private String province;

    /** 所在市 */
    @JSONField(name = "city", ordinal = 140)
    private String city;

    /** 所在县区 */
    @JSONField(name = "area", ordinal = 150)
    private String area;

    /** 详细地址 */
    @JSONField(name = "address", ordinal = 160)
    private String address;

    /** 营业执照图片 */
    @JSONField(name = "business_license", ordinal = 170)
    private String businessLicense;

    /** 企业许可证图片 */
    @JSONField(name = "allow_license", ordinal = 180)
    private String allowLicense;

    /** 联系人 */
    @JSONField(name = "contacts", ordinal = 190)
    private String contacts;

    /** 联系人手机号 */
    @JSONField(name = "contact_phone", ordinal = 200)
    private String contactPhone;

    /** 创建时间 */
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss",ordinal = 210)
    private Date createTime;

    public void setSysState(Integer sysState) {
        this.sysState = sysState;
        this.sysStateCn = SysStateEnum.getName(sysState);
    }

    public void setShopState(Integer shopState) {
        this.shopState = shopState;
        this.shopStateCn = ShopStateEnum.getName(shopState);
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
        this.shopTypeCn = ShopTypeEnum.getName(shopType);
    }
}