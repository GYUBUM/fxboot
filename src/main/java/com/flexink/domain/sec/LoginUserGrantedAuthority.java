/*package com.flexink.domain.sec;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
//@Entity
@Table(name = "AUTHORITY", uniqueConstraints={
		@UniqueConstraint(columnNames={"USERNAME", "AUTHORITY"})
})
public class LoginUserGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 2642610728784974077L;

	@Id
	@Column(name = "AUTHORITY_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "USERNAME")
	private String username;

	@Column(name = "AUTHORITY")
	private String authority = null;

	public LoginUserGrantedAuthority() {
	}

	public LoginUserGrantedAuthority(String authority) {
		this.authority = authority;
	}
	

}
*/