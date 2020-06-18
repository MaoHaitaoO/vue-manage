package com.manage.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.manage.server.vo.file.FileShow;

public interface FileService {
	void upload(MultipartFile[] multipartFiles);

	List<FileShow> fileListShow();

	void download(HttpServletResponse response, String filePathName);

	void importFile();

	void exportFile();

}
