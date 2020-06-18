package com.manage.server.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.manage.core.vo.R;
import com.manage.server.service.FileService;
import com.manage.server.vo.file.FileListShowRes;
import com.manage.server.vo.file.FileShow;

@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@PostMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile[] multipartFiles) {
		fileService.upload(multipartFiles);
		return R.ok();
	}

	@PostMapping("/download")
	public void download(HttpServletResponse response, @Valid @NotBlank(message = "文件名称不能为空") String fileName) {
		fileService.download(response, fileName);
	}

	@RequiresPermissions("file:fileListShow")
	@GetMapping("/fileListShow")
	public R fileListShow() {
		List<FileShow> list = fileService.fileListShow();

		FileListShowRes res = new FileListShowRes();
		res.setList(list);
		return R.ok().data(res);
	}

	@PostMapping("/importFile")
	public void importFile() {

	}

	@GetMapping("/exportFile")
	public void exportFile() {

	}

}
