package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.Enum.ProductStateEnum;
import com.trade.core.entity.ProductPigEntity;
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
 * 猪源 controller
 *
 * @author lx
 * @since 2018-6-20 14:39:04
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/productPig")
public class ProductPigController extends BaseController {
    private static final String FN = "猪源";
    @Autowired
    private IProductPigService<ProductPigEntity> service;
    @Autowired
    private Environment env;

    /**
     * 获取猪源上传目录
     */
    private String getFileDir(JSONObject jo) throws CustomException {
        return env.getProperty("cfg.upload_dir") + env.getProperty("cfg.upload_file_product_pig") + "/" + ValidateUtil.requiredSingle(jo, "account_id") + "/";//上传文件路径
    }

    /**
     * 图片上传
     */
    private void uploadImg(HttpServletRequest request, JSONObject jo,String fileDir) throws CustomException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(BaseConfig.UPLOAD_IMG);//本次上传的文件
        FileHeper fileHeper = new FileHeper(files,fileDir,3,BaseConfig.UPLOAD_IMG_FORMAT,BaseConfig.UPLOAD_IMG_SIZE).upload();//文件上传
        if (fileHeper.NOT_SUCCESS()) throw new CustomException(fileHeper.getMsg());
        if(StringUtil.isNotEmpty(fileHeper.getFilePath())){
            jo.put("product_img", fileHeper.getFilePath());
        }
    }

    /**
     * 视频上传
     */
    private void uploadVideo(HttpServletRequest request, JSONObject jo,String fileDir) throws CustomException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(BaseConfig.UPLOAD_VIDEO);//本次上传的文件
        FileHeper fileHeper = new FileHeper(files,fileDir,1,BaseConfig.UPLOAD_VIDEO_FORMAT,BaseConfig.UPLOAD_VIDEO_SIZE).upload();//文件上传
        if (fileHeper.NOT_SUCCESS()) throw new CustomException(fileHeper.getMsg());
        if(StringUtil.isNotEmpty(fileHeper.getFilePath())){
            jo.put("product_video", fileHeper.getFilePath());
        }
    }

    /**
     * 猪源-发布猪源
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/publish.do
     */
    @PostMapping(value = "/publish" + BaseConfig.API_SUFFIX)
    public void publish(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        try {
            uploadImg(request,jo,getFileDir(jo));
            uploadVideo(request,jo,getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());
            jo.put(BaseConfig.SESSION_ACCOUNT, getSA(request));//session 验证&&参数处理
            res = service.publish(jo);
        } catch (Exception e) {
            res.exception(FN + "-发布猪源", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("product_img"));//添加失败，删除本次上传的图片
                FileUtil.deleteFiles(jo.getString("product_video"));//添加失败，删除本次上传的视频
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }
    
    /**
     * 猪源-编辑
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/update.do
     */
    @PostMapping(value = "/update" + BaseConfig.API_SUFFIX)
    public void update(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        ProductPigEntity old = new ProductPigEntity();//修改前猪源
        try {
            String id = ValidateUtil.requiredSingle(jo, "id");//猪源id
            old = service.getById(id);//查询编辑的猪源
            if(old.getProductState().equals(ProductStateEnum.上架.getCode())){
                res.fail("无法编辑已上架的猪源，请下架后操作");
                return;
            }
            uploadImg(request,jo,getFileDir(jo));
            uploadVideo(request,jo,getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());
            jo.put(BaseConfig.SESSION_ACCOUNT, getSA(request));//session 验证&&参数处理
            res = service.update(jo);
        } catch (Exception e) {
            res.exception(FN + "-编辑", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("product_img"));//编辑失败，删除本次上传的图片
                FileUtil.deleteFiles(jo.getString("product_video"));//编辑失败，删除本次上传的视频
            } else{
                if(StringUtil.isNotEmpty(jo.getString("product_img"))){
                    FileUtil.deleteFiles(old.getProductImg());//编辑成功，删除之前上传的图片
                }
                if(StringUtil.isNotEmpty(jo.getString("product_video"))){
                    FileUtil.deleteFiles(old.getProductVideo());//编辑成功，删除之前上传的视频
                }
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 猪源-我的猪源
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/myProductPig.do
     */
    @RequestMapping(value = "/myProductPig" + BaseConfig.API_SUFFIX)
    public void myProductPig(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 猪源-猪源-修改状态（上下架）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/changeState.do
     */
    @PostMapping(value = "/changeState" + BaseConfig.API_SUFFIX)
    public void changeState(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "changeState", FN);
    }

    /**
     * 猪源-猪源-搜猪源（买家）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/searchPig.do
     */
    @RequestMapping(value = "/searchPig" + BaseConfig.API_SUFFIX)
    public void searchPig(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "searchPig", FN);
    }

    /**
     * 猪源-猪源-详情（卖家）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/saleDetail.do
     */
    @RequestMapping(value = "/saleDetail" + BaseConfig.API_SUFFIX)
    public void saleDetail(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "saleDetail", FN);
    }

    /**
     * 猪源-详情（买家预订）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/buyDetail.do
     */
    @RequestMapping(value = "/buyDetail" + BaseConfig.API_SUFFIX)
    public void buyDetail(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "buyDetail", FN);
    }

    /**
     * 猪源-删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/deleteProductPig.do
     */
    @PostMapping(value = "/deleteProductPig" + BaseConfig.API_SUFFIX)
    public void deleteProductPig(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, "deleteProductPig", FN);
    }
    /**
     * 猪源-保存
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 猪源-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/getPage.do
     */
    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }
    /**
     * 猪源-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 猪源-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/productPig/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }
}
