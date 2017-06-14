package com.flexink.domain.menu.repository;

import org.springframework.stereotype.Repository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.menu.Menu;

@Repository
public interface MenuRepository extends JpaQueryDslRepository<Menu, Long> {

}
