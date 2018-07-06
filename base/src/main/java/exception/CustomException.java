package exception;

/**
 * 自定义异常
 * @author lx
 * @since 2018-06-10 17:27:19
 */
public class CustomException extends Exception {
    private static final long serialVersionUID = -4085737606183514878L;

    public CustomException(String msg) {
        super(msg);
    }

}
