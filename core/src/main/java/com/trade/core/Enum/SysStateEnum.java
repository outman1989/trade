package com.trade.core.Enum;

/**
 * 系统状态枚举
 *
 * @author lx
 * @since 2018-6-8 17:37:47
 */
@SuppressWarnings("unused")
public enum SysStateEnum {

    未激活(0, "未激活"),
    正常(1, "正常"),
    关闭(2, "关闭"),
    冻结(3, "冻结"),
    禁用(4, "禁用"),
    异常(5, "异常"),
    失效(6, "失效"),
    已删除(7, "已删除");

    private Integer code;
    private String name;

    SysStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (SysStateEnum r : SysStateEnum.values()) {
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
