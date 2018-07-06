package constants;

/**
 * 返回数据枚举
 *
 * @author lx
 * @since 2018-06-07 14:24:27
 */
@SuppressWarnings("unused")
public enum ResponseEnum {
    SUCCESS(0, "请求成功"),
    FAIL(1, "请求失败"),
    SESSIONOUT(2, "请求超时，请重新登录"),
    EXCEPTION(3, "系统繁忙，请稍后再试"),
    INVALID_PARAM(4, "参数错误"),
    EXISTS(5, "数据已存在");

    private Integer status;
    private String msg;

    ResponseEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    // 普通方法
    public static String getMsg(Integer status) {
        for (ResponseEnum r : ResponseEnum.values()) {
            if (r.getStatus().equals(status)) {
                return r.msg;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
