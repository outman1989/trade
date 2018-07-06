package com.trade.core.Enum;

/**
 * 优先级状态枚举
 *
 * @author lx
 * @since 2018-6-14 17:55:54
 */
@SuppressWarnings("unused")
public enum PriorityEnum {
    默认(0, "默认"),
    置顶(99, "置顶");

    private Integer code;
    private String name;

    PriorityEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (PriorityEnum r : PriorityEnum.values()) {
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
