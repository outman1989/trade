package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import entity.Token;

import java.util.Date;

public class TokenUtil {

    /**
     * token加密
     * @param token
     * @param tokenKey
     * @return
     */

    public static String tokenEncrypt(Token token, String tokenKey) {
        String tokenStr = "";
        try {
            tokenStr = EncryptUtil.aesEncrypt(JSONObject.toJSONString(token), tokenKey);
        } catch (Exception e) {
            LogUtil.error("token加密", e);
        }
        return tokenStr;
    }

    /**
     * token解密
     *
     * @param tokenStr
     * @return
     */
    public static Token tokenDecrypt(String tokenStr, String tokenKey) {
        Token token = null;
        try {
            tokenStr = EncryptUtil.aesDecrypt(tokenStr, tokenKey);
            token = JSON.parseObject(tokenStr, Token.class);
        } catch (Exception e) {
            LogUtil.error("token解密", e);
        }
        return token;
    }

    /**
     * 验证token是否超时
     *
     * @author ygc
     * @since 2018-1-23 13:49:58
     */
    public static int tokenSessionOut(Token token) {
        Date validate = token.getValiday();
        if (validate.getTime() - new Date().getTime() <= 0) {
            return 2;//请求超时
        }
        return 0;
    }


}
