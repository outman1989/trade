package com.trade.core.Enum;

/**
 * 猪源状态枚举
 *
 * @author lx
 * @since 2018-6-20 15:00:40
 */
@SuppressWarnings("unused")
public enum ProductStateEnum {
    下架(0, "下架"),
    上架(1, "上架");

    private Integer code;
    private String name;

    ProductStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (ProductStateEnum r : ProductStateEnum.values()) {
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
