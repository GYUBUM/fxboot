package com.flexink.security.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"authorities", "roleResources"})
@Entity
@Table(name="T_ROLE")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ROLE_ID", unique = true, nullable = false)
	private Long id;
	
	@Column(name="ROLE_NAME", length=100)
	private String roleName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<Authorities> authorities = new HashSet<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<RoleResource> roleResources = new HashSet<>();
}
