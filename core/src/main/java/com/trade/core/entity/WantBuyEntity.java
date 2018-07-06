package com.trade.core.entity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 求购表(want_buy)
 * 
 * @author lx
 * @since 2018-06-29 17:42:15
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "want_buy")
public class WantBuyEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7529388292104106994L;

    /** 求购id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 账号id */
    @Column(name = "account_id", length = 18)
	@JSONField(name = "account_id", ordinal = 20)
    private String accountId;

    /** 猪源品类（生猪，仔猪，种猪，白条） */
    @Column(name = "product_category", length = 10)
	@JSONField(name = "product_category", ordinal = 30)
    private String productCategory;

    /** 猪源品种（外三元，内三元，土杂猪，外二元，内二元，藏香猪，二元母猪，长白母猪，长白公猪，大白母猪，大白公猪，杜洛克母猪，杜洛克公猪，皮特兰母猪，皮特兰公猪） */
    @Column(name = "product_variety", length = 20)
	@JSONField(name = "product_variety", ordinal = 40)
    private String productVariety;

    /** 最小估重（公斤/头） */
    @Column(name = "min_weight", length = 10)
	@JSONField(name = "min_weight", ordinal = 50)
    private Integer minWeight;

    /** 最大估重（公斤/头） */
    @Column(name = "max_weight", length = 10)
	@JSONField(name = "max_weight", ordinal = 60)
    private Integer maxWeight;

    /** 配送方式（0=买家拉猪（默认），1=卖家送猪） */
    @Column(name = "delivery_type", length = 10)
	@JSONField(name = "delivery_type", ordinal = 70)
    private Integer deliveryType;

    /** 猪源品系（默认空，[美系，加系，丹系，台系，英系，法系]） */
    @Column(name = "product_strain", length = 10)
	@JSONField(name = "product_strain", ordinal = 80)
    private String productStrain;

    /** 求购数量 */
    @Column(name = "buy_num", length = 10)
	@JSONField(name = "buy_num", ordinal = 90)
    private Integer buyNum;

    /** 单价 */
    @Column(name = "unit_price", length = 11)
	@JSONField(name = "unit_price", ordinal = 100)
    private BigDecimal unitPrice;

    /** 价格单位（单位：生猪 元/公斤  仔猪or种猪or白条 元/头） */
    @Column(name = "price_unit", length = 10)
	@JSONField(name = "price_unit", ordinal = 110)
    private String priceUnit;

    /** 有效截止日期（格式：2020-06-19） */
    @Column(name = "valid_date", length = 10)
	@JSONField(name = "valid_date", ordinal = 120)
    private String validDate;

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

    /** 求购说明 */
    @Column(name = "buy_explain", length = 300)
	@JSONField(name = "buy_explain", ordinal = 160)
    private String buyExplain;

    /** 联系人 */
    @Column(name = "contacts", length = 20)
	@JSONField(name = "contacts", ordinal = 170)
    private String contacts;

    /** 联系人手机号 */
    @Column(name = "contact_phone", length = 20)
	@JSONField(name = "contact_phone", ordinal = 180)
    private String contactPhone;

    /** 详细地址 */
    @Column(name = "address", length = 100)
	@JSONField(name = "address", ordinal = 190)
    private String address;

    /** 创建时间 */
    @Column(name = "create_time", length = 26)
	@JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 200)
    private Date createTime;

}