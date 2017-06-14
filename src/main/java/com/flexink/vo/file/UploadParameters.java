package com.flexink.vo.file;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadParameters {

	private File file;

	private MultipartFile multipartFile;
	
	private List<MultipartFile> multipartFiles;

	private String targetType;

	private String targetId;

	private int sort;

	private String desc;

	private boolean deleteIfExist;

	private boolean thumbnail = true;

	private int thumbnailWidth = 150;

	private int thumbnailHeight = 150;
	
	private String savePath;
}
