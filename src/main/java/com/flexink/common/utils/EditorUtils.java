package com.flexink.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorUtils {

	/********************************************************************
	 * @메소드명	: getImgSrc
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: CkEditor 컨텐츠 중 <img> 태그 의 src 리스트 조회 
	 ********************************************************************/
	public static List<String> getImgSrc(String str) {
		Pattern nonValidPattern = Pattern
				.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		List<String> result = new ArrayList<String>();
		Matcher matcher = nonValidPattern.matcher(str);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		return result;
	}
	
	/********************************************************************
	 * @메소드명	: getImgIds
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: CkEditor img Id 리스트 조회
	 ********************************************************************/
	public static List<String> getImgIds(String str) {
		List<String> result = new ArrayList<String>();
		
		List<String> imgSrcs = getImgSrc(str);
		
		for(String s : imgSrcs) {
			if(s.contains("/files/preview/")) {
				result.add(s.substring(s.lastIndexOf("/") + 1));
			}
		}
		
		return result;
	}
}
