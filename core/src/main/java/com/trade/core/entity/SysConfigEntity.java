package com.trade.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 系统配置表(sys_config)
 * 
 * @author lx
 * @since 2018-06-06
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sys_config")
public class SysConfigEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1290127997691899740L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @JSONField(ordinal = 10)
    private Integer id;

    /** 配置名称（中文） */
    @Column(name = "name",  length = 30)
    @JSONField(name = "name",  ordinal = 20)
    private String name;
    
    /** 键 */
    @Column(name = "config_key",  length = 30)
    @JSONField(name = "config_key",  ordinal = 30)
    private String configKey;
    
    /** 值 */
    @Column(name = "config_value",  length = 30)
    @JSONField(name = "config_value",  ordinal = 40)
    private String configValue;
    
    /** 解释 */
    @Column(name = "config_explain",  length = 300)
    @JSONField(name = "config_explain",  ordinal = 50)
    private String configExplain;
    
}