package com.trade.core.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import util.LogUtil;
import util.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * excel工具类
 *
 * @author lx
 * @since 2018-07-02 17:03:08
 */
@SuppressWarnings("unused")
public class ExcelUtil {

    /**
     * 导出excel
     *
     * @param list           list
     * @param title          title
     * @param sheetName      sheetName
     * @param pojoClass      pojoClass
     * @param fileName       fileName
     * @param isCreateHeader isCreateHeader
     * @param response       response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    /**
     * 导出excel
     *
     * @param list      list
     * @param title     title
     * @param sheetName sheetName
     * @param pojoClass pojoClass
     * @param fileName  fileName
     * @param response  response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出excel
     *
     * @param list     list
     * @param fileName fileName
     * @param response response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 默认导出excel
     *
     * @param list         list
     * @param pojoClass    pojoClass
     * @param fileName     fileName
     * @param response     response
     * @param exportParams exportParams
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) downLoadExcel(fileName, response, workbook);
    }

    /**
     * 下载excel
     *
     * @param fileName fileName
     * @param response response
     * @param workbook workbook
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            LogUtil.error("downLoadExcel", e);
        }
    }

    /**
     * 默认导出excel
     *
     * @param list     list
     * @param fileName fileName
     * @param response response
     */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) downLoadExcel(fileName, response, workbook);
    }

    /**
     * 导入excel
     *
     * @param filePath   filePath
     * @param titleRows  titleRows
     * @param headerRows headerRows
     * @param pojoClass  pojoClass
     * @param <T>        T
     */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtil.isEmpty(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            LogUtil.error("模板不能为空", e);
        } catch (Exception e) {
            LogUtil.error("importExcel", e);
        }
        return list;
    }

    /**
     * 导入excel
     *
     * @param file       file
     * @param titleRows  titleRows
     * @param headerRows headerRows
     * @param pojoClass  pojoClass
     * @param <T>        T
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            LogUtil.error("excel文件不能为空", e);
        } catch (Exception e) {
            LogUtil.error("importExcel", e);
        }
        return list;
    }
}
