package com.trade.core.Enum;

/**
 * 支付状态枚举
 *
 * @author lx
 * @since 2018-6-26 17:50:22
 */
@SuppressWarnings("unused")
public enum PayStateEnum {
    未支付(0, "未支付"),
    已支付(1, "已支付"),
    退款中(2, "退款中"),
    已退款(3, "已退款");

    private Integer code;
    private String name;

    PayStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (PayStateEnum r : PayStateEnum.values()) {
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
