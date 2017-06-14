package com.flexink.security.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.flexink.security.dto.AuthoritiesDto;
import com.flexink.security.service.AuthoritiesCacheManager;
import com.flexink.security.service.ResourceMetaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
	
	@Autowired
	private ResourceMetaService resourceMetaService;

	@Autowired
	private AuthoritiesCacheManager authoritiesCacheManager;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		/*FilterInvocation fi = (FilterInvocation) object;
	    String url = fi.getRequestUrl();

	    log.debug("###########################");
	    log.debug("Filter Access URL : {}", url);
	    
	    List<AuthoritiesDto> userRoleDto = authoritiesCacheManager.getAuthorities().get(url);
	    if (userRoleDto == null) {
	      return null;
	    }
	    
	    List<String> roles = new ArrayList<String>();
	    for(AuthoritiesDto dto : userRoleDto) {
	    	roles.add(dto.getRoleName());
	    }
	    
	    log.debug("Roles : {}", roles);

	    String[] roleArr = new String[roles.size()];
	    roleArr = roles.toArray(roleArr);
	    

	    log.debug("###########################");
	    
	    return SecurityConfig.createList(roleArr);*/
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//resourceMetaService.findAllResources();
	}

}
