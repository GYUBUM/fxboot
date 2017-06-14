package com.flexink.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.flexink.security.domain.Authorities;
import com.flexink.security.domain.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper=true)
public class SessionUserDetail extends org.springframework.security.core.userdetails.User {

	private String email;
	
	public SessionUserDetail(User user) {
		super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
				user.isCredentialsNonExpired(), user.isAccountNonLocked(), convertSimpleGrantedAuthorities(user.getUserRoles()));
		this.email = user.getEmail();
	}


	
	public static List<SimpleGrantedAuthority> convertSimpleGrantedAuthorities(List<Authorities> authList) {
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthority simpleGrantedAuthority;
		List<String> userRoles = new ArrayList<>();
		
		for(Authorities auth : authList) {
			userRoles.add(auth.getRole().getRoleName());
			simpleGrantedAuthority = new SimpleGrantedAuthority(auth.getRole().getRoleName());
			simpleGrantedAuthorities.add(simpleGrantedAuthority);
		}
		
		return simpleGrantedAuthorities;
	}
}