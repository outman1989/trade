package com.trade.core.sqlbuilder;

import com.trade.core.util.YmlUtil;
import constants.SqlConfig;
import org.apache.ibatis.jdbc.SQL;
import util.DateUtil;
import util.LogUtil;
import util.ObjectUtil;
import util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 构建SQL基础类
 *
 * @param <E>
 * @author lx
 * @since 2018年1月18日 上午11:37:09
 */
class BaseSqlBuilder<E> {
    private final static String SHOW_SQL = YmlUtil.getConfig("show_sql");

    /**
     * 打印SQL
     */
    private String SQL(String sql) {
        if ("on".equalsIgnoreCase(SHOW_SQL)) LogUtil.info(sql);
        return sql;
    }

    /**
     * 根据id查询详情
     *
     * @param fields 关键字要加``
     * @param table  表名
     */
    String _getById(final String fields, final String table) {
        return new SQL() {
            {
                SELECT(StringUtil.replaceEmpty(fields, SqlConfig.DEFAULT_QUERY_FIELDS));
                FROM(table);
                WHERE(String.format("%s = #{%s}", SqlConfig.DEFAULT_ID, SqlConfig.DEFAULT_ID));
            }
        }.toString();
    }

    /**
     * 根据条件查询
     *
     * @param fields <span style="color: red;">格式：a,b,c... 关键字要加``</span>
     * @param query  <span style="color: red;">格式：where 1=1... 关键字要加``</span>
     * @param table  表名
     */
    String _getBySQL(String fields, String query, String table) {
        return SQL(String.format("SELECT %s FROM %s %s", fields, table, StringUtil.dealEmpty(query)));
    }

    /**
     * 批量新增
     *
     * @param list             增加列表
     * @param excludeFieldName 排除的字段 格式：id,b,c,...
     * @param table            表名
     */
    String _add(List<E> list, String excludeFieldName, String table) {
        StringBuilder sb = new StringBuilder();
        List<Field> fields = getFields();
        sb.append(insertColumnsSql(fields, excludeFieldName, table));//插入字段
        list.forEach(x -> sb.append(insertValuesSql(fields, x, excludeFieldName)));//插入数据
        sb.setLength(sb.length() - 2);
        return SQL(sb.toString());
    }

    /**
     * 获取E类的所有字段
     */
    @SuppressWarnings("unchecked")
    private List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        Class<E> clazz = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]; // 获取泛型真实类型
        for (Class<?> clz = clazz; clz != Object.class; clz = clz.getSuperclass()) {
            fields.addAll(Arrays.asList(clz.getDeclaredFields()));
        }
        // 会获取子类和父类中两个同名 同类型 字段，需要去重
        List<Field> fieldsDistinct = new ArrayList<>();
        for (Field f : fields) {
            if (fieldsDistinct.parallelStream().noneMatch(
                    fd -> f.getName().equals(fd.getName()) && f.getType().getCanonicalName().equals(fd.getType().getCanonicalName())// 去重arraylist(fieldsDistinct)中的一个元素
            )) {
                fieldsDistinct.add(f);
            }
        }
        return fieldsDistinct;
    }

    /**
     * 插入语句-字段
     *
     * @param fields           字段集合
     * @param excludeFieldName 排除的字段 格式：id,b,c,...
     * @param table            表名
     */
    private String insertColumnsSql(List<Field> fields, String excludeFieldName, String table) {
        StringBuilder sb = new StringBuilder();
        if (fields != null && fields.size() != 0) {
            sb.append(String.format("INSERT INTO %s (", table));
            for (Field fd : fields) {
                if (!fd.isAccessible()) fd.setAccessible(true);
                if (notContains(excludeFieldName, fd.getName())) {
                    sb.append("`").append(fd.getName()).append("`, ");
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append(") VALUES ");
        }
        return sb.toString();
    }

    /**
     * 插入语句-值
     *
     * @param fields           字段集合
     * @param entity           实体
     * @param excludeFieldName 排除的字段 格式：id,b,c,...
     */
    private String insertValuesSql(List<Field> fields, E entity, String excludeFieldName) {
        StringBuilder sb = new StringBuilder();
        if (fields != null && fields.size() != 0) {
            sb.append(" (");
            for (Field fd : fields) {
                if (!fd.isAccessible()) fd.setAccessible(true);
                if (notContains(excludeFieldName, fd.getName())) {
                    Object value;
                    try {
                        value = fd.get(entity);
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                    if (value instanceof String) {
                        sb.append(" '").append(value).append("', ");
                    } else if (value instanceof Date) {
                        sb.append(" '").append(DateUtil.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss")).append("', ");
                    } else if (value instanceof Boolean) {
                        Boolean v = (Boolean) value;
                        sb.append(" ").append((v.compareTo(Boolean.TRUE) == 0 ? 1 : 0)).append(", ");
                    } else {
                        sb.append(" ").append(value).append(", ");
                    }
                }
            }
            sb.setLength(sb.length() - 2);
            sb.append("), ");
        }
        return sb.toString();
    }

    /**
     * 判断源字符串是否包含目标字符串
     *
     * @param strSource 源字符串
     * @param strTarget 目标字符串
     */
    private boolean notContains(String strSource, String strTarget) {
        String[] arrSource = strSource.split(",");
        for (int i = 0; i < arrSource.length; i++) {
            arrSource[i] = arrSource[i].trim();
            if (strTarget.equals(arrSource[i].replaceAll("`", ""))) return false;
        }
        return true;
    }

    /**
     * 新增
     *
     * @param excludeFieldName 排除的字段 如：id,serialVersionUID,...
     * @param table            表名
     */
    String _add(final String excludeFieldName, final String table) {
        return new SQL() {
            {
                INSERT_INTO(table);
                List<Field> fields = getFields();
                for (Field fd : fields) {
                    if (!fd.isAccessible()) fd.setAccessible(true);
                    String fdName = fd.getName();
                    if (notContains(excludeFieldName, fd.getName())) {
                        VALUES(String.format("`%s`", StringUtil.camelToUnderline(fdName)), String.format("#{%s.%s}", SqlConfig.PARAM_ENTITY, fdName));
                    }
                }
            }
        }.toString();
    }

    /**
     * 根据id删除
     */
    String _delById(final String table) {
        return new SQL() {
            {
                DELETE_FROM(table);
                WHERE(String.format("%s = #{%s}", SqlConfig.DEFAULT_ID, SqlConfig.DEFAULT_ID));
            }
        }.toString();
    }

    /**
     * 根据条件删除
     */
    String _delBySQL(String query, String table) {
        return SQL(String.format("DELETE FROM %s %s", table, query));
    }

    /**
     * 根据条件修改
     */
    String _updateBySQL(String updateSQL, String table) {
        return SQL(String.format("UPDATE %s %s", table, updateSQL));
    }

    /**
     * 更新
     *
     * @param entity           要更新的实体类
     * @param id               自增主键名 关键字要加``
     * @param excludeFieldName 排除的字段 格式：id,b,c,...
     * @param table            <span style="color: red;">注意：mapper中要配置@Param(value="entity")</span>
     */
    String _update(final E entity, String id, final String excludeFieldName, final String table) {
        return new SQL() {
            {
                try {
                    List<Field> fields = getFields();
                    if (fields != null && fields.size() != 0) {
                        UPDATE(table);
                        for (Field fd : fields) {
                            if (!fd.isAccessible()) fd.setAccessible(true);
                            if (!fd.getName().equals(id) && notContains(excludeFieldName, fd.getName()) && !ObjectUtil.isEmpty(fd.get(entity))) {
                                SET(String.format("`%s` = #{%s.%s}", StringUtil.camelToUnderline(fd.getName()), SqlConfig.PARAM_ENTITY, fd.getName()));
                            }
                        }
                        WHERE(String.format("`%s` = #{%s.%s}", id, SqlConfig.PARAM_ENTITY, SqlConfig.DEFAULT_ID));
                    }
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    LogUtil.error("更新", e);
                }
            }
        }.toString();
    }

    /**
     * 批量更新
     *
     * @param list   注意：list.size不能为0
     * @param fields 指定要更新的字段，格式：id, field1, field2... 类型：String 关键字要加``
     * @param table  表名
     */
    String _updateBatch(List<E> list, String fields, String table) {
        String[] fieldArr = fields.split(",");
        StringBuilder sb = new StringBuilder("INSERT INTO " + table + " (" + fields + ") VALUES ");
        for (Object entity : list) {
            sb.append("(");
            StringBuilder sbdFdSQL = new StringBuilder();
            for (String fStr : fieldArr) {
                Field fd;
                try {
                    fd = entity.getClass().getDeclaredField(fStr);
                    if (!fd.isAccessible()) fd.setAccessible(true);
                    Object value = fd.get(entity);
                    if (value instanceof String) {
                        sbdFdSQL.append("'").append(value).append("', ");
                    } else if (value instanceof Date) {
                        sbdFdSQL.append("'").append(DateUtil.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss")).append("', ");
                    } else if (value instanceof Boolean) {
                        Boolean v = (Boolean) value;
                        sbdFdSQL.append("").append((v.compareTo(Boolean.TRUE) == 0 ? 1 : 0)).append(", ");
                    } else {
                        sbdFdSQL.append(value).append(", ");
                    }
                } catch (Exception e) {
                    LogUtil.error("批量更新", e);
                    return "";
                }
            }
            String fdSQL = sbdFdSQL.toString();
            if (!"".equals(fdSQL)) fdSQL = fdSQL.substring(0, fdSQL.length() - 2);
            sb.append(fdSQL).append("), ");
        }
        String sql = sb.toString();
        if (!"".equals(sql)) sql = sql.substring(0, sql.length() - 2) + " ";
        sql += " ON DUPLICATE KEY UPDATE ";

        StringBuilder sbdFdSQL = new StringBuilder();
        for (String fStr : fieldArr) {
            sbdFdSQL.append(fStr).append(" = VALUES(").append(fStr).append("),");
        }
        String fdSQL = sbdFdSQL.toString();
        if (!"".equals(fdSQL)) fdSQL = fdSQL.substring(0, fdSQL.length() - 1);
        sql += fdSQL;
        return SQL(sql);
    }

    /**
     * 分页查询
     *
     * @param pageNumber 分页序号
     * @param pageSize   分页大小
     * @param fields     <span style="color: red;">格式：a,b,c... 关键字要加``</span>
     * @param query      <span style="color: red;">格式：where a=1... 关键字要加``</span>
     * @param order      排序条件
     * @param table      表名
     */
    String _getPage(Integer pageNumber, Integer pageSize, String fields, String query, String order, String table) {
        order = StringUtil.replaceEmpty(order, SqlConfig.DEFAULT_ORDER);
        fields = StringUtil.replaceEmpty(fields, SqlConfig.DEFAULT_QUERY_FIELDS);
        int startIndex = (SqlConfig.getPageNumber(pageNumber) - 1) * SqlConfig.getPageSize(pageSize);
        return SQL(String.format("SELECT %s FROM %s %s %s LIMIT %s,%s", fields, table, StringUtil.dealEmpty(query), order, startIndex, pageSize));
    }

    /**
     * 根据条件查询条数
     *
     * @param query 查询条件
     * @param table 表名
     */
    String _getCount(String query, String table) {
        return SQL(String.format("SELECT COUNT(1) FROM %s %s", table, StringUtil.dealEmpty(query)));
    }

}
