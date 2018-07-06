package com.trade.core.Enum;

/**
 * 供应类型枚举
 *
 * @author lx
 * @since 2018-6-20 15:00:40
 */
@SuppressWarnings("unused")
public enum SupplyTypeEnum {
    库存(0, "库存"),
    长期供应(1, "长期供应");

    private Integer code;
    private String name;

    SupplyTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (SupplyTypeEnum r : SupplyTypeEnum.values()) {
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
