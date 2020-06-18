package com.manage.core.po;

import lombok.Data;

@Data
public class Error {

    private String errorKey;

    private String errorCode;

    private String errorMsg;
}