package util;

import constants.BaseConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日志工具类
 *
 * @author lx
 * @since 2017-3-2 16:13:04
 */
@Slf4j
@SuppressWarnings("unused")
public class LogUtil {

    /**
     * info信息记录文件日志
     */
    public static void info(String logMsg) {
        info(logMsg, BaseConfig.DEFAULT_LOG_INDEX);
    }

    /**
     * info信息记录文件日志
     *
     * @param index 调用层级
     */
    private static void info(String logMsg, Integer index) {
        try {
            index = (null == index || 0 == index) ? BaseConfig.DEFAULT_LOG_INDEX : index;
            log.info(String.format("【%s】~%s", Thread.currentThread().getStackTrace()[index].getClassName(), logMsg));
        } catch (Exception ex) {
            log.error(String.format("【info信息记录文件日志】~%s~%s", ex.toString(), ex.getMessage()));
        }
    }

    /**
     * error信息记录文件日志
     */
    public static void error(String logMsg, Exception e) {
        error(logMsg, e, BaseConfig.DEFAULT_LOG_INDEX);
    }

    /**
     * error信息记录文件日志
     *
     * @param index 调用层级
     */
    public static void error(String logMsg, Exception e, Integer index) {
        try {
            index = (null == index || 0 == index) ? BaseConfig.DEFAULT_LOG_INDEX : index;
            String clazz = Thread.currentThread().getStackTrace()[index].getClassName();//获取调用者类名
            String method = Thread.currentThread().getStackTrace()[index].getMethodName();//获取调用者方法名
            int line = Thread.currentThread().getStackTrace()[index].getLineNumber();//调用者行数
            if (null == e || StringUtil.isEmpty(e.toString())) {
                log.error(String.format("【类：%s】~【方法：%s】~【第：%s行】~%s", clazz, method, line, logMsg));
            } else {
                log.error(String.format("【类：%s】~【方法：%s】~【第：%s行】~%s~错误信息：{%s~%s}~异常栈：{%s}", clazz, method, line, logMsg, e.toString(), e.getMessage(), getExcStr(e)));
            }
        } catch (Exception ex) {
            log.error(String.format("【error信息记录文件日志】~%s~%s", ex.toString(), ex.getMessage()));
        }
    }

    /**
     * 获取异常信息
     */
    private static String getExcStr(Exception e) {
        String excStr = "";
        try {
            List<String> ls = new ArrayList<>();//保存系统错误类集合
            //错误数据处理
            Arrays.asList(e.getStackTrace()).subList(0, 10).stream()
                    .filter((stack) -> ((stack.toString().contains("com." + BaseConfig.PROJECT_NAME)))) //匹配
                    .forEach((stack) -> ls.add(stack.toString())); //保存集合ls中
            excStr = ls.toString();
        } catch (Exception ex) {
            log.error(String.format("【获取异常信息】~%s~%s", ex.toString(), ex.getMessage()));
        }
        return excStr;
    }

}