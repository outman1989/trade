package com.trade.core.entity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * 猪场指标表(piggery_index)
 * 
 * @author lx
 * @since 2018-06-29 14:03:44
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "piggery_index")
public class PiggeryIndexEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2760370218901545245L;

    /** 猪场指标id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 账号id */
    @Column(name = "account_id", length = 18)
	@JSONField(name = "account_id", ordinal = 20)
    private String accountId;

    /** 店铺id */
    @Column(name = "shop_id", length = 18)
	@JSONField(name = "shop_id", ordinal = 30)
    private String shopId;

    /** 猪场id */
    @Column(name = "piggery_id", length = 18)
	@JSONField(name = "piggery_id", ordinal = 40)
    private String piggeryId;

    /** 养殖品系（默认无，[美系，加系，丹系，台系，英系，法系]） */
    @Column(name = "yzpx", length = 10)
	@JSONField(name = "yzpx", ordinal = 50)
    private String yzpx;

    /** 生猪年出栏（默认0） */
    @Column(name = "szncl", length = 10)
	@JSONField(name = "szncl", ordinal = 60)
    private Integer szncl;

    /** 一元母猪年供应（默认0） */
    @Column(name = "yymzngy", length = 10)
	@JSONField(name = "yymzngy", ordinal = 70)
    private Integer yymzngy;

    /** 二元母猪年供应（默认0） */
    @Column(name = "eymzngy", length = 10)
	@JSONField(name = "eymzngy", ordinal = 80)
    private Integer eymzngy;

    /** 母猪存栏（默认0） */
    @Column(name = "mzcl", length = 10)
	@JSONField(name = "mzcl", ordinal = 90)
    private Integer mzcl;

    /** 仔猪年出栏（默认0） */
    @Column(name = "zzncl", length = 10)
	@JSONField(name = "zzncl", ordinal = 100)
    private Integer zzncl;

    /** 全进全出（0=否，1=是） */
    @Column(name = "qjqc", length = 1)
	@JSONField(name = "qjqc", ordinal = 110)
    private Integer qjqc;

    /** 装猪台（默认无） */
    @Column(name = "zzt", length = 30)
	@JSONField(name = "zzt", ordinal = 120)
    private String zzt;

    /** 称重方式（默认无） */
    @Column(name = "czfs", length = 30)
	@JSONField(name = "czfs", ordinal = 130)
    private String czfs;

    /** 冲水设备（默认无） */
    @Column(name = "cssb", length = 30)
	@JSONField(name = "cssb", ordinal = 140)
    private String cssb;

    /** 喂料方式（默认无） */
    @Column(name = "wlfs", length = 30)
	@JSONField(name = "wlfs", ordinal = 150)
    private String wlfs;

    /** 交通方式（默认无） */
    @Column(name = "jtfs", length = 30)
	@JSONField(name = "jtfs", ordinal = 160)
    private String jtfs;

    /** （默认0）PSY 是指每头母猪每年所能提供的断奶仔猪头数，是衡量猪场效益和母猪繁殖成绩的重要指标 */
    @Column(name = "psy", length = 10)
	@JSONField(name = "psy", ordinal = 170)
    private BigDecimal psy;

    /** （默认0）MSY 为每年每头母猪出栏肥猪头数。MSY=PSY*育肥猪成活率 */
    @Column(name = "msy", length = 10)
	@JSONField(name = "msy", ordinal = 180)
    private BigDecimal msy;

    /** 出肉率（默认0） */
    @Column(name = "crl", length = 10)
	@JSONField(name = "crl", ordinal = 190)
    private BigDecimal crl;

    /** 瘦肉率（默认0） */
    @Column(name = "srl", length = 10)
	@JSONField(name = "srl", ordinal = 200)
    private BigDecimal srl;

    /** 伤残率（默认0） */
    @Column(name = "scl", length = 10)
	@JSONField(name = "scl", ordinal = 210)
    private BigDecimal scl;

    /** 杂毛率（默认0） */
    @Column(name = "zml", length = 10)
	@JSONField(name = "zml", ordinal = 220)
    private BigDecimal zml;

    public PiggeryIndexEntity(String id, String accountId, String shopId, String piggeryId) {
        this.id = id;
        this.accountId = accountId;
        this.shopId = shopId;
        this.piggeryId = piggeryId;
        String defaultString = "无";
        BigDecimal defaultDecimal = new BigDecimal(0);
        this.yzpx = defaultString;    //		养殖品系（默认无，[美系，加系，丹系，台系，英系，法系]）
        this.szncl = 0;    //	生猪年出栏（默认0）
        this.yymzngy = 0;    //	一元母猪年供应（默认0）
        this.eymzngy = 0;    //	二元母猪年供应（默认0）
        this.mzcl = 0;    //	母猪存栏（默认0）
        this.zzncl = 0;    //	仔猪年出栏（默认0）
        this.qjqc = 0;    //		全进全出（0=否，1=是）
        this.zzt = defaultString;    //	装猪台（默认无）
        this.czfs = defaultString;    //		称重方式（默认无）
        this.cssb = defaultString;    //		冲水设备（默认无）
        this.wlfs = defaultString;    //		喂料方式（默认无）
        this.jtfs = defaultString;    //		交通方式（默认无）
        this.psy = defaultDecimal;    //	（默认0）PSY 是指每头母猪每年所能提供的断奶仔猪头数，是衡量猪场效益和母猪繁殖成绩的重要指标
        this.msy = defaultDecimal;    //	（默认0）MSY 为每年每头母猪出栏肥猪头数。MSY=PSY*育肥猪成活率
        this.crl = defaultDecimal;    //	出肉率（默认0）
        this.srl = defaultDecimal;    //	瘦肉率（默认0）
        this.scl = defaultDecimal;    //	伤残率（默认0）
        this.zml = defaultDecimal;    //	杂毛率（默认0）
    }
}