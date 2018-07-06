package util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES 工具类
 */
public class AESUtil {
	
	/** 
	 * 将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {  
	        if (hexStr.length() < 1)  
	                return null;  
	        byte[] result = new byte[hexStr.length()/2];  
	        for (int i = 0;i< hexStr.length()/2; i++) {
	                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                result[i] = (byte) (high * 16 + low);  
	        }  
	        return result;  
	}
	
	/** 
	 * 将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

    /** 
     * AES加密
     * @param content
     * @param password
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            byte[] raw = password.getBytes("UTF-8");
            if (raw.length != 16) {
                throw new IllegalArgumentException("Invalid key size." + password);
            }

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16])); // zero IV
            return cipher.doFinal(content.getBytes("UTF-8"));
        } catch (Exception e) {
        }
        return null;
    }
    
    /** 
     * AES解密
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(byte[] content, String password) {
        try {
            byte[] raw = password.getBytes("UTF-8");
            if (raw.length != 16) {
                throw new IllegalArgumentException("Invalid key size. " + password);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(content);

            return new String(original, "UTF-8");
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }

    
//    byte[] srcIv=new byte[16];
    
    /** 
     * AES加密（Base64编码）
     * @param content
     * @param key
     * @return
     */
    public static String EncryptByBase64(String content, String key) {
    	String rc = "";
    	try {
    		if(key != null && key.length() == 16){
    			byte[] raw=key.getBytes();
    			SecretKeySpec skeySpec=new SecretKeySpec(raw,"AES"); 
    			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    			IvParameterSpec iv = new IvParameterSpec(new byte[16]);
    			cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
    			byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
    			Base64.Encoder encoder = Base64.getEncoder();
    			byte[] enStr = encoder.encode(encrypted);
    			rc = new String(enStr);
//    			rc = new BASE64Encoder().encode(encrypted);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
    }
    
    /**
     * AES解密（Base64编码）
     * @param content
     * @param key
     * @return
     */
    public static String DecryptByBase64(String content, String key){
    	String rc = "";
        try {
            if (key != null && key.length() == 16) {
            	byte[] raw = key.getBytes("ASCII");
            	SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            	IvParameterSpec iv = new IvParameterSpec(new byte[16]);
            	cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//            	byte[] encrypted1 = new BASE64Decoder().decodeBuffer(content);//
            	Base64.Decoder decoder = Base64.getDecoder();
            	byte[] encrypted1 = decoder.decode(content);
            	byte[] original = cipher.doFinal(encrypted1);
            	rc = new String(original,"UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ""; //解密失败返回空值
        }
        return rc;
    }
    
//    /**
//     * 小程序AES-128-CBC PKCS#7解密
//     * 参数均需要Base64解码
//     * @param data 密文
//     * @param sessionKey 秘钥
//     * @param iv 初始向量
//     * @return
//     */
//	public static String Decrypt128CBCPKCS7(String data,String sessionKey,String iv){
//    	String rc = "";
//    	try {
//    		BASE64Decoder decoder = new BASE64Decoder();
//			byte[] datas = decoder.decodeBuffer(data);//解码密文
//			byte[] keys = decoder.decodeBuffer(sessionKey);//解码秘钥
//			byte[] ivs = decoder.decodeBuffer(iv);//解码初始向量
//			// 如果密钥不足16位补足
//			int base = 16;
//			if (keys.length % base != 0) {
//				int groups = keys.length / base + (keys.length % base != 0 ? 1 : 0);
//				byte[] temp = new byte[groups * base];
//				Arrays.fill(temp, (byte) 0);
//				System.arraycopy(keys, 0, temp, 0, keys.length);
//				keys = temp;
//			}
//			// 初始化
//			Security.addProvider(new BouncyCastleProvider());
//			Key key = new SecretKeySpec(keys, "AES");// 转化成JAVA的密钥格式
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");//初始化cipher
//			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
//			byte[] res = cipher.doFinal(datas);
//			rc = new String(res, "UTF-8");
//		} catch (Exception e) {
//			e.printStackTrace();
//			rc = "";
//		}
//    	return rc;
//    }
    
    
	public static void main(String[] args) {
//		  String content = 	"CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
//					"QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
//					"9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
//					"3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
//					"NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
//					"evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
//					"/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
//					"u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
//					"/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
//					"8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
//					"C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
//					"6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
//					"/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
//					"lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
//					"oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
//					"20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
//					"Db/XcxxmK01EpqOyuxINew==";
//		  String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
//		  String iv = "r7BXXKkLb8qrSNn05n0qiA==";
//		  String res = Decrypt128CBCPKCS7(content,sessionKey,iv);
//		  System.out.println(res);
//		Base64Util base64Util = new Base64Util();
//		AESUtil a = new AESUtil();
//		String ss="SQbIVAqrbDJ9oaRyuTw5WlUAUZSZWqjcyKcTPKSXhh8wtU7E16RTwhG4jUidDe/v";
//		String z = AESUtil.DecryptByBase64(ss, "N6AG2WHLH74S5WC5");
//		String z = "{\"thirdCustId\":\"laidanji110\",\"orderNo\":\"ZF201704250001\",\"tradeAmount\":20.0,\"operator\":\"archer\"}";
//		System.out.println(z);
//		String s = AESUtil.EncryptByBase64(z, "1234567890123456");
//		System.out.println(s);
//		System.out.println(AESUtil.DecryptByBase64(s, "1234567890123456"));
//		String sss=base64Util.Decode(ss);
//		System.out.println(sss);
//		System.out.print(decrypt(sss.getBytes(),"1234567890123456"));
	    //String aaa=new Base64Util().Decode(ss);
	    //System.out.println(aaa);
		//String a =AESUtil.decrypt(new Base64Util().Decode(ss).getBytes(),BaseConfig.AES_VALUE);
		//System.out.print(a);
		//String content="abcdefghigklmnopqrstuvwxyz0123456789";
//      InputStream in = System.in;
//      BufferedReader br = new BufferedReader(new InputStreamReader(in));
//      while(br.readLine() != null){
//          System.out.println();
//      }
//      System.out.println(encrypt(content, "1234567890123456"));
      //System.out.println(new String(base64Util.EncodeByByte(encrypt(content, "1234567890123456"))));
//      System.out.println(new String(base64Util.Decode("qF8ie9UhJkh6m0Dp5GIBme0dOu57K3V67X1RgJ57ZouODSz20eDuCP1QAiO2Gp3ZsV9OOhIry7qj5fD78c+0x+onppXOQnFbxHQcNFjBM81D/2EybR6K+hJVLw+7lgya13+33l4chTTEr0ot8E+Pk2OMD4Blx3nHIcOUQ2xyNGI6JNI4POo86tXAE0cQinXPyYdoC39VIKnZRFwAJNjgHpYW/psqTQnF/Sa/HYl1pkNv21356b72EHCPX0yaCtodC0Gbpcjm6K74v3+UDK2N2a4/VRFD6kUpRG1jdEwCFzBhQn8N01abStvx7rgXvzndwneBvDXRTfOjSMcEpJGIcDNUkrmozqGHKSxB0iUyh+3zddXdmQKm2mPxx6jZidSBL33cdnt6Ldw7RWrvSvrk575Yg5lJ+fq4DdWbICU2BTS6rXcNGv1qDAb4y3g64rFEj3+xBOhoUiQdYMVXHDxuNwcV6jAdVPe5BJltVaEFp13/+20rGgGBLVkU4Qj5EwXMXCgOKYaQcJ4RyfWU8vVPlknNG0m6jBwN3kO1whmOtP611RxfG/fyUAxSTjbjpkTHBxzMmjn0LiVx9c3RhWHngsdlHCWqNJ9HhzkW2FHeppNfpE0KBIyGi9oABBO0xxmuk3nnKKlmMM/08+315dw3DxoTIBtVbkiN5YAszZsA4vTXSH4/7FIz1lj5w+f5SomldXJzJ3Kni4x9KffIq4b2ye3rKyCSWiektCiod37RNYGJRWlcFNY3e9SPnej67xDJBxtQy3sA/X1vVpxnuuyZA7XZ+P0lwmHLh/mjJA8v4nw=")));
  }

}
