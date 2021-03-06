package com.trade.core.Enum;

/**
 * 性别枚举
 *
 * @author lx
 * @since 2018-6-9 09:02:36
 */
@SuppressWarnings("unused")
public enum SexEnum {
    保密(0, "保密"),
    男(1, "男"),
    女(2, "女");

    private Integer code;
    private String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (SexEnum r : SexEnum.values()) {
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
