package com.trade.core.Enum;

/**
 * 权限类型枚举
 *
 * @author lx
 * @since 2018-6-8 17:53:47
 */
@SuppressWarnings("unused")
public enum RoleTypeEnum {
    普通权限(0, "普通权限"),
    管理员权限(1, "管理员权限");

    private Integer code;
    private String name;

    RoleTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (RoleTypeEnum r : RoleTypeEnum.values()) {
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
