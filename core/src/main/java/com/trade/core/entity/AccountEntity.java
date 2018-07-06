package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.MD5Util;
import util.ObjectUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账号表(account)
 *
 * @author lx
 * @since 2018-06-07
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class AccountEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3740066701980415538L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(name = "id", ordinal = 10)
    private String id;

    /** 用户名（登录名） */
    @Column(name = "login_name",  length = 30)
    @JSONField(name = "login_name",  ordinal = 20)
    private String loginName;

    /** 登录密码 */
    @Column(name = "password",  length = 30)
    @JSONField(name = "password",  ordinal = 30)
    private String password;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state",  length = 10)
    @JSONField(name = "sys_state",  ordinal = 40)
    private Integer sysState;

    /** 认证类型（猪场，屠宰场，贸易商） */
    @Column(name = "auth_type",  length = 20)
    @JSONField(name = "auth_type",  ordinal = 50)
    private String authType;

    /** 认证状态（0=未认证，1=个人认证，2=企业认证） */
    @Column(name = "auth_state",  length = 10)
    @JSONField(name = "auth_state",  ordinal = 60)
    private Integer authState;

    /** 权限类型（0=普通权限 1=管理员权限） */
    @Column(name = "role_type",  length = 10)
    @JSONField(name = "role_type",  ordinal = 70)
    private Integer roleType;

    /** 企业id */
    @Column(name = "company_id",  length = 19)
    @JSONField(name = "company_id",  ordinal = 80)
    private String companyId;

    /** 昵称 */
    @Column(name = "nick_name",  length = 30)
    @JSONField(name = "nick_name",  ordinal = 90)
    private String nickName;

    /** 性别（0=保密，1=男，2=女） */
    @Column(name = "sex",  length = 10)
    @JSONField(name = "sex",  ordinal = 100)
    private Integer sex;

    /** 真实姓名 */
    @Column(name = "real_name",  length = 30)
    @JSONField(name = "real_name",  ordinal = 110)
    private String realName;

    /** 头像 */
    @Column(name = "icon",  length = 100)
    @JSONField(name = "icon",  ordinal = 120)
    private String icon;

    /** 证件类型（IC=身份证） */
    @Column(name = "card_type",  length = 10)
    @JSONField(name = "card_type",  ordinal = 130)
    private String cardType;

    /** 证件号 */
    @Column(name = "card_no",  length = 30)
    @JSONField(name = "card_no",  ordinal = 140)
    private String cardNo;

    /** 手机号 */
    @Column(name = "phone",  length = 20)
    @JSONField(name = "phone",  ordinal = 150)
    private String phone;

    /** 邮箱 */
    @Column(name = "mailbox",  length = 30)
    @JSONField(name = "mailbox",  ordinal = 160)
    private String mailbox;

    /** QQ */
    @Column(name = "qq",  length = 20)
    @JSONField(name = "qq",  ordinal = 170)
    private String qq;

    /** 微信 */
    @Column(name = "weixin",  length = 30)
    @JSONField(name = "weixin",  ordinal = 180)
    private String weixin;

    /** 所在省 */
    @Column(name = "province",  length = 20)
    @JSONField(name = "province",  ordinal = 190)
    private String province;

    /** 所在城市 */
    @Column(name = "city",  length = 20)
    @JSONField(name = "city",  ordinal = 200)
    private String city;

    /** 所在区/县 */
    @Column(name = "area",  length = 20)
    @JSONField(name = "area",  ordinal = 210)
    private String area;

    /** 详细地址 */
    @Column(name = "address",  length = 100)
    @JSONField(name = "address",  ordinal = 220)
    private String address;

    /** 注册渠道（web，android，ios，weixin，admin） */
    @Column(name = "register_channel",  length = 10)
    @JSONField(name = "register_channel",  ordinal = 230)
    private String registerChannel;

    /** 信誉值（买家） */
    @Column(name = "reputation")
    @JSONField(name = "reputation",  ordinal = 240)
    private BigDecimal reputation;

    /** 创建时间 */
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 250)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 注册初始化
     */
    public AccountEntity registerInit(String id) {
        this.id = id;
        this.password = MD5Util.encodeAccPwd(this.loginName, this.password);//密码MD5加密
        this.sysState = SysStateEnum.正常.getCode();//系统状态
        this.authType = AuthTypeEnum.暂无.toString();//认证类型
        this.authState = AuthStateEnum.未认证.getCode();//认证状态
        this.roleType = RoleTypeEnum.普通权限.getCode();//权限类型
        this.sex = SexEnum.保密.getCode();//性别
        this.registerChannel = ChannelEnum.WEB.getCode();//注册渠道
        this.createTime = new Date();
        return this;
    }

    /**
     * 返回前段账号信息（隐藏一些属性）
     */
    public AccountEntity returnAccount(){
        if(!ObjectUtil.isEmpty(this)){
            this.password = null;
        }
        return this;
    }

}