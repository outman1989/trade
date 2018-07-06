package util;

import com.alibaba.fastjson.JSONObject;
import constants.ResponseEnum;
import exception.CustomException;

/**
 * 验证工具类
 *
 * @author lx
 * @since 2018-06-10 17:12:39
 */
public class ValidateUtil {

    /**
     * 非空验证
     *
     * @param jo     json实体
     * @param params 参数名...
     * @throws CustomException 自定义异常
     */
    public static void required(JSONObject jo, String... params) throws CustomException {
        if (ObjectUtil.isEmpty(jo)) {
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg());
        }
        for (String s : params) {
            if (StringUtil.isEmpty(jo.getString(s))) {
                throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg() + "-" + s);
            }
        }
    }

    /**
     * 非空验证（单个参数，有返回值）
     *
     * @param jo    json实体
     * @param param 参数名
     * @return 参数值
     * @throws CustomException 自定义异常
     */
    public static String requiredSingle(JSONObject jo, String param) throws CustomException {
        if (ObjectUtil.isEmpty(jo)) {
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg());
        }
        if (StringUtil.isEmpty(jo.getString(param))) {
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg() + "-" + param);
        }
        return jo.getString(param);
    }

    /**
     * 整数验证
     */
    public static Integer numValidate(JSONObject jo, String param) throws CustomException {
        if (ObjectUtil.isEmpty(jo)) {
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg());
        }
        if (StringUtil.isEmpty(jo.getString(param))) {
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg() + "-" + param);
        }
        if(ObjectUtil.isEmpty(jo.getInteger(param))){
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg() + "-" + param);
        }
        return jo.getInteger(param);
    }

    /**
     * 正整数验证
     */
    public static Integer positiveInteger(JSONObject jo, String param) throws CustomException {
        Integer res = numValidate(jo,param);
        if(res<= 0){
            throw new CustomException(ResponseEnum.INVALID_PARAM.getMsg() + "-" + param);
        }
        return res;
    }




}
