package com.trade.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.trade.core.entity.AccountEntity;
import com.trade.core.entity.NoticeEntity;
import com.trade.core.service.INoticeService;
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
 * 公告 controller
 *
 * @author lx
 * @since 2018-07-03 17:03:44
 */
@RestController
@RequestMapping(value = BaseConfig.API_WEB + "/notice")
public class NoticeController extends BaseController {
    private static final String FN = "公告";
    @Autowired
    private INoticeService<NoticeEntity> service;
    @Autowired
    private Environment env;

    /**
     * 获取公告上传目录
     */
    private String getFileDir(JSONObject jo) {
        AccountEntity account = jo.getJSONObject(BaseConfig.SESSION_ACCOUNT).toJavaObject(AccountEntity.class);//获取登录账号信息
        return env.getProperty("cfg.upload_dir") + env.getProperty("cfg.upload_file_notice") + "/" + account.getId() + "/";//上传文件路径
    }
    
    /**
     * 图片上传
     */
    private void uploadImg(HttpServletRequest request, JSONObject jo, String fileDir) throws CustomException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(BaseConfig.UPLOAD_IMG);//本次上传的文件
        FileHeper fileHeper = new FileHeper(files,fileDir,1,BaseConfig.UPLOAD_IMG_FORMAT,BaseConfig.UPLOAD_IMG_SIZE).upload();//文件上传
        if (fileHeper.NOT_SUCCESS()) throw new CustomException(fileHeper.getMsg());
        if(StringUtil.isNotEmpty(fileHeper.getFilePath())){
            jo.put("img_path", fileHeper.getFilePath());
        }
    }

    /**
     * 图片上传
     */
    private void uploadFile(HttpServletRequest request, JSONObject jo, String fileDir) throws CustomException {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(BaseConfig.UPLOAD_FILE);//本次上传的文件
        FileHeper fileHeper = new FileHeper(files,fileDir,1,null,BaseConfig.UPLOAD_FILE_SIZE).upload();//文件上传
        if (fileHeper.NOT_SUCCESS()) throw new CustomException(fileHeper.getMsg());
        if(StringUtil.isNotEmpty(fileHeper.getFilePath())){
            jo.put("file_path", fileHeper.getFilePath());
        }
    }

    /**
     * 公告-发布公告
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/publish.do
     */
    @PostMapping(value = "/publish" + BaseConfig.API_SUFFIX)
    public void publish(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        try {
            jo.put(BaseConfig.SESSION_ACCOUNT, getSA(request));//session 验证&&参数处理
            uploadImg(request,jo,getFileDir(jo));
            uploadFile(request,jo,getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());
            res = service.publish(jo);
        } catch (Exception e) {
            res.exception(FN + "-发布公告", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("img_path"));//添加失败，删除本次上传的图片
                FileUtil.deleteFiles(jo.getString("file_path"));//添加失败，删除本次上传的文件
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }

    /**
     * 公告-编辑
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/update.do
     */
    @PostMapping(value = "/update" + BaseConfig.API_SUFFIX)
    public void update(HttpServletRequest request, HttpServletResponse response) {
        ResponseData<Object> res = new ResponseData<>();
        JSONObject jo = super.getFlatParams(request);
        NoticeEntity old = new NoticeEntity();//修改前公告
        try {
            String id = ValidateUtil.requiredSingle(jo, "id");//公告id
            jo.put(BaseConfig.SESSION_ACCOUNT, getSA(request));//session 验证&&参数处理
            old = service.getById(id);//查询编辑的公告
            uploadImg(request,jo,getFileDir(jo));
            uploadFile(request,jo,getFileDir(jo));
            jo.put(SqlConfig.PARAM_ENTITY, jo.clone());
            res = service.update(jo);
        } catch (Exception e) {
            res.exception(FN + "-编辑", jo, e);
        } finally {
            if (res.NOT_SUCCESS()) {
                FileUtil.deleteFiles(jo.getString("img_path"));//编辑失败，删除本次上传的图片
                FileUtil.deleteFiles(jo.getString("file_path"));//编辑失败，删除本次上传的文件
            } else{
                if(StringUtil.isNotEmpty(jo.getString("img_path"))){
                    FileUtil.deleteFiles(old.getImgPath());//编辑成功，删除之前上传的图片
                }
                if(StringUtil.isNotEmpty(jo.getString("file_path"))){
                    FileUtil.deleteFiles(old.getFilePath());//编辑成功，删除之前上传的文件
                }
            }
            returnData(request, response, JSON.toJSONString(res));
        }
    }
    
    /**
     * 公告-保存（新增 or 修改）
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/save.do
     */
    @PostMapping(value = "/save" + BaseConfig.API_SUFFIX)
    public void save(HttpServletRequest request, HttpServletResponse response) {
        super._save(request,response,service,FN);
    }

    /**
     * 公告-分页查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/getPage.do
     */

    @RequestMapping(value = "/getPage" + BaseConfig.API_SUFFIX)
    public void getPage(HttpServletRequest request, HttpServletResponse response) {
        super._getPage(request,response,service,FN);
    }

    /**
     * 公告-详情查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/detail.do
     */
    @RequestMapping(value = "/detail" + BaseConfig.API_SUFFIX)
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        super._detail(request,response, service,FN);
    }

    /**
     * 公告-根据条件查询
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/list.do
     */
    @RequestMapping(value = "/list" + BaseConfig.API_SUFFIX)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        super._doService(request, response, service, SqlConfig.GET_BY_SQL, FN);
    }

    /**
     * 公告-删除
     * 本地接口地址：http://127.0.0.1:8660/api/trade/web/notice/delete.do
     */
    @RequestMapping(value = "/delete" + BaseConfig.API_SUFFIX)
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        super._deleteInIds(request,response, service,FN);
    }
}
