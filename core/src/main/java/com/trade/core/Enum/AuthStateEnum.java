package com.trade.core.Enum;

/**
 * 认证状态枚举
 *
 * @author lx
 * @since 2018-6-8 17:53:47
 */
@SuppressWarnings("unused")
public enum AuthStateEnum {
    未认证(0, "未认证"),
    个人认证(1, "个人认证"),
    企业认证(2, "企业认证");

    private Integer code;
    private String name;

    AuthStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (AuthStateEnum r : AuthStateEnum.values()) {
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
