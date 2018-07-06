package com.trade.core.entity.VO;

import com.alibaba.fastjson.annotation.JSONField;
import com.trade.core.Enum.DeliveryTypeEnum;
import com.trade.core.Enum.ProductStateEnum;
import com.trade.core.Enum.SupplyTypeEnum;
import com.trade.core.Enum.SysStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 猪源VO
 * 
 * @author lx
 * @since 2018-6-21 16:23:00
 */
@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("unused")
public class ProductPigVO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2759604052374014585L;

    /** 猪源id */
    @Column(name = "id", unique = true, nullable = false, length = 18)
    @JSONField(ordinal = 10)
    private String id;

    /** 账号id */
    @Column(name = "account_id",  length = 18)
    @JSONField(name = "account_id", ordinal = 20)
    private String accountId;

    /** 店铺id */
    @Column(name = "shop_id",  length = 18)
    @JSONField(name = "shop_id", ordinal = 30)
    private String shopId;

    /** 猪场id */
    @Column(name = "piggery_id",  length = 18)
    @JSONField(name = "piggery_id", ordinal = 40)
    private String piggeryId;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @Column(name = "sys_state",  length = 10)
    @JSONField(name = "sys_state", ordinal = 50)
    private Integer sysState;

    /** 状态[0=未激活，1=正常（默认），2=关闭，3=冻结，4=禁用，5=异常，6=失效，7=已删除] */
    @JSONField(name = "sys_state_cn", ordinal = 50)
    private String sysStateCn;

    /** 猪源状态（0=下架，1=上架） */
    @Column(name = "product_state",  length = 10)
    @JSONField(name = "product_state", ordinal = 60)
    private Integer productState;

    /** 猪源状态（0=下架，1=上架） */
    @JSONField(name = "product_state_cn", ordinal = 60)
    private String productStateCn;

    /** 猪源品类（生猪，仔猪，种猪，白条） */
    @Column(name = "product_category",  length = 10)
    @JSONField(name = "product_category", ordinal = 70)
    private String productCategory;

    /** 猪源品种（外三元，内三元，土杂猪，外二元，内二元，藏香猪，二元母猪，长白母猪，长白公猪，大白母猪，大白公猪，杜洛克母猪，杜洛克公猪，皮特兰母猪，皮特兰公猪） */
    @Column(name = "product_variety",  length = 20)
    @JSONField(name = "product_variety", ordinal = 80)
    private String productVariety;

    /** 猪源品系（默认空，[美系，加系，丹系，台系，英系，法系]） */
    @Column(name = "product_strain",  length = 10)
    @JSONField(name = "product_strain", ordinal = 90)
    private String productStrain;

    /** 猪源场地（默认空，[国家核心育种场，原种场，二级扩繁场]） */
    @Column(name = "product_place",  length = 20)
    @JSONField(name = "product_place", ordinal = 100)
    private String productPlace;

    /** 猪源级别（默认空，[标准级，测定级]） */
    @Column(name = "product_level",  length = 20)
    @JSONField(name = "product_level", ordinal = 110)
    private String productLevel;

    /** 剩余库存 */
    @Column(name = "surplus_stock",  length = 10)
    @JSONField(name = "surplus_stock", ordinal = 120)
    private Integer surplusStock;

    /** 已售库存 */
    @Column(name = "sold_stock",  length = 10)
    @JSONField(name = "sold_stock", ordinal = 130)
    private Integer soldStock;

    /** 供应类型（0=库存（默认），1=长期供应） */
    @Column(name = "supply_type",  length = 10)
    @JSONField(name = "supply_type", ordinal = 140)
    private Integer supplyType;

    /** 供应类型（0=库存（默认），1=长期供应） */
    @JSONField(name = "supply_type_cn", ordinal = 140)
    private String supplyTypeCn;

    /** 优选(0=false（默认），1=true) */
    @Column(name = "preference",  length = 1)
    @JSONField(name = "preference", ordinal = 150)
    private boolean preference;

    /** 保障金(0=false（默认），1=true) */
    @Column(name = "ensured",  length = 1)
    @JSONField(name = "ensured", ordinal = 160)
    private boolean ensured;

    /** 冻结保障金金额 */
    @Column(name = "frozen_ensured",  length = 11)
    @JSONField(name = "frozen_ensured", ordinal = 170)
    private BigDecimal frozenEnsured;

    /** ID猪(0=false（默认），1=true) */
    @Column(name = "id_pig",  length = 1)
    @JSONField(name = "id_pig", ordinal = 180)
    private boolean idPig;

    /** 最小估重（公斤/头） */
    @Column(name = "min_weight",  length = 10)
    @JSONField(name = "min_weight", ordinal = 190)
    private Integer minWeight;

    /** 最大估重（公斤/头） */
    @Column(name = "max_weight",  length = 10)
    @JSONField(name = "max_weight", ordinal = 200)
    private Integer maxWeight;

    /** 价格（单位：生猪 X元/公斤  仔猪or种猪 X元/头） */
    @Column(name = "unit_price",  length = 11)
    @JSONField(name = "unit_price", ordinal = 210)
    private BigDecimal unitPrice;

    /** 超出，每公斤加 X 元 */
    @Column(name = "over_price",  length = 10)
    @JSONField(name = "over_price", ordinal = 220)
    private BigDecimal overPrice;

    /** 低于，每公斤减 X 元 */
    @Column(name = "less_price",  length = 10)
    @JSONField(name = "less_price", ordinal = 230)
    private BigDecimal lessPrice;

    /** 出售日期（格式：2020-06-19，长期供应时不限出售日期） */
    @Column(name = "sale_date",  length = 10)
    @JSONField(name = "sale_date", ordinal = 240)
    private String saleDate;

    /** 配送方式（0=买家拉猪（默认），1=卖家送猪） */
    @Column(name = "delivery_type",  length = 10)
    @JSONField(name = "delivery_type", ordinal = 250)
    private Integer deliveryType;

    /** 配送方式（0=买家拉猪（默认），1=卖家送猪） */
    @JSONField(name = "delivery_type_cn", ordinal = 250)
    private String deliveryTypeCn;

    /** 控料(0=false（默认），1=true) */
    @Column(name = "control_material",  length = 10)
    @JSONField(name = "control_material", ordinal = 260)
    private boolean controlMaterial;

    /** 猪源图片（只支持 *.jpg,*.jpeg,*.png格式，最多上传3张图） */
    @Column(name = "product_img",  length = 500)
    @JSONField(name = "product_img", ordinal = 270)
    private String productImg;

    /** 猪源视频（文件格式avi，mp4，rmvb，mpg，mov，视频最多上传1份） */
    @Column(name = "product_video",  length = 200)
    @JSONField(name = "product_video", ordinal = 280)
    private String productVideo;

    /** 猪源描述 */
    @Column(name = "product_describe",  length = 300)
    @JSONField(name = "product_describe", ordinal = 290)
    private String productDescribe;

    /** 创建时间 */
    @Column(name = "create_time")
    @JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 300)
    private Date createTime;

    public void setSysState(Integer sysState) {
        this.sysState = sysState;
        this.sysStateCn = SysStateEnum.getName(sysState);
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
        this.productStateCn = ProductStateEnum.getName(productState);
    }

    public void setSupplyType(Integer supplyType) {
        this.supplyType = supplyType;
        this.supplyTypeCn = SupplyTypeEnum.getName(supplyType);
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
        this.deliveryTypeCn = DeliveryTypeEnum.getName(deliveryType);
    }
}