package util;

import constants.BaseConfig;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * 请求加密工具类
 *
 * @author 禹光彩
 */
public class EncryptUtil {

    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * aes解密
     *
     * @param encrypt 内容
     */
    public static String aesDecrypt(String encrypt) throws Exception {
        return aesDecrypt(encrypt, BaseConfig.AES_KEY);
    }

    /**
     * aes加密
     */
    public static String aesEncrypt(String content) {
        try {
            return aesEncrypt(content, BaseConfig.AES_KEY);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return String 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     *
     * @param bytes 待编码的byte[]
     * @return String 编码后的base64 code
     */
    public static String base64Encode(byte[] bytes) {
        String str = "";
        try {
            bytes = Base64.encodeBase64(bytes);
            str = new String(bytes, "UTF-8");
        } catch (Exception e) {
            LogUtil.error("base64Encode", e);
        } finally {
            return str;
        }
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    public static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return byte[] 加密后byte[]
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    /**
     * AES加密为base 64 code
     *
     * @param content     待加密的内容
     * @param encryptKey= 加密密钥
     * @return String 加密后的base64 code
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return String 解密后的
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes, "UTF-8");
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的 string
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws Exception {
//        String content = "我爱你";
//        System.out.println("加密前：" + content);
//        System.out.println("加密密钥和解密密钥：" + BaseConfig.AES_KEY);
//        String encrypt = EncryptUtil.aesEncrypt(content, BaseConfig.AES_KEY);
//        System.out.println("加密后：" + encrypt);
//        String decrypt = aesDecrypt(encrypt, BaseConfig.AES_KEY);
//        System.out.println("解密后：" + decrypt);
    }

}
