package entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import constants.BaseConfig;
import constants.ResponseEnum;
import exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import util.LogUtil;
import util.ObjectUtil;
import util.StringUtil;

import java.io.Serializable;

/**
 * 返回数据实体类
 *
 * @author lx
 * @since 2018-6-7 14:05:47
 */

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = -1817349608255048450L;

    @JSONField(ordinal = 1)
    private Integer status;// 结果状态 0=成功，1=失败，2=请求超时，3=系统异常
    @JSONField(ordinal = 2)
    private String msg;// 错误信息，可空。
    @JSONField(ordinal = 3)
    private T data;// 返回结果。需要返回的结果序列化成json传入该字段。

    public boolean SUCCESS(){
        return this.status == 0;
    }

    public boolean NOT_SUCCESS(){
        return !(this.status == 0);
    }

    /**
     * 返回成功
     */
    public ResponseData<T> success() {
        return success(null, ResponseEnum.SUCCESS.getMsg());
    }
    /**
     * 返回成功
     *
     * @param data 返回数据
     */
    public ResponseData<T> success(T data) {
        return success(data, ResponseEnum.SUCCESS.getMsg());
    }

    /**
     * 返回成功
     *
     * @param data       返回数据
     * @param successMsg 成功信息
     */
    public ResponseData<T> success(T data, String successMsg) {
        this.status = ResponseEnum.SUCCESS.getStatus();
        this.msg = successMsg;
        this.data = data;
        return this;
    }

    /**
     * 返回无效参数
     */
    public ResponseData<T> invalidParam() {
        return invalidParam(ResponseEnum.INVALID_PARAM.getMsg());
    }

    /**
     * 返回无效参数
     */
    public ResponseData<T> invalidParam(CustomException e) {
        return invalidParam(e.getMessage());
    }

    /**
     * 返回无效参数
     */
    public ResponseData<T> invalidParam(String invalidParamMsg) {
        this.status = ResponseEnum.INVALID_PARAM.getStatus();
        this.msg = StringUtil.isEmpty(invalidParamMsg)?ResponseEnum.INVALID_PARAM.getMsg():invalidParamMsg;
        return this;
    }

    /**
     * 返回已存在
     */
    public ResponseData<T> exists() {
        return exists(ResponseEnum.EXISTS.getMsg());
    }

    /**
     * 返回已存在
     *
     * @param existsMsg 错误信息
     */
    public ResponseData<T> exists(String existsMsg) {
        this.status = ResponseEnum.EXISTS.getStatus();
        this.msg = existsMsg;
        return this;
    }

    /**
     * 返回失败
     */
    public ResponseData<T> fail() {
        return fail(ResponseEnum.FAIL.getMsg());
    }

    /**
     * 返回失败
     *
     * @param failMsg 错误信息
     */
    public ResponseData<T> fail(String failMsg) {
        this.status = ResponseEnum.FAIL.getStatus();
        this.msg = failMsg;
        return this;
    }

    /**
     * 返回请求超时
     *
     * @param e 异常信息
     */
    public ResponseData<T> sessionOut(Exception e) {
        return sessionOut(e, ResponseEnum.SESSIONOUT.getMsg());
    }

    /**
     * 返回请求超时
     *
     * @param e 异常信息
     */
    public ResponseData<T> sessionOut(Exception e, String exceptionMsg) {
        this.status = ResponseEnum.SESSIONOUT.getStatus();
        this.msg = ResponseEnum.SESSIONOUT.getMsg();
        return this;
    }

    /**
     * 返回异常
     *
     * @param logMsg Slf4j日志记录异常信息
     * @param e      异常
     */
    public ResponseData<T> exception(String logMsg, JSONObject jo, Exception e) {
        return exception(logMsg + "~" + (ObjectUtil.isEmpty(jo)?"":jo.toJSONString()), e);
    }

    /**
     * 返回异常
     *
     * @param logMsg Slf4j日志记录异常信息
     * @param e      异常
     */
    public ResponseData<T> exception(String logMsg, Exception e) {
        String causeStr = String.valueOf(e.getCause());
        if(causeStr.contains("CustomException")){
            if(causeStr.contains("参数错误")){
                return invalidParam(causeStr.substring(causeStr.indexOf("参数错误"),causeStr.length()));
            }
            return invalidParam(e.getCause().getMessage());
        }else if(e.toString().contains("CustomException")){
            return invalidParam(e.getMessage());
        }else if(causeStr.contains("SessionException")){
            return sessionOut(e);
        }else if(e.toString().contains("SessionException")){
            return invalidParam(e.getMessage());
        }
        this.status = ResponseEnum.EXCEPTION.getStatus();
        this.msg = ResponseEnum.EXCEPTION.getMsg();
        LogUtil.error(logMsg, e, BaseConfig.DEFAULT_LOG_INDEX + 1);
        return this;
    }

    /**
     * 获取操作结果
     *
     * @param affectedRows 影响行数
     */
    public ResponseData<T> operateRes(Integer affectedRows) {
        return operateRes(affectedRows, null);
    }

    /**
     * 获取操作结果
     *
     * @param affectedRows 影响行数
     */
    public ResponseData<T> operateRes(Integer affectedRows, T entity) {
        if (null != affectedRows && affectedRows > 0) {
            return this.success(entity);
        } else {
            return this.fail();
        }
    }

}
