/*package com.flexink.domain.sec;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
//@Entity
@Table(name = "USER2")
public class LoginUserDetails implements UserDetails, Serializable{

	private static final long serialVersionUID = -8507239908294868883L;
	
	public LoginUserDetails() {
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
	}

	@Size(min=2, max=20)
	@NotNull
	@Id
	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	@Size(min=4)
	@NotNull
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@Transient
	private String retypePassword;
	
	@Email
	@Column(name = "EMAIL", nullable = true)
	private String email;

	@Column(name = "ACCOUNTNONEXPIRED")
	private boolean accountNonExpired;		// 계정 만료 여부 (true:만료되지 않음, false: 만료)

	@Column(name = "ACCOUNTNONLOCKED")
	private boolean accountNonLocked;		// 계정 잠김 여부 (true:잠기지 않음, false: 잠김)

	@Column(name = "CREDENTIALSNONEXPIRED")
	private boolean credentialsNonExpired;	// 패스워드 만료 여부 (true:만료되지 않음, false: 만료)

	@Column(name = "ENABLED")
	private boolean enabled;				// 계정 사용 여부 (true:사용가능, false:사용불가능)


	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="USERNAME")
	private Collection<LoginUserGrantedAuthority> authorities;

	public void addAuthorities(LoginUserGrantedAuthority authority) {
		if(this.authorities == null) {
			this.authorities = new ArrayList<LoginUserGrantedAuthority>();
		}
		authorities.add(authority);
	}

}
*/