package com.flexink.domain.code.repository;

import org.springframework.stereotype.Repository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.code.CommonCode;
import com.flexink.domain.code.CommonCodeId;

@Repository
public interface CommonCodeRepository extends JpaQueryDslRepository<CommonCode, CommonCodeId> {

}
