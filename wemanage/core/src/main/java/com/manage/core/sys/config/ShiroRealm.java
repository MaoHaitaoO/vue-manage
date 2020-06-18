package com.manage.core.sys.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.manage.core.constant.PermissionType;
import com.manage.core.dao.RolePermissionDao;
import com.manage.core.dao.UserLoginDao;
import com.manage.core.dao.UserRoleDao;
import com.manage.core.po.Permission;
import com.manage.core.po.Role;
import com.manage.core.po.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private UserLoginDao userLoginDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;


	/**
	 * 权限认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		log.info("doGetAuthorizationInfo================");
		User userInfo = (User) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		List<Role> roleList = userRoleDao.selectRoleByUserId(userInfo.getUserId());
		Set<String> roleSet = new HashSet<>();
		for (Role role : roleList) {
			roleSet.add(role.getRoleName());
		}
		simpleAuthorizationInfo.addRoles(roleSet);

		List<Permission> permissionList =
				rolePermissionDao.selectPermissionByUserIdAndType(userInfo.getUserId(), PermissionType.TYPE_INTERFACE);
		Set<String> permissionSet = new HashSet<>();
		for (Permission permission : permissionList) {
			permissionSet.add(permission.getPermissionExp());
		}

		simpleAuthorizationInfo.addStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		log.info("doGetAuthenticationInfo================");

		String username = (String) token.getPrincipal();
		/**
		 * 前面已经验证完密码，此处略去校验
		 */
		// String password = new String((char[]) token.getCredentials());
		User user = userLoginDao.selectByUsername(username);


		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
				user.getUserPwd(), // 密码
				getName() // realm name
		);
		return authenticationInfo;
	}
}
