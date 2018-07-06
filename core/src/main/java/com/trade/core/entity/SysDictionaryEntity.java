package com.trade.core.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 系统字典表(sys_dictionary)
 *
 * @author lx
 * @since 2018-06-06
 */
@Entity
@NoArgsConstructor
@Table(name = "sys_dictionary")
@SuppressWarnings("unused")
public class SysDictionaryEntity implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -1375428725180728310L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, length = 11)
    @JSONField(ordinal = 10)
    private Integer id;

    /**
     * 父键
     */
    @Column(name = "p_key", length = 50)
    @JSONField(name = "p_key", ordinal = 20)
    @Excel(name = "父键", orderNum = "1")
    private String pKey;

    /**
     * 父值
     */
    @Column(name = "p_value", length = 50)
    @JSONField(name = "p_value", ordinal = 30)
    @Excel(name = "父值", orderNum = "2")
    private String pValue;

    /**
     * 子键
     */
    @Column(name = "c_key", length = 50)
    @JSONField(name = "c_key", ordinal = 40)
    @Excel(name = "子键", orderNum = "3")
    private String cKey;

    /**
     * 子值
     */
    @Column(name = "c_value", length = 50)
    @JSONField(name = "c_value", ordinal = 50)
    @Excel(name = "子值", orderNum = "4")
    private String cValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }

    public String getcKey() {
        return cKey;
    }

    public void setcKey(String cKey) {
        this.cKey = cKey;
    }

    public String getcValue() {
        return cValue;
    }

    public void setcValue(String cValue) {
        this.cValue = cValue;
    }
}