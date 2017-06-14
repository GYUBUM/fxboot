package com.flexink.domain.file.repository;

import org.springframework.stereotype.Repository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.file.CommonFile;

@Repository
public interface CommonFileRepository extends JpaQueryDslRepository<CommonFile, Long> {
	
	CommonFile findByTargetTypeAndTargetId(String targetType, String targetId);
	
}
