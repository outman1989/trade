package com.trade.core.sqlbuilder;

import com.trade.core.entity.SysConfigEntity;
import constants.SqlConfig;

import java.util.List;
import java.util.Map;

/**
 * 系统配置 sqlbuilder
 *
 * @author lx
 * @since 2018-6-6 16:06:27
 */
@SuppressWarnings("unused")
public class SysConfigSqlBuilder extends BaseSqlBuilder<SysConfigEntity> {

    private static final String TABLE = SqlConfig.Table(SysConfigEntity.class);

    /**
     * 根据id查询信息
     *
     * @return String SQL语句
     */
    public String getById() {
        return super._getById(SqlConfig.DEFAULT_QUERY_FIELDS, TABLE);
    }

    /**
     * 根据条件查询
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String getBySQL(Map<String, Object> parameters) {
        return super._getBySQL((String) parameters.get(SqlConfig.PARAM_FIELDS), (String) parameters.get(SqlConfig.PARAM_QUERY), TABLE);
    }

    /**
     * 新增
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String add(Map<String, Object> parameters) {
        return super._add(SqlConfig.EXCLUDE_ID_AND_SERIALVERSIONUID, TABLE);
    }

    /**
     * 批量新增
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    @SuppressWarnings("unchecked")
    public String addBatch(Map<String, Object> parameters) {
        return super._add((List<SysConfigEntity>) parameters.get(SqlConfig.PARAM_LIST), SqlConfig.EXCLUDE_ID_AND_SERIALVERSIONUID, TABLE);
    }

    /**
     * 编辑
     *
     * @return String SQL语句
     */
    public String update(Map<String, Object> parameters) {
        return super._update((SysConfigEntity) parameters.get(SqlConfig.PARAM_ENTITY), SqlConfig.DEFAULT_ID, SqlConfig.EXCLUDE_ID_AND_SERIALVERSIONUID, TABLE);
    }

    /**
     * 批量修改
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    @SuppressWarnings("unchecked")
    public String updateBatch(Map<String, Object> parameters) {
        return super._updateBatch((List<SysConfigEntity>) parameters.get(SqlConfig.PARAM_LIST), (String) parameters.get(SqlConfig.PARAM_FIELDS), TABLE);
    }

    /**
     * 根据条件修改
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String updateBySQL(Map<String, Object> parameters) {
        return super._updateBySQL((String) parameters.get(SqlConfig.PARAM_UPDATESQL), TABLE);
    }

    /**
     * 根据id删除
     *
     * @return String SQL语句
     */
    public String delById() {
        return super._delById(TABLE);
    }

    /**
     * 根据条件删除
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String delBySQL(Map<String, Object> parameters) {
        return super._delBySQL((String) parameters.get(SqlConfig.PARAM_QUERY), TABLE);
    }

    /**
     * 分页查询
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String getPage(Map<String, Object> parameters) {
        return super._getPage((Integer) parameters.get(SqlConfig.PARAM_PAGE_NUMBER), (Integer) parameters.get(SqlConfig.PARAM_PAGE_SIZE), (String) parameters.get(SqlConfig.PARAM_FIELDS), (String) parameters.get(SqlConfig.PARAM_QUERY), (String) parameters.get(SqlConfig.PARAM_ORDER), TABLE);
    }

    /**
     * 根据条件获取条数
     *
     * @param parameters 参数
     * @return String SQL语句
     */
    public String getCount(Map<String, Object> parameters) {
        return super._getCount((String) parameters.get(SqlConfig.PARAM_QUERY), TABLE);
    }

}