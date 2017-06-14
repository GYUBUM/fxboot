package com.flexink.common.scheduled;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.flexink.common.utils.PhaseUtils;
import com.flexink.common.utils.PropertyUtils;
import com.flexink.security.filter.FilterMetadataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@PropertySource("classpath:project.properties")
public class ScheduledTasks {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	
	@Value("${scheduled.a}")
	String scheduledA;
	
	@Value("${scheduled.b}")
	String scheduledB;
	
	@Scheduled(cron="${scheduled.a}")
	public void reportCurrentTimeA(){
		if(PhaseUtils.isProduction()) {
			log.debug("Production Phase");
		} else if(PhaseUtils.isLocal()) {
			log.debug("Local Phase");
		}
		log.debug("[cron={}]The time is now {}", scheduledA, DATE_FORMAT.format(new Date()));
	}
	
	@Scheduled(fixedDelayString="${scheduled.b}")
	public void reportCurrentTimeB(){
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("[fixedRate={}]The time is now {}", scheduledB, DATE_FORMAT.format(new Date()));
	}
	
	
	/********************************************************************
	 * @메소드명	: deleteTempDir
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Temp Directory 데이터 삭제
	 ********************************************************************/
	@Scheduled(cron="0 0 4 * * *")
	public void deleteTempDir() {
		log.debug("Run Delete Temp Directory");
		System.gc();
		String tempPath = PropertyUtils.getSavePathProperty("upload.tmpDir") + "/";
		File tmpDir = FileUtils.getFile(tempPath);
		try {
			FileUtils.deleteDirectory(tmpDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

/************************************************************************************
 * Cron 표기법
 * **********************************************************************************
 * 초 0-59 , - * /																	*
 * 분 0-59 , - * /																	*
 * 시 0-23 , - * /																	*
 * 일 1-31 , - * ? / L W																*
 * 월 1-12 or JAN-DEC , - * /														*
 * 요일 1-7 or SUN-SAT , - * ? / L #													*
 * 년(옵션) 1970-2099 , - * /															*
 * 																					*
 * * : 모든 값																			*
 * ? : 특정 값 없음																		*
 * - : 범위 지정에 사용																	*
 * , : 여러 값 지정 구분에 사용																*
 * / : 초기값과 증가치 설정에 사용															*
 * L : 지정할 수 있는 범위의 마지막 값														*
 * W : 월~금요일 또는 가장 가까운 월/금요일													*
 * # : 몇 번째 무슨 요일 2#1 => 첫 번째 월요일												*
 * 																					*
 * 초분시일월주(년)																		*
 *  "0 0 12 * * ?" : 아무 요일, 매월, 매일 12:00:00										*
 *  "0 15 10 ? * *" : 모든 요일, 매월, 아무 날이나 10:15:00									*
 *  "0 15 10 * * ?" : 아무 요일, 매월, 매일 10:15:00										*
 *  "0 15 10 * * ? *" : 모든 연도, 아무 요일, 매월, 매일 10:15								*
 *  "0 15 10 * * ? : 2005" 2005년 아무 요일이나 매월, 매일 10:15							*
 *  "0 * 14 * * ?" : 아무 요일, 매월, 매일, 14시 매분 0초									*
 *  "0 0/5 14 * * ?" : 아무 요일, 매월, 매일, 14시 매 5분마다 0초								*
 *  "0 0/5 14,18 * * ?" : 아무 요일, 매월, 매일, 14시, 18시 매 5분마다 0초						*
 *  "0 0-5 14 * * ?" : 아무 요일, 매월, 매일, 14:00 부터 매 14:05까지 매 분 0초					*
 *  "0 10,44 14 ? 3 WED" : 3월의 매 주 수요일, 아무 날짜나 14:10:00, 14:44:00				*
 *  "0 15 10 ? * MON-FRI" : 월~금, 매월, 아무 날이나 10:15:00								*
 *  "0 15 10 15 * ?" : 아무 요일, 매월 15일 10:15:00										*
 *  "0 15 10 L * ?" : 아무 요일, 매월 마지막 날 10:15:00									*
 *  "0 15 10 ? * 6L" : 매월 마지막 금요일 아무 날이나 10:15:00								*
 *  "0 15 10 ? * 6L 2002-2005" : 2002년부터 2005년까지 매월 마지막 금요일 아무 날이나 10:15:00	*
 *  "0 15 10 ? * 6#3" : 매월 3번째 금요일 아무 날이나 10:15:00								*
 ************************************************************************************/
