package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.ProductStateEnum;
import com.trade.core.entity.PiggeryEntity;
import com.trade.core.entity.ProductPigEntity;
import com.trade.core.service.IPiggeryService;
import com.trade.core.service.IProductPigService;
import constants.BaseConfig;
import constants.SqlConfig;
import entity.FileHeper;
import entity.ResponseData;
import exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import util.FileUtil;
import util.StringUtil;
import util.ValidateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 猪场 controller
 *
 * @author lx
 * @since 2018-6-13 11:24:48
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/piggery")
public class PiggeryController extends BaseController {
    private static final String FN = "猪场";
    @Autowired
    private IPiggeryService<PiggeryEntity> service;
    @Autowired
    private IProductPigService<ProductPigEntity> productPigService;
    @Autowired
    private Environment env;

    /**
     * 获取猪源上传目录
     */
    private String getFileDir(JSONObject jo) throws CustomException {
        return env.getProperty("cfg.upload_dir") + env.getProperty("cfg.upload_file_piggery") + "/" + ValidateUtil.requiredSingle(jo, "account_id") + "/";//上传文件路径
    }

    /**
     * 图片上传
     */
    private void uploadImg(HttpServletRequest request, JSONObject jo, String fileDir) throws CustomException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(BaseConfig.UPLOAD_IMG);//本次上传的文件
        FileHeper fileHeper = new FileHeper(files, fileDir, 6, BaseConfig.UPLOAD_IMG_FORMAT, BaseConfig.UPLOAD_IMG_SIZE).upload();//文件上传
        if (fileHeper.NOT_SUCCESS()) throw new CustomException(fileHeper.getMsg());
        if (StringUtil.isNotEmpty(fileHeper.getFilePath())) {
            jo.put("piggery_img", fileHeper.getFilePath());
        }
    }

    /**
     * 猪场-添加
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/add.do
     */
    @PostMapping(value = "/add" + BaseConfig.API_SUFFIX)
    public void add(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        try {
            uploadImg(request, jo, getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());
            res = service.add(jo);
        } catch (Exception e) {
            res.exception(FN + "-添加", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("piggery_img"));//添加失败，删除本次上传的图片
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 猪场-编辑
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/update.do
     */
    @PostMapping(value = "/update" + BaseConfig.API_SUFFIX)
    public void update(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        PiggeryEntity old = new PiggeryEntity();//修改前猪场
        try {
            String id = ValidateUtil.requiredSingle(jo, "id");//猪场id
            //查询该猪场的在售猪源
            int onSale = productPigService.getCount(String.format(" WHERE piggery_id = '%s' AND product_state = %s ", id, ProductStateEnum.上架.getCode()));
            if (onSale > 0) {
                res.fail("该猪场有在售猪源，需要先删除在售猪源，再执行本操作");
                return;
            }
            old = service.getById(id);//查询编辑的猪场
            uploadImg(request, jo, getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());//构造保存猪场json实体
            res = service.save(jo);//执行保存
        } catch (Exception e) {
            res.exception(FN + "-编辑", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("piggery_img"));//编辑失败，删除本次上传的图片
            } else {
                if (StringUtil.isNotEmpty(jo.getString("piggery_img"))) {
                    FileUtil.deleteFiles(old.getPiggeryImg());//编辑成功，删除之前上传的图片
                }
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 猪场-置顶
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/setTop.do
     */
    @PostMapping(value = "/setTop" + BaseConfig.API_SUFFIX)
    public void setTop(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "setTop", FN);
    }

    /**
     * 猪场-删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/deletePiggery.do
     */
    @PostMapping(value = "/deletePiggery" + BaseConfig.API_SUFFIX)
    public void deletePiggery(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "deletePiggery", FN);
    }

    /**
     * 猪场-保存
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request, response, service, FN);
    }

    /**
     * 猪场-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request, response, service, FN);
    }

    /**
     * 猪场-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "detail", FN);
    }

    /**
     * 猪场-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/piggery/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }

}
