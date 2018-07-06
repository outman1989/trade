package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseUtil {

    /**
     * 字符串转实体--lx--2018-2-8 11:42:08
     */
    public static <T> T getResult(String url,String data,Class<T> clazz){
        try {
            data = EncryptUtil.aesEncrypt(data);
            String res = HttpClientUtil.postStream(url, data);
            //加密字符串解密得到json格式{isSuccess:0,result:Object}
            res = EncryptUtil.aesDecrypt(res);
            JSONObject obj = JSON.parseObject(res);
            if(obj.getIntValue("isSuccess")==0){
                return (T) JSON.parseObject(obj.getString("result"), clazz);
            }
        }catch(Exception e){
            LogUtil.error("字符串转实体",e);
            return null;
        }
        return null;
    }
    /**
     * 字符串转实体--lx--2018-2-8 11:42:08
     */
    public static <T> T getRes(String url, Map<String,String> map, Class<T> clazz){
        try {
            String js = EncryptUtil.aesEncrypt(JSON.toJSONString(map));
            String res = HttpClientUtil.postStream(url,js);
            if (StringUtil.isNotEmpty(res)) {
                //加密字符串解密得到json格式{isSuccess:0,result:Object}
                res = EncryptUtil.aesDecrypt(res);
                JSONObject obj = JSON.parseObject(res);
                if(obj.getIntValue("isSuccess")==0){
                    return (T) JSON.parseObject(obj.getString("result"), clazz);
                }
            } else {
                return null;
            }
        }catch(Exception e){
            LogUtil.error("字符串转实体",e);
            return null;
        }
        return null;
    }

    /**
     * 字符串转集合--lx--2018-2-8 11:42:08
     */
    public static <T> List<T> getResToList(String url, Map<String,String> map, Class<T> clazz){
        List<T> list = new ArrayList<T>();
        String str = "";
        try {
            String js = EncryptUtil.aesEncrypt(JSON.toJSONString(map));
            String res = HttpClientUtil.postStream(url,js);
            if (StringUtil.isNotEmpty(res)) {
                //加密字符串解密得到json格式{isSuccess:0,result:Object}
                res = EncryptUtil.aesDecrypt(res);
                JSONObject obj = JSON.parseObject(res);
                if(obj.getIntValue("isSuccess")==0){
                    return JSONArray.parseArray(obj.getString("result"),clazz);
                }
            } else {
                return list;
            }
        }catch(Exception e){
            LogUtil.error("字符串转实体",e);
            return list;
        }
        return list;
    }
    public static void main(String[] args) {
//        String demoJSON="q/XECinsInRYxHOBJrKJtkawPXD0or/TWHrB6FmsXyEQUtvZBRsPXvWvUkkYW9k5WCBsm/NzMnWrPhnG8IKsU5LYe06jbgc+NPcN8nE7HKLlcErfTHi4RnH877AlYvVuZvJdlZaY2K3/i9LvUaYXsR/65CgW119E+TmR9Abcucw=";
    }
}
