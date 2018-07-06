package com.trade.core.entity.VO;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账号VO
 *
 * @author lx
 * @since 2018-6-9 17:36:44
 */
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("unused")
public class AccountVO implements java.io.Serializable {
    private static final long serialVersionUID = 2156196535651328098L;

    @JSONField(ordinal = 10)
    private String id;

    /** 用户名（登录名） */
    @JSONField(name = "login_name",  ordinal = 20)
    private String loginName;

    /** 登录密码 */
    @JSONField(name = "password",  ordinal = 30)
    private String password;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @JSONField(name = "sys_state",  ordinal = 40)
    private Integer sysState;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @JSONField(name = "sys_state_cn",  ordinal = 40)
    private String sysStateCn;

    /** 认证类型（猪场，屠宰场，贸易商） */
    @JSONField(name = "auth_type",  ordinal = 50)
    private String authType;

    /** 认证状态（0=未认证，1=个人认证，2=企业认证） */
    @JSONField(name = "auth_state",  ordinal = 60)
    private Integer authState;

    /** 认证状态（0=未认证，1=个人认证，2=企业认证） */
    @JSONField(name = "auth_state_cn",  ordinal = 60)
    private String authStateCn;

    /** 权限类型（0=普通权限 1=管理员权限） */
    @JSONField(name = "role_type",  ordinal = 70)
    private Integer roleType;

    /** 权限类型（0=普通权限 1=管理员权限） */
    @JSONField(name = "role_type_cn",  ordinal = 70)
    private String roleTypeCn;

    /** 企业id */
    @JSONField(name = "company_id",  ordinal = 80)
    private String companyId;

    /** 昵称 */
    @JSONField(name = "nick_name",  ordinal = 90)
    private String nickName;

    /** 性别（0=保密，1=男，2=女） */
    @JSONField(name = "sex",  ordinal = 100)
    private Integer sex;

    /** 性别（0=保密，1=男，2=女） */
    @JSONField(name = "sex_cn",  ordinal = 100)
    private String sexCn;

    /** 真实姓名 */
    @JSONField(name = "real_name",  ordinal = 110)
    private String realName;

    /** 头像 */
    @JSONField(name = "icon",  ordinal = 120)
    private String icon;

    /** 证件类型（IC=身份证） */
    @JSONField(name = "card_type",  ordinal = 130)
    private String cardType;

    /** 证件类型（IC=身份证） */
    @JSONField(name = "card_type_cn",  ordinal = 130)
    private String cardTypeCn;

    /** 证件号 */
    @JSONField(name = "card_no",  ordinal = 140)
    private String cardNo;

    /** 手机号 */
    @JSONField(name = "phone",  ordinal = 150)
    private String phone;

    /** 邮箱 */
    @JSONField(name = "mailbox",  ordinal = 160)
    private String mailbox;

    /** QQ */
    @JSONField(name = "qq",  ordinal = 170)
    private String qq;

    /** 微信 */
    @JSONField(name = "weixin",  ordinal = 180)
    private String weixin;

    /** 所在省 */
    @JSONField(name = "province",  ordinal = 190)
    private String province;

    /** 所在城市 */
    @JSONField(name = "city",  ordinal = 200)
    private String city;

    /** 所在区/县 */
    @JSONField(name = "area",  ordinal = 210)
    private String area;

    /** 详细地址 */
    @JSONField(name = "address",  ordinal = 220)
    private String address;

    /** 注册渠道（web，android，ios，weixin，admin） */
    @JSONField(name = "register_channel",  ordinal = 230)
    private String registerChannel;

    /** 信誉值（买家） */
    @JSONField(name = "reputation",  ordinal = 240)
    private BigDecimal reputation;

    /** 创建时间 */
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 250)
    private Date createTime;

    public void setSysState(Integer sysState) {
        this.sysState = sysState;
        this.sysStateCn = SysStateEnum.getName(sysState);
    }

    public void setAuthState(Integer authState) {
        this.authState = authState;
        this.authStateCn = AuthStateEnum.getName(authState);
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
        this.roleTypeCn = RoleTypeEnum.getName(roleType);
    }

    public void setSex(Integer sex) {
        this.sex = sex;
        this.sexCn = SexEnum.getName(sex);
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
        this.cardTypeCn = CardTypeEnum.getName(cardType);
    }

}