package com.trade.core.Enum;

/**
 * 订单状态枚举
 *
 * @author lx
 * @since 2018-6-26 17:50:22
 */
@SuppressWarnings("unused")
public enum OrderStateEnum {
    等待买家付款(0, "等待买家付款"),
    买家已付款(1, "买家已付款"),
    卖家已发货(2, "卖家已发货"),
    交易完成(3, "交易完成"),
    订单取消(4, "订单取消"),
    买家申请退款(5, "买家申请退款"),
    卖家已退款(6, "卖家已退款"),
    交易关闭(7, "交易关闭"),
    订单异常(99, "订单异常");

    private Integer code;
    private String name;

    OrderStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (OrderStateEnum r : OrderStateEnum.values()) {
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
