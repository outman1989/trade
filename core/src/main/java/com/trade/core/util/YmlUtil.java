package com.trade.core.util;

import com.trade.core.entity.YmlConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import util.LogUtil;

import java.util.Map;

/**
 * @author lx
 * @since 2018-05-30 16:55:11
 */
public class YmlUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static Map<String,String> propertiesMap =null;

    public YmlUtil() {
    }

    /**
     * 获取yml配置文件
     */
    public static String getConfig(String key) {
        try{
            if (propertiesMap ==null){
                YmlConfig ymlConfig = applicationContext.getBean(YmlConfig.class);
                propertiesMap = ymlConfig.getCfg();
            }
            return propertiesMap.get(key);
        }catch (Exception e){
            LogUtil.error("获取yml配置文件",e);
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(YmlUtil.applicationContext == null){
            YmlUtil.applicationContext  = applicationContext;
        }
    }
}
