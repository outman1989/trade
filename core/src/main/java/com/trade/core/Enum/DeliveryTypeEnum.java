package com.trade.core.Enum;

/**
 * 配送方式枚举
 *
 * @author lx
 * @since 2018-6-20 15:00:40
 */
@SuppressWarnings("unused")
public enum DeliveryTypeEnum {
    买家拉猪(0, "买家拉猪"),
    卖家送猪(1, "卖家送猪");

    private Integer code;
    private String name;

    DeliveryTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (DeliveryTypeEnum r : DeliveryTypeEnum.values()) {
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
