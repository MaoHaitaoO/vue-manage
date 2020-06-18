package com.manage.server.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.manage.core.constant.errorcode.CommonErrorEnum;
import com.manage.core.exception.RuleException;
import com.manage.core.util.crypto.Base64Util;
import com.manage.core.validate.Validate;
import com.manage.server.service.FileService;
import com.manage.server.vo.file.FileShow;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileSerivceImpl implements FileService {

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${file.upload-dir.default}")
	private String uploadDirDefaultPath;

	@Value("${file.upload-dir.head}")
	private String uploadDirHeadPath;

	@Value("${file.download-dir.default}")
	private String downloadDirDefaultPath;

	@Override
	public void upload(MultipartFile[] multipartFiles) {
		try {
			String uploadPath = ResourceUtils.getURL("classpath:").getPath() + uploadDirDefaultPath + File.separator;
			File fileDir = new File(uploadPath);
			if (!fileDir.exists() && !fileDir.isDirectory()) {
				fileDir.mkdirs();
			}

			Validate.assertTrue(fileDir.listFiles().length <= 10, CommonErrorEnum.FILE_LENGTH_MAX);

			if (multipartFiles != null && multipartFiles.length > 0) {
				for (int i = 0; i < multipartFiles.length; i++) {
					String storagePath = uploadPath + multipartFiles[i].getOriginalFilename();
					Validate.assertFalse(new File(storagePath).exists(), CommonErrorEnum.FILE_IS_EXISTS);

					log.info("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + ","
							+ multipartFiles[i].getOriginalFilename() + "，保存的路径为：" + storagePath);
					Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);
				}
			}
		} catch (IOException e) {
			throw new RuleException(CommonErrorEnum.FILE_RESOLUTION_ERROR, e.getMessage(), e);
		}

	}

	@Override
	public List<FileShow> fileListShow() {
		List<FileShow> list;
		try {
			String path = ResourceUtils.getURL("classpath:").getPath() + downloadDirDefaultPath + File.separator;
			File fileDir = new File(path);

			list = new ArrayList<>();
			if (fileDir.exists()) {
				File[] files = fileDir.listFiles();
				for (File file : files) {
					String toBase64 = Base64Util.imageToBase64(path + file.getName());

					FileShow fileShow = new FileShow();
					fileShow.setFilename(file.getName());
					fileShow.setImgBase64(toBase64);
					list.add(fileShow);
				}
			}
		} catch (IOException e) {
			throw new RuleException(CommonErrorEnum.FILE_RESOLUTION_ERROR, e.getMessage(), e);
		}
		return list;
	}

	@Override
	public void download(HttpServletResponse response, String filePathName) {
		String path;
		try {
			// String path1 = getClass().getResource("/down").getPath();
			// this.getClass().getResourceAsStream("/static/download/示例样表.xlsx");
			// String contextPath =
			// ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
			path = ResourceUtils.getURL("classpath:").getPath() + downloadDirDefaultPath + File.separator
					+ filePathName;
		} catch (IOException e) {
			throw new RuleException(CommonErrorEnum.FILE_RESOLUTION_ERROR, e.getMessage(), e);
		}

		File file = new File(path);
		Validate.assertTrue(file.exists(), CommonErrorEnum.FILE_IS_NOT_EXISTS);
		try (InputStream inStream = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment;fileName=" + URLEncoder.encode(filePathName, "UTF-8"));
			byte[] buff = new byte[2048];
			int len;
			while ((len = inStream.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
			os.flush();
		} catch (Exception e) {
			throw new RuleException(CommonErrorEnum.FILE_RESOLUTION_ERROR, e.getMessage(), e);
		}
	}

	@Override
	public void importFile() {

	}

	@Override
	public void exportFile() {

	}
}
