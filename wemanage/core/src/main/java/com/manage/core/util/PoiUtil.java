package com.manage.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import com.manage.core.constant.errorcode.CommonErrorEnum;
import com.manage.core.exception.RuleException;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

public class PoiUtil {
	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			boolean isCreateHeader, HttpServletResponse response) {
		ExportParams exportParams = new ExportParams(title, sheetName);
		exportParams.setCreateHeadRows(isCreateHeader);
		defaultExport(list, pojoClass, fileName, response, exportParams);

	}

	public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
			HttpServletResponse response) {
		defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
	}

	public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		defaultExport(list, fileName, response);
	}

	private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
			ExportParams exportParams) {
		Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
		if (workbook != null) {
			downLoadExcel(fileName, response, workbook);
		}
	}

	private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-Type", "application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
	}

	private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
		Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
		if (workbook != null) {
			downLoadExcel(fileName, response, workbook);
		}
	}

	public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
		if (StringUtils.isBlank(filePath)) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
		return list;
	}

	public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
			Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		List<T> list;
		try {
			list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
		return list;
	}

	public static <T> List<T> importExcel(MultipartFile file, String path, Integer titleRows, Integer headerRows,
			Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		params.setTitleRows(titleRows);
		params.setHeadRows(headerRows);
		params.setSaveUrl(path);
		params.setNeedSave(false);
		List<T> list;
		try {
			list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
		return list;
	}

	public static <T> List<T> importExcel(MultipartFile file, Class<T> pojoClass) {
		if (file == null) {
			return null;
		}
		ImportParams params = new ImportParams();
		List<T> list;
		try {
			list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.SYSTEM_ERROR, e.getMessage(), e);
		}
		return list;
	}
}
