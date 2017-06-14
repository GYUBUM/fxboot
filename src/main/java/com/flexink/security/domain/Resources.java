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
@ToString(exclude = {"roleResources"})
@Entity
@Table(name = "T_RESOURCES")
public class Resources {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="RESOURCES_ID", unique = true, nullable = false)
	private Long id;

	@Column(name="URL")
	private String url;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "resources")
	private Set<RoleResource> roleResources = new HashSet<>();
}
