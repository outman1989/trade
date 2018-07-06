package com.trade.core.Enum;

/**
 * 证件类型枚举
 *
 * @author lx
 * @since 2018-6-10 14:21:56
 */
@SuppressWarnings("unused")
public enum CardTypeEnum {
    身份证("IC", "身份证");

    private String code;
    private String name;

    CardTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (CardTypeEnum r : CardTypeEnum.values()) {
            if (r.getCode().equals(code)) {
                return r.name;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
