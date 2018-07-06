package util;

import constants.BaseConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 1. 功能：字符串工具类
 * 2. 作者：SYSTEM
 * 3. 创建日期：2016-5-20 15:42:54
 */
@SuppressWarnings("unused")
public class StringUtil {

    /**
     * 正则校验
     */
    public static boolean matches(String reg, String str) {
        if (StringUtil.isEmpty(reg)) {
            return true;
        }
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str).matches();
    }

    /**
     * 正则校验
     */
    public static boolean notMatches(String reg, String str) {
        return !matches(reg, str);
    }

    /**
     * 判断存在特殊字符
     */
    public static boolean filterSpecialChar(String str) {
        Pattern pattern = Pattern.compile(BaseConfig.SPECIAL_CHARACTER);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    /**
     * 过滤特殊字符
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        Pattern pattern = Pattern.compile(BaseConfig.SPECIAL_CHARACTER);
        Matcher matcher = pattern.matcher(str);
        String trim = matcher.replaceAll("").trim();
        return trim.replaceAll(" ", "");
    }

    /**
     * 字符串非空验证
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return (null == str) || (str.length() == 0) || ("".equals(str.trim())) || ("null".equalsIgnoreCase(str));
    }

    /**
     * 字符串非空验证2
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 字符串集合转文件名拼接
     *
     * @param fileNames 字符串集合
     * @param sign      拼接符号
     * @return 字符换
     */
    public static String getStringFromList(List<String> fileNames, String sign) {
        if (ListUtil.isEmpty(fileNames)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String f : fileNames) {
            sb.append(f).append(sign);
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 字符串集合转文件名拼接
     *
     * @param fileNames 字符串集合
     * @param prefix 拼接前缀
     * @param sign      拼接符号
     * @return 字符换
     */
    public static String getStringFromListAddPrefix(List<String> fileNames, String prefix, String sign) {
        if (ListUtil.isEmpty(fileNames)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String f : fileNames) {
            sb.append(prefix).append(f).append(sign);
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 文件名拼接转字符串集合
     *
     * @param fileNames 字符串集合
     * @param sign      拼接符号
     * @return 字符换
     */
    public static List<String> getListFromString(String fileNames, String sign) {
        if (StringUtil.isNotEmpty(fileNames) && StringUtil.isNotEmpty(sign)) {
            return Arrays.asList(fileNames.split(sign));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 字符串非空验证&替换
     *
     * @param str        源字符串
     * @param replaceStr 替换字符串
     * @return 最终字符串
     */
    public static String replaceEmpty(String str, String replaceStr) {
        return isEmpty(str) ? replaceStr : str;
    }

    /**
     * 空字符换处理
     *
     * @param str        源字符串
     * @return 最终字符串
     */
    public static String dealEmpty(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String getLimitLengthString(String str, int length) {
        String view = null;
        int counterOfDoubleByte = 0;
        if (str == null)
            return "";
        try {
            byte[] b = str.getBytes("GBK");
            if (b.length <= length)
                return str;
            for (int i = 0; i < length; i++) {
                if (b[i] > 0)
                    counterOfDoubleByte++;
            }
            if (counterOfDoubleByte % 2 == 0)
                view = new String(b, 0, length, "GBK") + "...";
            else
                view = new String(b, 0, length - 1, "GBK") + "...";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * 去除String 类型的所有空格
     */
    public static String replaceSpace(String value) {
        if (value != null) {
            value = value.replaceAll("\\s*", "");
        }
        return value;
    }

    /**
     * 判断字母或数字
     *
     * @return boolean 判断结果
     * @author lx
     */
    private static boolean zMOrSZ(String str) {
        Pattern pattern = Pattern.compile("[0-9]|[a-z]");
        Matcher ms = pattern.matcher(str);
        return ms.matches();
    }

    /**
     * 驼峰转下划线
     *
     * @param str 转换前的SQL语句
     * @return string 转换后的SQL语句
     */
    public static String camelToUnderline(String str) {
        if (str == null || "".equals(str.trim())) {
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c) && (i > 0 && zMOrSZ(String.valueOf(str.charAt(i - 1))))) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//		String sql= "Select * FROM demo WHERE 1=1  and dLoginName ='dLoginName' or ID =2"; // "aEd,aWdEw";
//        System.out.println(camelToUnderline(sql));
//        System.out.println(zMOrSZ("a"));
//        StringBuilder sb =new StringBuilder("sfsdfsd|sfsdfg|dsfsdf");
//        List<String> list = getListFromString(sb.toString(),"\\"+BaseConfig.IMG_SPLIT_SIGN);
//        String s = getStringFromList(list,BaseConfig.IMG_SPLIT_SIGN);
//        System.out.println(s);
    }

}
