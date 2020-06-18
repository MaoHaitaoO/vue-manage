package com.manage.server.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.manage.server.vo.user.ExportUserReq;
import com.manage.server.vo.user.UserDTO;
import com.manage.server.vo.user.UserListReq;
import com.manage.server.vo.user.UserListRes;

public interface UserService {

	UserDTO getUser(int id);

	UserListRes userList(UserListReq req);

	void exportUser(HttpServletResponse response, ExportUserReq req);

	void importUser(MultipartFile multipartFiles);
}
