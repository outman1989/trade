package com.trade.core.Enum;

/**
 * 消息类型枚举
 *
 * @author lx
 * @since 2018-7-3 17:18:44
 */
@SuppressWarnings("unused")
public enum NewsTypeEnum {
    系统消息(0, "系统消息");

    private Integer code;
    private String name;

    NewsTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (NewsTypeEnum r : NewsTypeEnum.values()) {
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
