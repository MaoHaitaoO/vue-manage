package com.manage.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.manage.core.cache.SystemConfigCache;
import com.manage.core.constant.SystemConfigKey;
import com.manage.core.po.User;
import com.manage.core.util.BeanCopierUtil;
import com.manage.core.util.PoiUtil;
import com.manage.core.util.crypto.RSAUtil;
import com.manage.server.dao.UserDao;
import com.manage.server.service.UserService;
import com.manage.server.vo.user.ExportUserReq;
import com.manage.server.vo.user.UserDTO;
import com.manage.server.vo.user.UserListReq;
import com.manage.server.vo.user.UserListRes;

@Service
public class UserServiceImpl implements UserService {

	private static final int PAGE_SIZE = 1000;

	private static final int ONCE_INSERT_NUMBER = 1000;

	private static final String PWD = "mht";

	@Autowired
	private UserDao userDao;

	@Override
	public UserDTO getUser(int id) {
		User user = userDao.selectById(id);
		return BeanCopierUtil.copy(user, UserDTO.class);
	}

	@Override
	public UserListRes userList(UserListReq req) {
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<User> userList = userDao.findAll();
		// copy推荐使用cglib的BeanCopier，效率高
		List<UserDTO> list = BeanCopierUtil.copyList(userList, UserDTO.class);

		PageInfo<User> pageInfo = new PageInfo<>(userList);
		return buildUserListRes(pageInfo, list);
	}

	@Override
	public void exportUser(HttpServletResponse response, ExportUserReq req) {
		List<User> userList = userDao.selectByIds(req.getList());
		// copy推荐使用cglib的BeanCopier，效率高
		List<UserDTO> list = BeanCopierUtil.copyList(userList, UserDTO.class);
		PoiUtil.exportExcel(list, "用户列表", "用户信息sheet", UserDTO.class, "用户列表.xls", true, response);
	}

	@Override
	@Transactional
	public void importUser(MultipartFile multipartFile) {
		List<UserDTO> list = PoiUtil.importExcel(multipartFile, UserDTO.class);
		List<User> userList = BeanCopierUtil.copyList(list, User.class);
		for (User user : userList) {
			String publicKey = SystemConfigCache.get(SystemConfigKey.LOGIN_PUBLIC_KEY);
			user.setUserPwd(RSAUtil.encrypt(PWD, publicKey));
		}

		batchInsert(userList);
	}

	private UserListRes buildUserListRes(PageInfo pageInfo, List<UserDTO> list) {
		UserListRes res = new UserListRes();
		res.setTotal(pageInfo.getTotal());
		res.setList(list);
		return res;
	}

	/**
	 * 分批批量插入，sql有长度限制
	 * 
	 * @param list
	 */
	private void batchInsert(List<User> list) {
		if (!CollectionUtils.isEmpty(list)) {
			List<User> tempList = new ArrayList<>();

			for (int i = 0; i < list.size(); i++) {
				tempList.add(list.get(i));
				if ((i + 1) % ONCE_INSERT_NUMBER == 0 || i == list.size() - 1) {
					userDao.batchInsert(tempList);
					tempList.clear();
				}
			}
		}
	}

	/**
	 * 分页处理从表里取出来的数据，针对数据量大的情况
	 * 
	 * @param list
	 */
	private void page(List list) {
		int totalPage = getTotalPage(list.size());
		for (int currentPage = 1; currentPage <= totalPage; currentPage++) {
			PageHelper.startPage(currentPage, PAGE_SIZE, false);
			// 业务处理逻辑
		}
	}

	private int getTotalPage(int count) {
		return count / PAGE_SIZE + ((count % PAGE_SIZE == 0) ? 0 : 1);
	}



}
