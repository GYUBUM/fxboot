package com.flexink;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.flexink.security.filter.FilterMetadataSource;
import com.flexink.security.service.AuthoritiesCacheManager;
import com.flexink.security.service.ResourceMetaService;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching
@EnableJpaRepositories
public class FXBootApplication {
	
	/********************************************************************
	 * @메소드명	: filterMetadataSource
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Role Authorities 정보
	 ********************************************************************/
	/*@Bean
	public FilterMetadataSource filterMetadataSource() {
		return new FilterMetadataSource();
	}*/

	/********************************************************************
	 * @메소드명	: authoritiesCacheManager
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Authorities Cache 적용
	 ********************************************************************/
	@Bean
	public AuthoritiesCacheManager authoritiesCacheManager() {
		return new AuthoritiesCacheManager();
	}

	/********************************************************************
	 * @메소드명	: resourceMetaService
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: security Role, URL 관련 서비스
	 ********************************************************************/
	@Bean
	public ResourceMetaService resourceMetaService() {
		return new ResourceMetaService();
	}
}
