package com.manage.server.vo.file;

import java.util.List;

import com.manage.core.vo.BaseDataRes;

import lombok.Data;

@Data
public class FileListShowRes extends BaseDataRes {

	private List<FileShow> list;
}
