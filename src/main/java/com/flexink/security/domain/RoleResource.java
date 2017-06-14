package com.flexink.security.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"role", "resources"})
@Entity
@Table(name="T_ROLE_RESOURCE")
public class RoleResource {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ROLE_RESOURCE_ID", unique = true, nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCES_ID")
	private Resources resources;
	
	
	
}
