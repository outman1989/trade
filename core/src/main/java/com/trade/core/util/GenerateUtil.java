package com.trade.core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.DateUtil;
import util.FileUtil;
import util.LogUtil;
import util.StringUtil;

import java.io.*;

/**
 * @author lx
 * @since 2018-06-25 14:03:44
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class GenerateUtil {
    private static final String OUTSIDE_DIR = "D:\\TableGo\\JavaBean\\Entity\\";
    private static final String CODE_DIR = "D:\\IntelliJ IDEA 2017.3.2\\project\\trade\\core\\src\\main\\java\\com\\trade\\core\\";
    private static final String ENTITY = "entity\\";
    private static final String[] ITEMS = {"sqlbuilder\\", "mapper\\", "service\\", "service\\impl\\", "controller\\"};
    private static final String[] default_target = {"SysLog", "sysLog", "系统日志", "2018-6-6 06:06:06"};
    private String[] target;
    private String[] replace;

    /**
     * 快速生成基础代码
     */
    public static void main(String args[]) {
//        String fn = "News";
//        String fn_cn = "消息";
//        generateEntity(fn);//生成实体类
//        makeBaseCode(fn, fn_cn);//生成基础代码：sqlbuilder, mapper, service, serviceImpl, controller
    }

    /**
     * 初始化
     *
     * @param replace   替换功能名
     * @param replaceCn 替换功能名中文
     */
    private GenerateUtil(String replace, String replaceCn) {
        this.target = default_target;
        this.replace = new String[]{replace, (replace.substring(0, 1).toLowerCase() + replace.substring(1)), replaceCn, DateUtil.getDateStringyyyy_MM_dd_HHmmss()};
    }

    /**
     * 生成基础代码
     */
    public static String makeBaseCode(String replace, String replaceCn) {
        String res;
        GenerateUtil gu = new GenerateUtil(replace, replaceCn);
        res = generate(gu, CODE_DIR + ITEMS[0] + default_target[0] + "SqlBuilder.java", CODE_DIR + ITEMS[0] + replace + "SqlBuilder.java");
        if(StringUtil.isNotEmpty(res)) return res;
        res = generate(gu, CODE_DIR + ITEMS[1] + "I"+default_target[0] + "Mapper.java", CODE_DIR + ITEMS[1] + "I"+ replace + "Mapper.java");
        if(StringUtil.isNotEmpty(res)) return res;
        res = generate(gu, CODE_DIR + ITEMS[2] + "I"+default_target[0] + "Service.java", CODE_DIR + ITEMS[2] + "I"+ replace + "Service.java");
        if(StringUtil.isNotEmpty(res)) return res;
        res = generate(gu, CODE_DIR + ITEMS[3] + default_target[0] + "ServiceImpl.java", CODE_DIR + ITEMS[3] + replace + "ServiceImpl.java");
        if(StringUtil.isNotEmpty(res)) return res;
        res = generate(gu, CODE_DIR + ITEMS[4] + default_target[0] + "Controller.java", CODE_DIR + ITEMS[4] + replace + "Controller.java");
        if(StringUtil.isNotEmpty(res)) return res;
        return null;
    }

    /**
     * 生成代码
     */
    private static String generate(GenerateUtil gu, String targetPath, String replacePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            /* 读入TXT文件 */
            File sourceFile = new File(targetPath); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(sourceFile)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            File targetFile = new File(replacePath); // 相对路径，如果没有则要建立一个新的output。txt文件
            boolean resCreateNewFile = targetFile.createNewFile(); // 创建新文件
            if (!resCreateNewFile) {
                LogUtil.error("创建文件失败", null);
                return "创建文件失败";
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(targetFile));
            String line;
            line = br.readLine();
            while (line != null) {
                //【正常读写】
                contentBuilder.append(line).append("\r\n");
                line = br.readLine();
            }
            String content = contentBuilder.toString();
            for (int i = 0; i < gu.replace.length; i++) {
                content = content.replace(gu.target[i], gu.replace[i]);
            }
            out.write(content);
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
            reader.close();
            LogUtil.info("【生成代码成功】"+replacePath);
            return null;
        } catch (Exception e) {
            LogUtil.error("【生成代码异常】\r\n【targetPath】:" +targetPath + "\r\n~【replacePath】:"+replacePath, e);
            return ("【生成代码异常】\r\n【targetPath】:" +targetPath + "\r\n~【replacePath】:"+replacePath);
        }
    }

    /**
     * 生成实体类
     */
    public static String generateEntity(String entityName) {
        try {
            /* 读入TXT文件 */
            File sourceFile = new File(OUTSIDE_DIR + entityName + "Entity.java"); // 要读取以上路径的input。txt文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(sourceFile)); // 建立一个输入流对象reader
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            FileUtil.deleteFiles(CODE_DIR + ENTITY + entityName+"Entity.java");//删除之前存在的文件
            File targetFile = new File(CODE_DIR + ENTITY + entityName+"Entity.java"); // 相对路径，如果没有则要建立一个新的output。txt文件
            boolean resCreateNewFile = targetFile.createNewFile(); // 创建新文件
            if (!resCreateNewFile) {
                LogUtil.error("创建文件失败", null);
                return "创建文件失败";
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(targetFile));
            String line;
            line = br.readLine();
            int lineNum = 0;
            int jsonOrdinal = 10;//json顺序
            while (line != null) {
                lineNum++;
                //【去掉注释】
                if (lineNum < 13) {
                    line = br.readLine();
                    continue;
                }
                //【添加导包】
                if (line.contains("package")) {
                    out.write(line + "\r\n");
                    out.write("import com.alibaba.fastjson.annotation.JSONField;" + "\r\n");
                    out.write("import lombok.Getter;" + "\r\n");
                    out.write("import lombok.NoArgsConstructor;" + "\r\n");
                    out.write("import lombok.Setter;" + "\r\n");
                    out.write("import javax.persistence.*;" + "\r\n");
                    line = br.readLine();
                    continue;
                }
                //【忽略导包】
                if (line.contains("persistence.")) {
                    line = br.readLine();
                    continue;
                }
                //【注释时间】
                if (line.contains("@version")) {
                    out.write(" * @since " + DateUtil.getDateStringyyyy_MM_dd_HHmmss() + "\r\n");
                    line = br.readLine();
                    continue;
                }
                //【注解】
                if (line.contains("@Entity")) {
                    out.write(line + "\r\n");
                    out.write("@Getter" + "\r\n");
                    out.write("@Setter" + "\r\n");
                    out.write("@NoArgsConstructor" + "\r\n");
                    line = br.readLine();
                    continue;
                }
                //【@Id】
                if (line.contains("@Id")) {
                    out.write(line + "\r\n");
                    out.write("\t@GeneratedValue" + "\r\n");
                    line = br.readLine();
                    continue;
                }
                //【@Column】
                if (line.contains("@Column")) {
                    line = line.replace(" nullable = true,", "");
                    out.write(line + "\r\n");
                    String format = line.split("\"")[1].contains("_time") ? " format = \"yyyy-MM-dd HH:mm:ss\"," : "";
                    out.write("\t@JSONField(name = \"" + line.split("\"")[1] + "\"," + format + " ordinal = " + jsonOrdinal + ")" + "\r\n");
                    jsonOrdinal += 10;
                    line = br.readLine();
                    continue;
                }
                //【去掉Get、Set方法】
                if (line.contains("/**")) {
                    String oldLine = line;
                    line = br.readLine();
                    if (line.contains("* 获取")) {
                        out.write("}");
                        break;
                    } else {
                        out.write(oldLine + "\r\n");
                        continue;
                    }
                }
                //【正常读写】
                out.write(line + "\r\n");
                line = br.readLine();
            }
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
            reader.close();
            LogUtil.info("【生成实体类成功】"+targetFile.getAbsolutePath());
            return null;
        } catch (Exception e) {
            LogUtil.error("【生成实体类异常】", e);
            return "生成实体类异常";
        }
    }

}
