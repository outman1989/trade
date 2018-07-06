package exception;

/**
 * session异常
 * @author lx
 * @since 2018-06-10 17:27:19
 */
public class SessionException extends Exception {
    private static final long serialVersionUID = -9112976975459731769L;

    public SessionException(String msg) {
        super(msg);
    }
}
