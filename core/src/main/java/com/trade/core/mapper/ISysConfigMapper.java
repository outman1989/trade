package com.trade.core.mapper;

import com.trade.core.entity.SysConfigEntity;
import com.trade.core.sqlbuilder.SysConfigSqlBuilder;
import constants.SqlConfig;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
 * 系统配置 mapper
 *
 * @author lx
 * @since 2018-6-6 16:07:25
 */
@SuppressWarnings("unused")
public interface ISysConfigMapper {

    /**
     * 根据id查询信息
     */
    @SelectProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.GET_BY_ID)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    SysConfigEntity getById(@Param(value = SqlConfig.DEFAULT_ID) int id);

    /**
     * 根据条件查询
     */
    @SelectProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.GET_BY_SQL)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<SysConfigEntity> getBySQL(@Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 新增
     */
    @InsertProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.ADD)
    @SelectKey(before = false, keyProperty = SqlConfig.DEFAULT_SELECT_KEY, resultType = Integer.class, statementType = StatementType.STATEMENT, statement = SqlConfig.DEFAULT_STATEMENT)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int add(@Param(value = SqlConfig.PARAM_ENTITY) SysConfigEntity entity);

    /**
     * 批量新增
     */
    @InsertProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.ADD_BATCH)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int addBatch(@Param(value = SqlConfig.PARAM_LIST) List<SysConfigEntity> list);

    /**
     * 编辑
     */
    @UpdateProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.UPDATE)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int update(@Param(value = SqlConfig.PARAM_ENTITY) SysConfigEntity entity);

    /**
     * 根据条件修改
     */
    @UpdateProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.UPDATE_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int updateBySQL(@Param(value = SqlConfig.PARAM_UPDATESQL) String updateSQL);

    /**
     * 根据id删除
     */
    @DeleteProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.DEL_BY_ID)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delById(@Param(value = SqlConfig.DEFAULT_ID) int id);

    /**
     * 根据条件删除
     */
    @DeleteProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.DEL_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delBySQL(@Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 分页查询
     */
    @SelectProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.GET_PAGE)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<SysConfigEntity> getPage(@Param(value = SqlConfig.PARAM_PAGE_NUMBER) Integer pageNumber, @Param(value = SqlConfig.PARAM_PAGE_SIZE) Integer pageSize, @Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query, @Param(value = SqlConfig.PARAM_ORDER) String order);

    /**
     * 根据条件获取条数
     */
    @SelectProvider(type = SysConfigSqlBuilder.class, method = SqlConfig.GET_COUNT)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    int getCount(@Param(value = SqlConfig.PARAM_QUERY) String query);

}
