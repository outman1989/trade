package com.trade.core.mapper;

import com.trade.core.entity.SysLogEntity;
import com.trade.core.sqlbuilder.SysLogSqlBuilder;
import constants.SqlConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 系统日志 mapper
 *
 * @author lx
 * @since 2018-6-6 06:06:06
 */
@SuppressWarnings("unused")
public interface ISysLogMapper {

    /**
     * 根据id查询信息
     */
    @SelectProvider(type = SysLogSqlBuilder.class, method = SqlConfig.GET_BY_ID)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    SysLogEntity getById(@Param(value = SqlConfig.DEFAULT_ID) String id);

    /**
     * 根据条件查询
     */
    @SelectProvider(type = SysLogSqlBuilder.class, method = SqlConfig.GET_BY_SQL)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<SysLogEntity> getBySQL(@Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 自定义查询
     */
    @SelectProvider(type = SysLogSqlBuilder.class, method = SqlConfig.QUERY_CUSTOM)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<Object> queryCustom(@Param(value = SqlConfig.PARAM_SQL) String sql);

    /**
     * 新增
     */
    @InsertProvider(type = SysLogSqlBuilder.class, method = SqlConfig.ADD)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int add(@Param(value = SqlConfig.PARAM_ENTITY) SysLogEntity entity);

    /**
     * 批量新增
     */
    @InsertProvider(type = SysLogSqlBuilder.class, method = SqlConfig.ADD_BATCH)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int addBatch(@Param(value = SqlConfig.PARAM_LIST) List<SysLogEntity> list);

    /**
     * 编辑
     */
    @UpdateProvider(type = SysLogSqlBuilder.class, method = SqlConfig.UPDATE)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int update(@Param(value = SqlConfig.PARAM_ENTITY) SysLogEntity entity);

    /**
     * 根据条件修改
     */
    @UpdateProvider(type = SysLogSqlBuilder.class, method = SqlConfig.UPDATE_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int updateBySQL(@Param(value = SqlConfig.PARAM_UPDATESQL) String updateSQL);

    /**
     * 根据id删除
     */
    @DeleteProvider(type = SysLogSqlBuilder.class, method = SqlConfig.DEL_BY_ID)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delById(@Param(value = SqlConfig.DEFAULT_ID) int id);

    /**
     * 根据条件删除
     */
    @DeleteProvider(type = SysLogSqlBuilder.class, method = SqlConfig.DEL_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delBySQL(@Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 分页查询
     */
    @SelectProvider(type = SysLogSqlBuilder.class, method = SqlConfig.GET_PAGE)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<SysLogEntity> getPage(@Param(value = SqlConfig.PARAM_PAGE_NUMBER) Integer pageNumber, @Param(value = SqlConfig.PARAM_PAGE_SIZE) Integer pageSize, @Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query, @Param(value = SqlConfig.PARAM_ORDER) String order);

    /**
     * 根据条件获取条数
     */
    @SelectProvider(type = SysLogSqlBuilder.class, method = SqlConfig.GET_COUNT)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    int getCount(@Param(value = SqlConfig.PARAM_QUERY) String query);

}
