package util;

import constants.BaseConfig;
import entity.FileHeper;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 1. 功能：文件工具类
 * 2. 作者：lx
 * 3. 创建日期：2018-6-19 09:50:12
 */
@SuppressWarnings("unused")
public class FileUtil {

    /**
     * 文件上传
     *
     * @param fileHeper 文件帮助类
     * @author lx
     * @since 2018-6-21 09:04:32
     */
    public static FileHeper upload(FileHeper fileHeper) {
        List<String> fileNames = new ArrayList<>();//上传文件全路径集合（用于出问题还原）
        for (int i = 0; i < fileHeper.getFiles().size(); ++i) {
            MultipartFile file = fileHeper.getFiles().get(i);
            if (!file.isEmpty()) {
                FileUtil.createDir(fileHeper.getFileDir());//创建文件上传路径
                String fieName = file.getOriginalFilename();//文件名
                String fileSuffix = fieName.substring(fieName.lastIndexOf("."));//文件后缀名
                String fileFullPath = fileHeper.getFileDir() + UUID.randomUUID() + fileSuffix;//完整文件地址（文件路径+文件名）
                //数量验证
                if (fileHeper.getFiles().size() > fileHeper.getFileNum()) {
                    return fileHeper.fail("文件数量超限", fileNames);
                }
                //格式验证（只支持 *.jpg,*.jpeg,*.png格式）
                if (StringUtil.isNotEmpty(fileHeper.getFileFormatReg()) && StringUtil.notMatches(fileHeper.getFileFormatReg(), fieName)) {
                    return fileHeper.fail("文件格式错误", fileNames);
                }
                //大小验证
                if (file.getSize() > fileHeper.getFileSize()) {
                    return fileHeper.fail("文件大小超限", fileNames);
                }
                //执行上传
                fileNames.add(fileFullPath);
                try {
                    byte[] bytes = file.getBytes();//文件流
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fileFullPath)));
                    stream.write(bytes);
                    stream.close();
                    LogUtil.info("【文件上传成功】" + fileFullPath);
                } catch (Exception e) {
                    LogUtil.error("【文件上传异常】" + fileFullPath, e);
                    return fileHeper.fail("文件上传异常", fileNames);
                }
            } else {
                LogUtil.error("【文件上传失败，第" + i + "个文件为空】", null);
                return fileHeper.fail("文件上传失败，第" + i + "个文件为空", fileNames);
            }
        }
        //上传完整性校验
        if (fileHeper.getFiles().size() != fileNames.size()) {
            return fileHeper.fail("部分文件上传失败", fileNames);
        }
        return fileHeper.success(fileNames);
    }

    /**
     * 创建目录
     *
     * @param dirName 目标目录名
     */
    private static void createDir(String dirName) {
        if (StringUtil.isNotEmpty(dirName)) {
            File dir = new File(dirName);
            if (!dir.exists()) {
                boolean res = dir.mkdirs();
                LogUtil.info("【创建目录】" + dirName + "【" + (res ? "成功" : "失败") + "】");
            }
        }

    }

    /**
     * 删除文件集合
     *
     * @param fileNames String 文件路径及名称集合 如c:/fqf.txt
     */
    public static void deleteFiles(List<String> fileNames) {
        if (ListUtil.isNotEmpty(fileNames)) {
            for (String f : fileNames) {
                deleteFile(f);
            }
        }
    }

    /**
     * 删除文件集合
     *
     * @param fileNames String 文件路径及名称集合 如c:/fqf.txt|c:/fqf2.txt
     */
    public static void deleteFiles(String fileNames) {
        deleteFiles(StringUtil.getListFromString(fileNames, "\\" + BaseConfig.SPLIT_SIGN));
    }

    /**
     * 删除文件集合
     *
     * @param fileNames String 文件路径及名称集合 如 c:/fqf.txt|c:/fqf2.txt
     * @param fileDir   String 文件路径前缀，如 d://upload_dir/trade/dev/
     */
    public static void deleteShortFiles(String fileNames, String fileDir) {
        List<String> list = StringUtil.getListFromString(fileNames, "\\" + BaseConfig.SPLIT_SIGN);
        String newFileNames = StringUtil.getStringFromListAddPrefix(list, fileDir, BaseConfig.SPLIT_SIGN);
        deleteFiles(StringUtil.getListFromString(newFileNames, "\\" + BaseConfig.SPLIT_SIGN));
    }

    /**
     * 删除文件
     *
     * @param fileName String 文件路径及名称 如c:/fqf.txt
     */
    private static void deleteFile(String fileName) {
        try {
            File file = new File(fileName);
            if(!file.exists()){
                return;
            }
            if (!file.isDirectory()) {
                boolean res = file.delete();
                LogUtil.info("【删除文件" + (res ? "成功" : "失败") + "】" + fileName);
            }
        } catch (Exception e) {
            LogUtil.error("【删除文件】", e);
        }
    }

    /**
     * 文件重命名
     *
     * @param path   文件目录
     * @param source 原来的文件名
     * @param target 新文件名
     */
    public void renameFile(String path, String source, String target) {
        if (!source.equals(target)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldFile = new File(path + "/" + source);
            File newFile = new File(path + "/" + target);
            if (!oldFile.exists()) {
                LogUtil.error(oldFile + "不存在", null);//重命名文件不存在
                return;
            }
            if (newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                LogUtil.error(target + "已经存在", null);
            else {
                boolean res = oldFile.renameTo(newFile);
                if(!res){
                    LogUtil.error(target + "文件重命名是失败", null);
                }
            }
        } else {
            LogUtil.error("新文件名和旧文件名相同", null);
        }
    }
}