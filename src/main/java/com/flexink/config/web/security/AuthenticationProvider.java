/*package com.flexink.config.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flexink.common.utils.PhaseUtils;
import com.flexink.config.web.security.user.LoginUserDetailsService;
import com.flexink.domain.sec.LoginUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

	@Autowired
	LoginUserDetailsService userDetailsService;
	@Autowired
	MessageSourceAccessor messageSource;

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		//detail에는 Object가 넘어온다  oauth  일경우에는 맵이 들어온다
//		WebAuthenticationDetails detail = (WebAuthenticationDetails) authentication.getDetails();
//		String remoteIP = detail.getRemoteAddress();
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		
		 
		if(PhaseUtils.isDevelopmentMode()) {
			log.debug("Input username : {}   Input password : {}", username, password);
			log.debug("Encoded password : {}", getPasswordEncoder().encode(password));
		}
		
		LoginUserDetails userDetails = userDetailsService.loadUserByUsername(username);
		//log.info("Login try ip :  ->  input id("+username+") info:"+userDetails);
		if(null == userDetails || userDetails.isAccountNonLocked()==false || userDetails.isAccountNonExpired() == false || userDetails.isEnabled() == false || userDetails.isCredentialsNonExpired() == false) {
			throw new UsernameNotFoundException(messageSource.getMessage("error.login.fail"));
		}


		if(!encoder.matches(encoder.encode(password),userDetails.getPassword())){    //실패
			throw new BadCredentialsException(messageSource.getMessage("error.login.fail"));
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
//		token.setDetails(userDetails);
		return token;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public void setPasswordEncoder(org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
		this.encoder = passwordEncoder;
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return this.encoder;
	}

}
*/