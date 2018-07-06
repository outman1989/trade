package com.trade.core.Enum;

/**
 * 消息状态枚举
 *
 * @author lx
 * @since 2018-7-3 17:18:44
 */
@SuppressWarnings("unused")
public enum NewsStateEnum {
    未读(0, "未读"),
    已读(1, "已读"),
    清除(2, "清除");

    private Integer code;
    private String name;

    NewsStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (NewsStateEnum r : NewsStateEnum.values()) {
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
