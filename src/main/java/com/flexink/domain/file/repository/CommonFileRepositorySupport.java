package com.flexink.domain.file.repository;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.domain.file.CommonFile;
import com.flexink.domain.file.QCommonFile;

@Repository
public class CommonFileRepositorySupport extends BaseQueryDslRepository<CommonFile, Long> {

	@Autowired
	public CommonFileRepositorySupport(CommonFileRepository repository) {
		super(CommonFile.class, repository);
	}
	
	QCommonFile qCommonFile = QCommonFile.commonFile;
	
	public CommonFile getCommonFile(String targetType, String targetId) {
		return from(qCommonFile).where(qCommonFile.targetType.eq(targetType).and(qCommonFile.targetId.eq(targetId))).fetchOne();
	}
	
	public List<CommonFile> getCommonFiles(String targetType, String targetId) {
		return from(qCommonFile).where(qCommonFile.targetType.eq(targetType).and(qCommonFile.targetId.eq(targetId))).fetch();
	}
	
	public void deleteByTargetTypeAndTargetIds(String targetType, Set<String> targetIds) {
    	delete(qCommonFile).where(qCommonFile.targetType.eq(targetType).and(qCommonFile.targetId.in(targetIds))).execute();
    }
	
	public void deleteByTargetTypeAndTargetId(String targetType, String targetId) {
        delete(qCommonFile).where(qCommonFile.targetType.eq(targetType).and(qCommonFile.targetId.eq(targetId))).execute();
    }
	
	public void updateCommonFile(CommonFile file) {
		update(qCommonFile).set(qCommonFile.targetType, file.getTargetType()).set(qCommonFile.targetId, file.getTargetId()).where(qCommonFile.id.eq(file.getId())).execute();
	}
	
}
