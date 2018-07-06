package util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Base64工具类
 * 
 * @author lx
 * @since 2012-11-26
 */
public class Base64Util {

	/**
	 * Base64加密ByString
	 * 
	 * @param str
	 * @return
	 */
	public static String EncodeByString(String str) {
		if (str == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(str.getBytes());
	}

	/**
	 * Base64加密ByByte
	 * 
	 * @param byteData
	 * @return
	 */
	public static String EncodeByByte(byte[] byteData) {
		if (byteData == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(byteData);
	}

	/**
	 * Base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String Decode(String str) {
		if (str == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(str);
			return new String(b,"utf-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
//		Base64Util base64Util = new Base64Util();
//		System.out.println(base64Util.EncodeByString("like"));
//		System.out.println(base64Util.Decode("bGlrZQ=="));
//		System.out.println(base64Util.EncodeByString("FCD6CEE38031673FBAB59185F8C5B73ACFC30C19CB07BE85F1AA2E56"));
//		System.out.println(base64Util.Decode("RkNENkNFRTM4MDMxNjczRkJBQjU5MTg1RjhDNUI3M0FDRkMzMEMxOUNCMDdCRTg1RjFBQTJFNTY="));
		
	}
}
