package com.trade.core.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @since 2018-5-30 13:36:30
 */
@Component
@ConfigurationProperties
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class YmlConfig {
    private Map<String,String> cfg = new HashMap<>();
}
