package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.PriorityEnum;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 猪场表(piggery)
 * 
 * @author lx
 * @since 2018-06-12
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "piggery")
public class PiggeryEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4259292934818385864L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(ordinal = 10)
    private String id;

    /** 账号id */
    @Column(name = "account_id", length = 19)
    @JSONField(name = "account_id",  ordinal = 20)
    private String accountId;

    /** 店铺id */
    @Column(name = "shop_id", length = 19)
    @JSONField(name = "shop_id",  ordinal = 30)
    private String shopId;

    /** 猪场名称 */
    @Column(name = "name", length = 30)
    @JSONField(name = "name",  ordinal = 40)
    private String name;

    /** 猪场头像 */
    @Column(name = "icon", length = 100)
    @JSONField(name = "icon",  ordinal = 50)
    private String icon;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state", length = 10)
    @JSONField(name = "sys_state",  ordinal = 60)
    private Integer sysState;

    /** 猪场状态（默认0，预留字段） */
    @Column(name = "piggery_state", length = 10)
    @JSONField(name = "piggery_state",  ordinal = 70)
    private Integer piggeryState;

    /** 猪场类型 */
    @Column(name = "piggery_type", length = 20)
    @JSONField(name = "piggery_type",  ordinal = 80)
    private String piggeryType;

    /** 所在省 */
    @Column(name = "province", length = 30)
    @JSONField(name = "province",  ordinal = 100)
    private String province;

    /** 所在市 */
    @Column(name = "city", length = 30)
    @JSONField(name = "city",  ordinal = 110)
    private String city;

    /** 所在县区 */
    @Column(name = "area", length = 30)
    @JSONField(name = "area",  ordinal = 120)
    private String area;

    /** 详细地址 */
    @Column(name = "address", length = 100)
    @JSONField(name = "address",  ordinal = 130)
    private String address;

    /** 猪场图片（保存文件路径，最多上传6张，以 | 分隔，只支持 *.jpg,*.jpeg,*.png格式） */
    @Column(name = "piggery_img", length = 1000)
    @JSONField(name = "piggery_img",  ordinal = 135)
    private String piggeryImg;

    /** 营业执照图片（保存文件路径，最多上传1张，只支持 *.jpg,*.jpeg,*.png格式） */
    @Column(name = "business_license", length = 200)
    @JSONField(name = "business_license",  ordinal = 140)
    private String businessLicense;

    /** 企业许可证图片（保存文件路径，最多上传1张，只支持 *.jpg,*.jpeg,*.png格式） */
    @Column(name = "allow_license", length = 200)
    @JSONField(name = "allow_license",  ordinal = 150)
    private String allowLicense;

    /** 联系人 */
    @Column(name = "contacts", length = 20)
    @JSONField(name = "contacts",  ordinal = 160)
    private String contacts;

    /** 联系人手机号 */
    @Column(name = "contact_phone", nullable = false, length = 20)
    @JSONField(name = "contact_phone",  ordinal = 170)
    private String contactPhone;

    /** 显示优先级（默认0，优先级从高往低） */
    @Column(name = "priority", nullable = false, length = 20)
    @JSONField(name = "priority",  ordinal = 175)
    private Integer priority;

    /** 创建时间 */
    @Column(name = "create_time")
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 180)
    private Date createTime;

    /**
     * 猪场初始化
     */
    public PiggeryEntity init(String id,String accountId,String shopId) {
        this.id = id;
        this.accountId = accountId;
        this.shopId = shopId;
        this.sysState = SysStateEnum.正常.getCode();//系统状态
        this.piggeryState = 0;//猪场状态
        this.priority = PriorityEnum.默认.getCode();//显示优先级
        this.createTime = new Date();
        return this;
    }
}