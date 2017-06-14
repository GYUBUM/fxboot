package com.flexink.sample.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@EnableCaching
@Transactional
@Service
public class EhCacheSampleService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

	public String getNoCache(String name) throws Exception {
		slowQuery(5000);
		return " --- Cache 요청시간 : " + DATE_FORMAT.format(new Date());
	}

	@Cacheable(cacheNames = "SampleCache", key="#name")
	public String getCache(String name) throws Exception {
		slowQuery(5000);
		return " --- Cache 요청시간 : " + DATE_FORMAT.format(new Date());
	}

	@CacheEvict(cacheNames = "SampleCache", key="#name")
	public String cacheRefresh(String name) throws Exception {
		return name + " Cache 삭제됨.";
	}

	
	private void slowQuery(long seconds) {
		try {
			Thread.sleep(seconds);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
