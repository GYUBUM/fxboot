package com.flexink.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.security.SessionUserDetail;
import com.flexink.security.domain.User;
import com.flexink.security.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	MessageSourceAccessor messageSource;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByusername(username);
		if (user == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("DigestAuthenticationFilter.usernameNotFound", new Object[]{username}));
		}
		
		return new SessionUserDetail(user);
	}
	
}