package com.flexink.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyUtils {
	
	final static  String FILE_SEPARATOR = System.getProperty("file.separator");
	private static String targetPath = "/target/classes/";
	public static final String RELATIVE_PATH_PREFIX = PropertyUtils.class.getResource("").getPath().substring(0, PropertyUtils.class.getResource("").getPath().lastIndexOf(targetPath)+targetPath.length());
	public static final String GLOBALS_PROPERTIES_FILE = RELATIVE_PATH_PREFIX + FILE_SEPARATOR + "project.properties";
	
	/********************************************************************
	 * @메소드명	: getProperty
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: project.properties 파일에서 값 조회
	 ********************************************************************/
	public static String getProperty(String keyName) {
		String value = "";
		log.debug("getProperty : {} = {}", GLOBALS_PROPERTIES_FILE, keyName);
		
		try {
			Properties props = new Properties();
			
			@Cleanup FileInputStream fis = new FileInputStream(GLOBALS_PROPERTIES_FILE);
			
			props.load(new BufferedInputStream(fis));
			if (props.getProperty(keyName) == null) {
				return "";
			}
			value = props.getProperty(keyName).trim();
		} catch (FileNotFoundException fne) {
			log.debug("Property file not found.", fne);
			throw new RuntimeException("Property file not found", fne);
		} catch (IOException ioe) {
			log.debug("Property file IO exception", ioe);
			throw new RuntimeException("Property file IO exception", ioe);
		}
		
		return value;
	}
	
	/********************************************************************
	 * @메소드명	: getSavePathProperty
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: parameter가 실제 Path가 아니면 properties 파일에서 조회
	 ********************************************************************/
	public static String getSavePathProperty(String keyName) {
		if(keyName.contains("/") || keyName.contains("\\")) {
			return keyName;
		} else {
			return getProperty(keyName);
		}
	}
}
