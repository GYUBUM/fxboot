package com.flexink.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationListener;

import com.flexink.security.CacheEventMessage;
import com.flexink.security.dto.AuthoritiesDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthoritiesCacheManager implements ApplicationListener<CacheEventMessage> {
	private Map<String, List<AuthoritiesDto>> authorities;

	public Map<String, List<AuthoritiesDto>> getAuthorities() {
		return authorities;
	}

	public List<AuthoritiesDto> getAuthoritie(String key) {
		return authorities.get(key);
	}

	@Override
	public void onApplicationEvent(CacheEventMessage event) {
		//authorities = event.getAuthoritiesDto().stream().collect(groupingBy(AuthoritiesDto::getUrl, toList()));

		List<AuthoritiesDto> authList = event.getAuthoritiesDto();
		
		Set<String> urls = new HashSet<String>();
		for(AuthoritiesDto dto : authList) {
			urls.add(dto.getUrl());
		}

		Map<String, List<AuthoritiesDto>> authMap = new HashMap<>();
		List<AuthoritiesDto> subList;
		for(String url : urls) {
			subList = new ArrayList<>();
			for(AuthoritiesDto dto : authList) {
				if(dto.getUrl().equals(url)) {
					subList.add(dto);
				}
			}
			authMap.put(url, subList);
		}
		
		log.debug("###############################");
		log.debug("Cache 됨 : {}", authMap);
		log.debug("###############################");
		
		authorities = authMap;
		
		// grouping
		// url => roles

		// String url;
		// for (UserRoleDto userRoleDto : event.getUserRoleDto()) {
		// url = userRoleDto.getUrl();
		// if (this.urlRoles.containsKey(url)) {
		// List<String> roles = this.urlRoles.get(url);
		// roles.add(userRoleDto.getRoleName());
		//
		// } else {
		// List<String> roles = new ArrayList<>();
		// roles.add(userRoleDto.getRoleName());
		// this.urlRoles.put(url, roles);
		// }
		// }
	}
}