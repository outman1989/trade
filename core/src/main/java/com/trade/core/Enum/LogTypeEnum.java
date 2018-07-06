package com.trade.core.Enum;

/**
 * 日志类型枚举
 *
 * @author lx
 * @since 2018-6-28 10:54:19
 */
@SuppressWarnings("unused")
public enum LogTypeEnum {
    默认(0, "默认"),
    买家(1, "买家"),
    卖家(2, "卖家"),
    运营(3, "运营");

    private Integer code;
    private String name;

    LogTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (LogTypeEnum r : LogTypeEnum.values()) {
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
