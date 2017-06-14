package com.flexink.security;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.flexink.security.dto.AuthoritiesDto;

public class CacheEventMessage extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3063861114711262062L;
	final List<AuthoritiesDto> authoritiesDto;

	public CacheEventMessage(Object source, final List<AuthoritiesDto> authoritiesDto) {
		super(source);
		this.authoritiesDto = authoritiesDto;
	}

	public List<AuthoritiesDto> getAuthoritiesDto() {
		return authoritiesDto;
	}

}
