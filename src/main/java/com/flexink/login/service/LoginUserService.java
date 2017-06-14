package com.flexink.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.security.domain.Authorities;
import com.flexink.security.domain.Role;
import com.flexink.security.domain.User;
import com.flexink.security.repository.AuthoritiesRepository;
import com.flexink.security.repository.RoleRepository;
import com.flexink.security.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**************************************************************
 * @FileName	: LoginUserService.java
 * @Project		: fxBoot
 * @Package_Name: com.flexink.common.login.service
 * @Date		: 2017. 3. 23. 
 * @작성자		: KIMSEOKHOON
 * @변경이력		:
 * @프로그램 설명 	: 사용자 신규 등록
 **************************************************************/
@Slf4j
@Service
@Transactional
public class LoginUserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
	/********************************************************************
	 * @메소드명	: regUser
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 사용자 등록
	 ********************************************************************/
	public User regUser(User user) {
		// 비밀번호 bcryto 변환
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		
		// 사용자 등록
		userRepository.save(user);
		// Role 조회
		Role role = roleRepository.findByRoleName("ROLE_ADMIN");
		// Authority 참조관계 설정 및 등록
		Authorities authority = new Authorities();
		authority.setUser(user);
		authority.setRole(role);
		authoritiesRepository.save(authority);
		
		log.debug("사용자 등록됨 : {}", user);
		return user;
	}
}
