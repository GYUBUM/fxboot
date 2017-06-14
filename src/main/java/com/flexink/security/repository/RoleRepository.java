package com.flexink.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexink.security.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findByRoleName(String roleName);
}
