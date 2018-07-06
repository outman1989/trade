package com.trade.core.mapper;

import com.trade.core.entity.ProductLogEntity;
import com.trade.core.sqlbuilder.ProductLogSqlBuilder;
import constants.SqlConfig;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 猪源日志 mapper
 *
 * @author lx
 * @since 2018-06-28 17:48:08
 */
@SuppressWarnings("unused")
public interface IProductLogMapper {

    /**
     * 根据id查询信息
     */
    @SelectProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.GET_BY_ID)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    ProductLogEntity getById(@Param(value = SqlConfig.DEFAULT_ID) String id);

    /**
     * 根据条件查询
     */
    @SelectProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.GET_BY_SQL)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<ProductLogEntity> getBySQL(@Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 自定义查询
     */
    @SelectProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.QUERY_CUSTOM)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<Object> queryCustom(@Param(value = SqlConfig.PARAM_SQL) String sql);

    /**
     * 新增
     */
    @InsertProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.ADD)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int add(@Param(value = SqlConfig.PARAM_ENTITY) ProductLogEntity entity);

    /**
     * 批量新增
     */
    @InsertProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.ADD_BATCH)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int addBatch(@Param(value = SqlConfig.PARAM_LIST) List<ProductLogEntity> list);

    /**
     * 编辑
     */
    @UpdateProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.UPDATE)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int update(@Param(value = SqlConfig.PARAM_ENTITY) ProductLogEntity entity);

    /**
     * 根据条件修改
     */
    @UpdateProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.UPDATE_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int updateBySQL(@Param(value = SqlConfig.PARAM_UPDATESQL) String updateSQL);

    /**
     * 根据id删除
     */
    @DeleteProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.DEL_BY_ID)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delById(@Param(value = SqlConfig.DEFAULT_ID) int id);

    /**
     * 根据条件删除
     */
    @DeleteProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.DEL_BY_SQL)
    @Options(useCache = false, flushCache = Options.FlushCachePolicy.TRUE, timeout = 10000)
    int delBySQL(@Param(value = SqlConfig.PARAM_QUERY) String query);

    /**
     * 分页查询
     */
    @SelectProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.GET_PAGE)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    List<ProductLogEntity> getPage(@Param(value = SqlConfig.PARAM_PAGE_NUMBER) Integer pageNumber, @Param(value = SqlConfig.PARAM_PAGE_SIZE) Integer pageSize, @Param(value = SqlConfig.PARAM_FIELDS) String fields, @Param(value = SqlConfig.PARAM_QUERY) String query, @Param(value = SqlConfig.PARAM_ORDER) String order);

    /**
     * 根据条件获取条数
     */
    @SelectProvider(type = ProductLogSqlBuilder.class, method = SqlConfig.GET_COUNT)
    @Options(flushCache = Options.FlushCachePolicy.FALSE, timeout = 10000)
    int getCount(@Param(value = SqlConfig.PARAM_QUERY) String query);

}
