package com.flexink.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.flexink.security.CacheEventMessage;
import com.flexink.security.dto.AuthoritiesDto;
import com.flexink.security.repository.ResourceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceMetaService {

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private ApplicationContext applicationContext;

	
	public void findAllResources() {
		
		List<AuthoritiesDto> authorities = resourceRepository.findAllAuthorities();
		
        log.debug("##########################");
        log.debug("ResourceMetaService : findAllResources() : {}", authorities);
        log.debug("##########################");

        applicationContext.publishEvent(new CacheEventMessage(this, authorities));
	}

}
