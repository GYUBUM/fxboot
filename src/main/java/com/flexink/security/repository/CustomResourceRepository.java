package com.flexink.security.repository;

import java.util.List;

import com.flexink.security.dto.AuthoritiesDto;

public interface CustomResourceRepository {

	List<AuthoritiesDto> findAllAuthorities();
	
}
