package com.flexink.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.security.domain.RoleResource;

public interface RoleResourceRepository extends JpaRepository<RoleResource, Long> {

}
