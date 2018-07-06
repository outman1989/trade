package constants;

import com.alibaba.fastjson.JSONObject;
import util.ObjectUtil;
import util.StringUtil;

/**
 * SQL配置常量
 *
 * @author lx
 * @since 2018-6-8 09:50:22
 */
public class SqlConfig {
    /**
     * 方法
     */
    public static final String GET_BY_ID = "getById";//根据id查询
    public static final String GET_BY_SQL = "getBySQL";//根据条件查询
    public static final String QUERY_CUSTOM = "queryCustom";//自定义查询
    public static final String ADD = "add";//新增
    public static final String SAVE = "save";//保存
    public static final String SAVE_CN = "-保存";
    public static final String ADD_BATCH = "addBatch";//批量新增
    public static final String UPDATE = "update";//编辑
    public static final String UPDATE_BY_SQL = "updateBySQL";//根据条件修改
    public static final String DEL_BY_ID = "delById";//根据id删除（详情查询）
    public static final String DETAIL_CN = "-详情查询";
    public static final String DEL_BY_SQL = "delBySQL";//根据条件删除
    public static final String DELETE = "delete";//根据ids删除
    public static final String DELETE_CN = "-删除";
    public static final String GET_PAGE = "getPage";//分页查询
    public static final String GET_PAGE_CN = "-分页查询";
    public static final String GET_COUNT = "getCount";//查询条数

    /**
     * 默认值
     */
    private static final Integer PAGE_NUMBER = 1;//当前页
    private static final Integer PAGE_SIZE = 10;//每页条数
    public static final String DEFAULT_QUERY_FIELDS = " * ";//默认查询字段
    public static final String DEFAULT_QUERY_CONDITION = " WHERE 1=1 ";//默认查询条件
    public static final String DEFAULT_ORDER = " ORDER BY id DESC ";//默认排序条件
    public static final String DEFAULT_ID = "id";//默认查询字段
    public static final String DEFAULT_SELECT_KEY = "entity.id";//默认查询key
    public static final String DEFAULT_STATEMENT = "SELECT LAST_INSERT_ID()";
    public static final String EXCLUDE_ID_AND_SERIALVERSIONUID = "id,serialVersionUID";//排除id,serialVersionUID
    public static final String EXCLUDE_SERIALVERSIONUID = "serialVersionUID";//排除serialVersionUID
    public static final String DEFAULT_QUERY_LIMIT = " LIMIT 100 ";//默认查询最大条数

    /**
     * 默认参数名
     */
    public static final String PARAM_PAGE_NUMBER = "page_number";//当前页参数全局常量
    public static final String PARAM_PAGE_SIZE = "page_size";//每页条数参数全局常量
    public static final String PARAM_QUERY = "query";//查询条件参数全局常量
    public static final String PARAM_ENTITY = "entity";//实体参数全局常量
    public static final String PARAM_LIST = "list";//集合参数全局常量
    public static final String PARAM_FIELDS = "fields";//字段参数全局常量
    public static final String PARAM_ORDER = "order";//字段参数全局常量
    public static final String PARAM_SQL = "sql";//sql语句参数全局常量
    public static final String PARAM_UPDATESQL = "updateSQL";//修改语句参数全局常量

    /**
     * 获取表名
     *
     * @param clazz 实体类类型
     * @return tableName（String）
     */
    public static String Table(Class clazz) {
        String table = clazz.getSimpleName();
        table = table.replace("Entity", "");
        table = table.substring(0, 1).toLowerCase() + table.substring(1);
        return StringUtil.camelToUnderline(table);
    }

    /**
     * 当前页
     */
    public static Integer getPageNumber(JSONObject jo) {
        return (ObjectUtil.isEmpty(jo) || ObjectUtil.isEmpty(jo.getInteger(PARAM_PAGE_NUMBER))) ? PAGE_NUMBER : jo.getInteger(PARAM_PAGE_NUMBER);// 开始页
    }

    /**
     * 每页条数
     */
    public static Integer getPageSize(JSONObject jo) {
        return (ObjectUtil.isEmpty(jo) || ObjectUtil.isEmpty(jo.getInteger(PARAM_PAGE_SIZE))) ? PAGE_SIZE : jo.getInteger(PARAM_PAGE_SIZE);// 开始页
    }

    /**
     * 当前页
     */
    public static Integer getPageNumber(Integer pageNumber) {
        if (ObjectUtil.isEmpty(pageNumber) || pageNumber.compareTo(0) == 0) pageNumber = PAGE_NUMBER;
        return pageNumber;
    }

    /**
     * 每页条数
     */
    public static Integer getPageSize(Integer pageSize) {
        if (ObjectUtil.isEmpty(pageSize) || pageSize.compareTo(0) == 0) pageSize = PAGE_SIZE;
        return pageSize;
    }
}
