package com.trade.core.Enum;

/**
 * 店铺状态枚举
 *
 * @author lx
 * @since 2018-6-13 15:54:25
 */
@SuppressWarnings("unused")
public enum ShopStateEnum {
    未认证(0, "未认证"),
    普通认证(1, "普通认证"),
    品牌认证(2, "品牌认证");

    private Integer code;
    private String name;

    ShopStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (ShopStateEnum r : ShopStateEnum.values()) {
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
