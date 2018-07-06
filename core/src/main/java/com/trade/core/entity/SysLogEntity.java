package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统日志表(sys_log)
 *
 * @author lx
 * @since 2018-6-6 06:06:06
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sys_log")
public class SysLogEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7959580668177065989L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(name = "id", ordinal = 1)
    private String id;

    /** 日志级别（debug、sys、info、error） */
    @Column(name = "log_level", length = 10)
    @JSONField(name = "log_level", ordinal = 20)
    private String logLevel;
    
    /** 渠道（web，android，ios，weixin，admin） */
    @Column(name = "channel", length = 10)
    @JSONField(name = "channel", ordinal = 30)
    private String channel;
    
    /** 设备版本号（浏览器版本、手机系统版本） */
    @Column(name = "device_version", length = 100)
    @JSONField(name = "device_version", ordinal = 40)
    private String deviceVersion;
    
    /** 账号id */
    @Column(name = "account_id", length = 18)
    @JSONField(name = "account_id", ordinal = 50)
    private String accountId;
    
    /** 登录名 */
    @Column(name = "login_name", length = 30)
    @JSONField(name = "login_name", ordinal = 60)
    private String loginName;
    
    /** 真实姓名 */
    @Column(name = "real_name", length = 30)
    @JSONField(name = "real_name", ordinal = 70)
    private String realName;
    
    /** ip */
    @Column(name = "ip", length = 30)
    @JSONField(name = "ip", ordinal = 80)
    private String ip;
    
    /** 关键字 */
    @Column(name = "keyword", length = 30)
    @JSONField(name = "keyword", ordinal = 90)
    private String keyword;
    
    /** 所在城市 */
    @Column(name = "city", length = 30)
    @JSONField(name = "city", ordinal = 100)
    private String city;
    
    /** 日志内容 */
    @Column(name = "msg", length = 300)
    @JSONField(name = "msg", ordinal = 110)
    private String msg;

    /** 创建时间 */
    @Column(name = "create_time")
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss",ordinal = 120)
    private Date createTime;
    
}