package com.manage.core.po;

import lombok.Data;

@Data
public class Permission {
    private Integer permissionId;

    private Integer parentId;

    private String permissionName;

    private String permissionExp;

    private String routerUrl;

    private String permissionOrder;

    private String type;
}