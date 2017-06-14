package com.flexink.config.web.security.service;
/*package com.flexink.config.web.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flexink.domain.sec.LoginUserDetails;
import com.flexink.domain.sec.LoginUserRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	MessageSourceAccessor messageSource;

	@Autowired
	LoginUserRepository userRepository;

	@Override
	public LoginUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// Security Locale 적용안됨.. 추후 수정 예정 (기본값 ko_KR)
		
		LoginUserDetails userDetails = userRepository.findByUsername(username);//throw exception;
		if(userDetails == null) {
			throw new UsernameNotFoundException(messageSource.getMessage("DigestAuthenticationFilter.usernameNotFound", new Object[]{username}));
		}
		return userDetails;
	}
}
*/