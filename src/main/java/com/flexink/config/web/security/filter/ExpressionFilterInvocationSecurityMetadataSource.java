/*package com.flexink.config.web.security.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.flexink.config.web.security.service.AuthoritiesCacheManager;
import com.flexink.config.web.security.service.ResourceMetaService;
import com.flexink.domain.sec.AuthoritiesDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExpressionFilterInvocationSecurityMetadataSource implements InitializingBean{

	@Autowired
	private ResourceMetaService resourceMetaService;
	
	@Autowired
	private AuthoritiesCacheManager authoritiesCacheManager;

	
	public ExpressionBasedFilterInvocationSecurityMetadataSource getRequestMap() {
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
	      
		//List<AuthoritiesDto> authorities = resourceRepository.findAllAuthorities(); 
		
		//Map<String, List<AuthoritiesDto>> authMap = aaa(authorities);
		Map<String, List<AuthoritiesDto>> authMap = authoritiesCacheManager.getAuthorities();
		
		Iterator<String> keys = authMap.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			List<AuthoritiesDto> authList = authMap.get(key);
			String roles = "";
			for(int i=0; i < authList.size(); i++) {
				roles = i != 0 ? ", " : "" + authList.get(i).getRoleName();
			}
			AntPathRequestMatcher ant = new AntPathRequestMatcher(key);
			List<ConfigAttribute> configAttributes = SecurityConfig.createList("hasRole('" + roles + "')");
			requestMap.put(ant, configAttributes);
		}
		
		log.debug("EXPRESSION {}", requestMap);
		
		return new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap, new DefaultWebSecurityExpressionHandler());
	}
	
	private Map<String, List<AuthoritiesDto>> aaa(List<AuthoritiesDto> authorities) {
		Set<String> urls = new HashSet<String>();
		for(AuthoritiesDto dto : authorities) {
			urls.add(dto.getUrl());
		}

		Map<String, List<AuthoritiesDto>> authMap = new HashMap<>();
		List<AuthoritiesDto> subList;
		for(String url : urls) {
			subList = new ArrayList<>();
			for(AuthoritiesDto dto : authorities) {
				if(dto.getUrl().equals(url)) {
					subList.add(dto);
				}
			}
			authMap.put(url, subList);
		}
		return authMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		resourceMetaService.findAllResources();
	}
}
*/