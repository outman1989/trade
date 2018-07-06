package com.trade.core.Enum;

/**
 * 渠道枚举
 *
 * @author lx
 * @since 2018-6-9 09:08:53
 */
@SuppressWarnings("unused")
public enum ChannelEnum {
    WEB("web", "网页"),
    WAP("wap", "移动端"),
    ANDROID("android", "安卓"),
    IOS("ios", "苹果"),
    WEIXIN("weixin", "微信"),
    ADMIN("admin", "运营");

    private String code;
    private String name;

    ChannelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(String code) {
        for (ChannelEnum r : ChannelEnum.values()) {
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
