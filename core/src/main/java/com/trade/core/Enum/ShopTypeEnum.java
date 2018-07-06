package com.trade.core.Enum;

/**
 * 店铺类型枚举
 *
 * @author lx
 * @since 2018-6-19 15:16:37
 */
@SuppressWarnings("unused")
public enum ShopTypeEnum {
    个人店铺(0, "个人店铺"),
    企业店铺(1, "企业店铺");

    private Integer code;
    private String name;

    ShopTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (ShopTypeEnum r : ShopTypeEnum.values()) {
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
