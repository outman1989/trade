package com.trade.core.configurator;

import com.trade.core.util.SnowflakeIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 全局id生成器工具配置器
 *
 * @author lx
 * @since 2018-06-05 18:37:24
 */
@Configuration
@ConditionalOnClass(SnowflakeIdUtil.class)
public class SnowflakeIdWorkerUtilConfigure {
    @Autowired
    private Environment env;

    @Bean
    @ConditionalOnMissingBean
    SnowflakeIdUtil snowflakeIdUtil() {
        return new SnowflakeIdUtil(Long.parseLong(env.getProperty("cfg.worker_id")), Long.parseLong(env.getProperty("cfg.datacenter_id")));
    }
}
