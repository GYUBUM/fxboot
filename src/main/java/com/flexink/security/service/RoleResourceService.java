package com.flexink.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.security.repository.RoleResourceRepository;

public class RoleResourceService {

	@Autowired
	private RoleResourceRepository roleResourceRepository;

	@Autowired
	private ResourceMetaService resourceMetaServiceImpl;

	@Transactional
	public void delete(Long id) {
		roleResourceRepository.delete(id);
		resourceMetaServiceImpl.findAllResources();
	}
	
}