package com.trade.core.Enum;

/**
 * 优惠类型枚举
 *
 * @author lx
 * @since 2018-6-26 17:50:22
 */
@SuppressWarnings("unused")
public enum DiscountTypeEnum {
    无优惠(0, "无优惠"),
    积分抵扣(1, "积分抵扣"),
    优惠券(2, "优惠券");

    private Integer code;
    private String name;

    DiscountTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (DiscountTypeEnum r : DiscountTypeEnum.values()) {
            if (r.getCode().equals(code)) {
                return r.name;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
