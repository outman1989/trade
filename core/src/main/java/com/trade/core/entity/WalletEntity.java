package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 钱包表(wallet)
 *
 * @author lx
 * @since 2018-06-12
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wallet")
public class WalletEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8983801452163012934L;

    @Id
    @GeneratedValue
    @JSONField(ordinal = 10)
    @Column(name = "id", unique = true, nullable = false, length = 18)
    private String id;
    /**
     * 账号id
     */
    @Column(name = "account_id", length = 19)
    @JSONField(name = "account_id", ordinal = 20)
    private String accountId;

    /**
     * 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除]
     */
    @Column(name = "sys_state", length = 10)
    @JSONField(name = "sys_state", ordinal = 30)
    private Integer sysState;

    /**
     * 总余额
     */
    @Column(name = "total_balance", length = 11)
    @JSONField(name = "total_balance", ordinal = 40)
    private BigDecimal totalBalance;

    /**
     * 冻结金额
     */
    @Column(name = "frozen_balance", length = 11)
    @JSONField(name = "frozen_balance", ordinal = 50)
    private BigDecimal frozenBalance;

    /**
     * 可用余额
     */
    @Column(name = "usable_balance", length = 11)
    @JSONField(name = "usable_balance", ordinal = 60)
    private BigDecimal usableBalance;

    /**
     * 保证金
     */
    @Column(name = "bond_balance", length = 11)
    @JSONField(name = "bond_balance", ordinal = 70)
    private BigDecimal bondBalance;

    /**
     * 积分
     */
    @Column(name = "integral", length = 11)
    @JSONField(name = "integral", ordinal = 80)
    private BigDecimal integral;

    /**
     * 充值金额
     */
    @Column(name = "recharge_balance", length = 11)
    @JSONField(name = "recharge_balance", ordinal = 90)
    private BigDecimal rechargeBalance;

    /**
     * 收入金额
     */
    @Column(name = "income_balance", length = 11)
    @JSONField(name = "income_balance", ordinal = 100)
    private BigDecimal incomeBalance;

    /**
     * 支出金额
     */
    @Column(name = "expend_balance", length = 11)
    @JSONField(name = "expend_balance", ordinal = 110)
    private BigDecimal expendBalance;

    /**
     * 提现金额
     */
    @Column(name = "presented_balance", length = 11)
    @JSONField(name = "presented_balance", ordinal = 120)
    private BigDecimal presentedBalance;

    /**
     * 退款金额
     */
    @Column(name = "refund_balance", length = 11)
    @JSONField(name = "refund_balance", ordinal = 130)
    private BigDecimal refundBalance;

    /**
     * 支付密码
     */
    @Column(name = "pay_password", length = 50)
    @JSONField(name = "pay_password", ordinal = 140)
    private String payPassword;

    /**
     * 绑定银行卡数
     */
    @Column(name = "bind_card", length = 10)
    @JSONField(name = "bind_card", ordinal = 150)
    private Integer bindCard;

    /**
     * 钱包开户
     */
    public WalletEntity openAccount(String id, String accountId) {
        this.id = id;
        this.accountId = accountId;
        this.sysState = SysStateEnum.正常.getCode();//系统状态
        this.totalBalance = new BigDecimal(0);//总余额
        this.frozenBalance = new BigDecimal(0);//冻结金额
        this.usableBalance = new BigDecimal(0);//可用余额
        this.bondBalance = new BigDecimal(0);//保证金
        this.integral = new BigDecimal(0);//积分
        this.rechargeBalance = new BigDecimal(0);//充值金额
        this.incomeBalance = new BigDecimal(0);//收入金额
        this.expendBalance = new BigDecimal(0);//支出金额
        this.presentedBalance = new BigDecimal(0);//提现金额
        this.refundBalance = new BigDecimal(0);//退款金额
        this.bindCard = 0;//绑定银行卡数
        return this;
    }
}