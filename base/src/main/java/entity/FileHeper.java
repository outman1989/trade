package entity;

import constants.BaseConfig;
import constants.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtil;
import util.StringUtil;

import java.util.List;

/**
 * 文件帮助类
 *
 * @author lx
 * @since 2018-06-20 17:30:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileHeper {
    private List<MultipartFile> files;//文件集合
    private String fileDir;// 文件保存路径
    private Integer fileNum;// 文件数量
    private String fileFormatReg;// 文件格式正则
    private Long fileSize;// 文件大小（单位：KB）
    private String filePath;//本次上传文件的路径（上传成功保存的文件路径，支持多个，以|分隔）
    private Integer status;// 上传状态 0=成功，1=失败
    private String msg;// 上传信息

    public FileHeper(List<MultipartFile> files, String fileDir, Integer fileNum, String fileFormatReg, Long fileSize) {
        this.files = files;
        this.fileDir = fileDir;
        this.fileNum = fileNum;
        this.fileFormatReg = fileFormatReg;
        this.fileSize = fileSize;
    }

    public boolean SUCCESS(){
        return this.status == 0;
    }

    public boolean NOT_SUCCESS(){
        return !(this.status == 0);
    }

    /**
     * 文件上传
     */
    public FileHeper upload() {
        return FileUtil.upload(this);
    }

    /**
     * 返回失败
     *
     * @param failMsg 错误信息
     */
    public FileHeper fail(String failMsg) {
        return fail(failMsg,null);
    }

    /**
     * 返回失败
     *
     * @param failMsg 错误信息
     * @param fileNames 上传文件路径记录
     */
    public FileHeper fail(String failMsg,List<String> fileNames) {
        FileUtil.deleteFiles(fileNames);//删除已上传文件
        this.status = ResponseEnum.FAIL.getStatus();
        this.msg = failMsg;
        return this;
    }

    /**
     * 返回成功
     *
     * @param fileNames 上传文件路径记录
     */
    public FileHeper success(List<String> fileNames) {
        this.status = ResponseEnum.SUCCESS.getStatus();
        this.filePath = StringUtil.getStringFromList(fileNames, BaseConfig.SPLIT_SIGN);
        return this;
    }
}
