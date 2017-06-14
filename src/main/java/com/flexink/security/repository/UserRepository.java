package com.flexink.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexink.security.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByusername(String username);

}
