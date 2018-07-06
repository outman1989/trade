package com.trade.core.Enum;

/**
 * 公告类型枚举
 *
 * @author lx
 * @since 2018-7-3 17:18:44
 */
@SuppressWarnings("unused")
public enum NoticeTypeEnum {
    系统公告(0, "系统公告"),
    精彩活动(1, "精彩活动");

    private Integer code;
    private String name;

    NoticeTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (NoticeTypeEnum r : NoticeTypeEnum.values()) {
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
