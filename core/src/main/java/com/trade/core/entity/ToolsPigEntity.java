package com.trade.core.entity;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.util.Date;

/**
 * 收猪工具表(tools_pig)
 * 
 * @author lx
 * @since 2018-07-03 12:07:44
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tools_pig")
public class ToolsPigEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4657958603945193264L;

    /** 收猪工具id */
    @Id
	@GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 18)
	@JSONField(name = "id", ordinal = 10)
    private String id;

    /** 车牌号 */
    @Column(name = "plate_number", length = 20)
	@JSONField(name = "plate_number", ordinal = 20)
    private String plateNumber;

    /** 车型 */
    @Column(name = "car_model", length = 20)
	@JSONField(name = "car_model", ordinal = 30)
    private String carModel;

    /** 车长 */
    @Column(name = "car_length", length = 10)
	@JSONField(name = "car_length", ordinal = 40)
    private String carLength;

    /** 载重 */
    @Column(name = "car_load", length = 10)
	@JSONField(name = "car_load", ordinal = 50)
    private String carLoad;

    /** 联系人 */
    @Column(name = "contacts", length = 20)
	@JSONField(name = "contacts", ordinal = 60)
    private String contacts;

    /** 联系人手机号 */
    @Column(name = "contact_phone", length = 20)
	@JSONField(name = "contact_phone", ordinal = 70)
    private String contactPhone;

    /** 所在省 */
    @Column(name = "province", length = 30)
	@JSONField(name = "province", ordinal = 80)
    private String province;

    /** 所在市 */
    @Column(name = "city", length = 30)
	@JSONField(name = "city", ordinal = 90)
    private String city;

    /** 所在县区 */
    @Column(name = "area", length = 30)
	@JSONField(name = "area", ordinal = 100)
    private String area;

    /** 详细地址 */
    @Column(name = "address", length = 100)
	@JSONField(name = "address", ordinal = 110)
    private String address;

    /** 工具说明 */
    @Column(name = "tools_explain", length = 300)
	@JSONField(name = "tools_explain", ordinal = 120)
    private String toolsExplain;

    /** 创建时间 */
    @Column(name = "create_time", length = 26)
	@JSONField(name = "create_time", format = "yyyy-MM-dd HH:mm:ss", ordinal = 130)
    private Date createTime;

}