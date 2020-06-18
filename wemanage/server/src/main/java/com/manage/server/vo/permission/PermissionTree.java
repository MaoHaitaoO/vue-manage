package com.manage.server.vo.permission;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionTree {

	private Integer permissionId;

	private Integer parentId;

	private String permissionName;

	private String permissionExp;

	private String routerUrl;

	private String permissionOrder;

	private String type;

	private String isHasPermission;

	private List<PermissionTree> children = new ArrayList<>();
}
