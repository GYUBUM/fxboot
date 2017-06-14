package com.flexink.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flexink.security.domain.Resources;

public interface ResourceRepository extends JpaRepository<Resources, Long>, CustomResourceRepository {

}
