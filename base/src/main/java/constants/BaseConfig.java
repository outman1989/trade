package constants;

import java.math.BigDecimal;

/**
 * 基础配置常量
 *
 * @author lx
 * @since 2018-5-29 11:57:51
 */
public class BaseConfig {

    public static final Integer DEFAULT_LOG_INDEX = 3;//默认日志层级
    public static final String PROJECT_NAME = "trade";//项目名
    public static final String ENCODING = "UTF-8";//默认字符集
    public static final String SESSION_ACCOUNT = PROJECT_NAME + "_account";//账号session key
    public static final Integer SESSION_ACCOUNT_LIMIT = 7200;//账号session 默认超时时间（60 * 60 * 2）= 7200 = 2小时
    public static final String API_SUFFIX = ".do";//api后缀
    public static final String API_WEB = "/web";//api web前缀
    public static final String API_APP = "/app";//api app前缀
    public static final String CONTENT_TYPE_DEFAULT = "text/html; charset=utf-8";//默认请求数据类型
    public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";//json请求数据类型
    public static final String AES_KEY = "1234567812345678";//aes秘钥
    public static final String MD5_KEY_ACCOUNT = "mUyA_2018!@#_lx";//MD5秘钥--账号密码加密
    public static final String SPECIAL_CHARACTER = "[`~!@#$%^&*()+=|{}':;\',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";//特殊字符
    public static final String SPLIT_SIGN = "|";//文件分隔符号
    public static final String SPLIT_SIGN_COMMA = ",";//隔符号-英文逗号
    public static final String UPLOAD_IMG = "upload_img";//上传文件参数名-图片
    public static final Long UPLOAD_IMG_SIZE = 5*1024*1024L;//上传文件参数-图片-大小（单位：byte 字节）
    public static final String UPLOAD_IMG_FORMAT = ".+(.jpeg|.JPEG|.jpg|.JPG|.png|.PNG)$";//图片格式正则
    public static final String UPLOAD_VIDEO = "upload_video";//上传文件参数名-视频
    public static final Long UPLOAD_VIDEO_SIZE = 15*1024*1024L;//上传文件参数-视频-大小（单位：byte 字节）
    public static final String UPLOAD_VIDEO_FORMAT = ".+(.avi|.AVI|.mp4|.MP4|.rmvb|.RMVB|.mpg|.MPG|.mov|.MOV)$";//视频格式正则
    public static final String UPLOAD_FILE = "upload_file";//上传文件参数名-文件
    public static final Long UPLOAD_FILE_SIZE = 5*1024*1024L;//上传文件参数-文件-大小（单位：byte 字节）
    public static final BigDecimal DEFAULT_AMOUNT  = new BigDecimal(0);;//默认金额
    public static final String LOG_SYS_ACCOUNT = "system";//日志系统账户名




}
