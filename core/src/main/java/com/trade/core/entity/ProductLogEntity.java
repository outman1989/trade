package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.LogTypeEnum;
import com.trade.core.Enum.SysStateEnum;
import constants.BaseConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 猪源日志表(product_log)
 * 
 * @author lx
 * @since 2018-06-28 17:48:08
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product_log")
public class ProductLogEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4535498235915533262L;

    /** 猪源日志id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 猪源id */
    @Column(name = "product_id", length = 18)
	@JSONField(name = "product_id", ordinal = 20)
    private String productId;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state", length = 10)
	@JSONField(name = "sys_state", ordinal = 30)
    private Integer sysState;

    /** 日志类型（0=默认，1=买家，2=卖家，3=运营） */
    @Column(name = "log_type", length = 10)
	@JSONField(name = "log_type", ordinal = 40)
    private Integer logType;

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

    /** 日志内容 */
    @Column(name = "log_info", length = 300)
	@JSONField(name = "log_info", ordinal = 80)
    private String logInfo;

    /** 创建时间 */
    @Column(name = "create_time", length = 26)
	@JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 90)
    private Date createTime;

    public ProductLogEntity(String id, String productId, String logInfo,AccountEntity account) {
        this.id = id;
        this.productId = productId;
        this.sysState = SysStateEnum.正常.getCode();
        this.logType = LogTypeEnum.默认.getCode();
        this.accountId = account.getId();
        this.loginName = account.getLoginName();
        this.realName = account.getRealName();
        this.logInfo = logInfo;
        this.createTime = new Date();
    }

    public ProductLogEntity(String id, String productId, String logInfo) {
        this.id = id;
        this.productId = productId;
        this.sysState = SysStateEnum.正常.getCode();
        this.logType = LogTypeEnum.默认.getCode();
        this.accountId = BaseConfig.LOG_SYS_ACCOUNT;
        this.loginName = BaseConfig.LOG_SYS_ACCOUNT;
        this.realName = BaseConfig.LOG_SYS_ACCOUNT;
        this.logInfo = logInfo;
        this.createTime = new Date();
    }

}