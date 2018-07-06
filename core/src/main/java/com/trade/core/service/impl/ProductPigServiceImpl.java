package com.trade.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.ProductStateEnum;
import com.trade.core.Enum.SysStateEnum;
import com.trade.core.entity.*;
import com.trade.core.entity.VO.ProductPigVO;
import com.trade.core.entity.VO.SearchPigVO;
import com.trade.core.entity.VO.ShopVO;
import com.trade.core.mapper.*;
import com.trade.core.service.IProductPigService;
import com.trade.core.util.O2OUtil;
import com.trade.core.util.SnowflakeIdUtil;
import constants.BaseConfig;
import constants.ResponseEnum;
import constants.SqlConfig;
import entity.Page;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.ListUtil;
import util.ObjectUtil;
import util.StringUtil;
import util.ValidateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 猪源 serviceImpl
 *
 * @author lx
 * @since 2018-6-13 11:09:41
 */
@Service
@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "unused"})
public class ProductPigServiceImpl<E> extends BaseServiceImpl implements IProductPigService<E> {
    @Autowired
    private SnowflakeIdUtil snowflakeIdUtil;
    @Autowired
    private IProductPigMapper mapper;
    @Autowired
    private IShopMapper shopMapper;
    @Autowired
    private IPiggeryMapper piggeryMapper;
    @Autowired
    private IPiggeryIndexMapper piggeryIndexMapper;
    @Autowired
    private IProductLogMapper productLogMapper;

    /**
     * 猪源-发布猪源
     */
    @Transactional
    @Override
    public ResponseData<Object> publish(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【参数获取、验证】
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        String accountId = ValidateUtil.requiredSingle(jo, "account_id");//账号id
        JSONObject jsonEntity = jo.getJSONObject(SqlConfig.PARAM_ENTITY);
        //非空验证:猪场id、猪源品类、猪源品种、供应类型、保障金、冻结保障金金额、最小估重、最大估重、价格、配送方式、控料
        ValidateUtil.required(jsonEntity, "piggery_id", "product_category", "product_variety", "supply_type", "ensured", "frozen_ensured", "min_weight", "max_weight", "unit_price", "delivery_type", "control_material");
        ProductPigEntity entity = jsonEntity.toJavaObject(ProductPigEntity.class);//获取猪源信息
        //2、【查询店铺信息】
        List<ShopEntity> resShopList = shopMapper.getBySQL("id", whereEqual("account_id", accountId));
        if (ListUtil.isEmpty(resShopList)) return res.fail("查询店铺失败");
        //3、【新增猪源】
        String product_id = snowflakeIdUtil.nextId();
        mapper.add(entity.init(product_id, accountId, resShopList.get(0).getId()));
        //4、【记录猪源日志】
        productLogMapper.add(new ProductLogEntity(snowflakeIdUtil.nextId(), product_id, "发布猪源", account));//新增猪源
        return res.success();
    }

    /**
     * 猪源-编辑
     */
    @Transactional
    @Override
    public ResponseData<Object> update(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【参数获取、验证】
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        ProductPigEntity entity = jo.getJSONObject(SqlConfig.PARAM_ENTITY).toJavaObject(ProductPigEntity.class);//获取猪源信息
        //2、【编辑猪源】
        int updateRes = mapper.update(entity);
        if (updateRes <= 0) throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        //3、【记录猪源日志】
        productLogMapper.add(new ProductLogEntity(snowflakeIdUtil.nextId(), entity.getId(), "编辑猪源", account));//编辑猪源
        return res.success();
    }

    /**
     * 猪源-上下架
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> changeState(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、【获取参数、验证】
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        ValidateUtil.required(jo, "shop_id", "product_state");//非空验证
        String ids = ValidateUtil.requiredSingle(jo, "ids");//猪场id
        //2、【猪源-上下架】
        int updateRes = mapper.updateBySQL(set("product_state", jo) + whereIn("id", jo.getString("ids") + andEqual("shop_id", jo)));
        List<String> idList = StringUtil.getListFromString(ids, BaseConfig.SPLIT_SIGN_COMMA);
        if ((updateRes == 0) || (idList.size() != updateRes)) {
            throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        }
        //3、【记录猪源日志】
        idList.forEach(x -> productLogMapper.add(new ProductLogEntity(snowflakeIdUtil.nextId(), x, "猪源上下架", account)));
        return res.success();
    }

    /**
     * 猪源-猪源-搜猪源（买家）
     *
     * @param jo 请求json
     * @return 响应结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<SearchPigVO>> searchPig(JSONObject jo) {
        ResponseData<Page<SearchPigVO>> res = new ResponseData<>();
        String query = getQuerySearchPig(jo);//查询条件
        String order = getOrderSearchPig(jo);//排序条件
        int total = mapper.searchPigCount("SELECT count(1) FROM product_pig pp LEFT JOIN piggery p ON pp.piggery_id = p.id " + query);
        List<SearchPigVO> list = new ArrayList<>();
        if (total > 0) {
            Integer pageSize = SqlConfig.getPageSize(jo);//每页条数
            int startIndex = (SqlConfig.getPageNumber(jo) - 1) * pageSize;
            list = mapper.searchPigPage("SELECT pp.*,p.name,p.province,p.city,p.area,p.address,p.contacts,p.contact_phone FROM product_pig pp LEFT JOIN piggery p ON pp.piggery_id = p.id " + query + order + " LIMIT " + startIndex + "," + pageSize);//分页查询
        }
        return res.success(new Page(total, list));
    }

    /**
     * 获取查询条件--猪源-搜猪源（买家）
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuerySearchPig(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        //1、 猪源条件
        query += (andEqual("pp.product_category", jo.getString("product_category")));//猪源品类
        query += (andEqual("pp.product_variety", jo.getString("product_variety")));//猪源品种
        query += (andEqual("pp.ensured", jo.getString("ensured")));//保障金猪源(0=false（默认），1=true)
        query += (andEqual("pp.preference", jo.getString("preference")));//优选(0=false（默认），1=true)
        query += (andEqual("pp.supply_type", jo.getString("supply_type")));//供应类型（0=库存（默认），1=长期供应）
        //有货（剩余库存>0 || 长期供应）
        if (StringUtil.isNotEmpty(jo.getString("have_stock"))) {
            query += " AND (pp.supply_type = '1' or pp.surplus_stock > '0')";
        }
        query += (andEqual("pp.id_pig", jo.getString("id_pig")));//ID猪
        query += (andMoreThan("pp.min_weight", jo.getString("min_weight")));//最小估重（公斤/头）
        query += (andLessThan("pp.max_weight", jo.getString("max_weight")));//最大估重（公斤/头）
        query += (andMoreThan("pp.unit_price", jo.getString("min_unit_price")));//最低价（单位：生猪 X元/公斤  仔猪or种猪 X元/头）
        query += (andLessThan("pp.unit_price", jo.getString("max_unit_price")));//最高价（单位：生猪 X元/公斤  仔猪or种猪 X元/头）
        query += (andEqual("pp.delivery_type", jo.getString("delivery_type")));//配送方式（0=买家拉猪（默认），1=卖家送猪）
        query += (andEqual("pp.sale_date", jo.getString("sale_date")));//出售时间

        //2、猪场条件
        //搜索框关键字（地区/店名/人名/手机）
        if (StringUtil.isNotEmpty(jo.getString("keyword"))) {
            query += " AND CONCAT(p.name,p.contacts,p.contact_phone,p.province,p.city,p.area) LIKE '%" + jo.getString("keyword") + "%' ";
        }
        query += (andEqual("p.province", jo.getString("province")));//地区-省
        query += (andEqual("p.city", jo.getString("city")));//地区-市
        query += (andEqual("p.area", jo.getString("area")));//地区-县区
        return query;
    }

    /**
     * 获取排序条件--猪源-搜猪源（买家）
     */
    private String getOrderSearchPig(JSONObject jo) {
        String order = "";//默认排序条件
        if (StringUtil.isNotEmpty(jo.getString("sort"))) {
            switch (jo.getString("sort")) {
                case "sort_price_asc":
                    order = " ORDER BY pp.unit_price ASC ";//价格从低到高
                    break;
                case "sort_price_desc":
                    order = " ORDER BY pp.unit_price DESC ";//价格从高到低
                    break;
                case "surplus_stock_asc":
                    order = " ORDER BY pp.surplus_stock ASC ";//数量从少到多
                    break;
                case "surplus_stock_desc":
                    order = " ORDER BY pp.surplus_stock DESC ";//数量从多到少
                    break;
                default:
                    order = " ORDER BY pp.sold_stock DESC, pp.create_time ASC ";//默认已售库存降序、发布时间升序
                    break;
            }
        }
        return order;
    }

    /**
     * 猪源-详情（卖家）
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> saleDetail(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        String id = ValidateUtil.requiredSingle(jo, "id");
        ProductPigEntity productPig = mapper.getById(id);
        if (ObjectUtil.isEmpty(productPig)) return res.fail();
        return res.success(O2OUtil.copyObject(productPig, ProductPigVO.class));
    }

    /**
     * 猪源-详情（买家预订）
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> buyDetail(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        Map<String, Object> map = new HashMap<>();
        //1、【猪源信息】
        String id = ValidateUtil.requiredSingle(jo, "id");
        ProductPigEntity productPig = mapper.getById(id);
        if (ObjectUtil.isEmpty(productPig)) return res.fail();
        map.put("productPig", O2OUtil.copyObject(productPig, ProductPigVO.class));
        //2.1、【店铺基础信息】
        ShopEntity shop = shopMapper.getById(productPig.getShopId());
        if (ObjectUtil.isEmpty(shop)) return res.fail();
        map.put("shop", O2OUtil.copyObject(shop, ShopVO.class));
        // TODO: 2018/6/29 2.2、【店铺数据】（交易量、违约次数、被举报次数、被投诉次数）
        // TODO: 2018/6/29 3、【买家评价】
        //5.1、【猪场基础信息】
        PiggeryEntity piggery = piggeryMapper.getById(productPig.getPiggeryId());
        if (ObjectUtil.isEmpty(piggery)) return res.fail();
        map.put("piggery", piggery);
        //5.2、【猪场指标】
        List<PiggeryIndexEntity> piggeryIndexList  = piggeryIndexMapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, whereIn("piggery_id", piggery.getId()));
        if (ListUtil.isEmpty(piggeryIndexList)) return res.fail();
        map.put("piggeryIndex", piggeryIndexList.get(0));
        return res.success(map);
    }

    /**
     * 猪源--删除
     */
    @Transactional
    @Override
    public ResponseData<Object> deleteProductPig(JSONObject jo) throws Exception {
        ResponseData<Object> res = new ResponseData<>();
        //1、参数验证
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        String ids = ValidateUtil.requiredSingle(jo, "ids");//猪场id
        //2、修改猪源状态为已删除
        int updateRes = mapper.updateBySQL(set("sys_state", SysStateEnum.已删除.getCode().toString()) + whereIn("id", ids) + andEqual("product_state", ProductStateEnum.下架.getCode().toString()));
        List<String> idList = StringUtil.getListFromString(ids, BaseConfig.SPLIT_SIGN_COMMA);
        if ((updateRes == 0) || (idList.size() != updateRes)) {
            throw new CustomException(ResponseEnum.EXCEPTION.getMsg());
        }
        //3、记录猪源日志
        idList.forEach(x -> productLogMapper.add(new ProductLogEntity(snowflakeIdUtil.nextId(), x, "删除猪源", account)));
        return res.operateRes(updateRes);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Object> save(JSONObject jo) {
        return super._save(mapper, jo.getJSONObject(SqlConfig.PARAM_ENTITY), null, ProductPigEntity.class, snowflakeIdUtil);
    }

    @Override
    public int delete(String ids) {
        return mapper.delBySQL(whereIn("id", ids));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<Page<ProductPigVO>> getPage(JSONObject jo) {
        return super._getPage(mapper, jo, getQuery(jo));
    }

    /**
     * 获取查询条件
     *
     * @param jo 请求参数json实体
     * @return query 查询条件
     */
    private String getQuery(JSONObject jo) {
        String query = SqlConfig.DEFAULT_QUERY_CONDITION;
        query += (andEqual("shop_id", jo));
        return query;
    }

    @Override
    public int getCount(String query) {
        return mapper.getCount(query);
    }


    @Override
    @SuppressWarnings("unchecked")
    public E getById(String id) {
        return (E) mapper.getById(id);
    }

    /**
     * 根据条件查询集合（默认限制每次最大查100条）
     *
     * @param jo 请求json
     * @return 响应结果
     */
    @Override
    @SuppressWarnings("unchecked")
    public ResponseData<List<E>> getBySQL(JSONObject jo) {
        ResponseData<List<E>> res = new ResponseData<>();
        return res.success((List<E>) mapper.getBySQL(SqlConfig.DEFAULT_QUERY_FIELDS, getQuery(jo) + SqlConfig.DEFAULT_QUERY_LIMIT));
    }

}
