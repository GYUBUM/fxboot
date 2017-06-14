package com.flexink.sample.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flexink.common.domain.BaseService;
import com.flexink.domain.code.CommonCode;
import com.flexink.domain.code.CommonCodeId;
import com.flexink.domain.code.QCommonCode;
import com.flexink.domain.code.repository.CommonCodeRepository;
import com.flexink.vo.ParamsVo;
import com.querydsl.core.BooleanBuilder;

@Service
public class PagingSampleService extends BaseService<CommonCode, CommonCodeId>{

	@Autowired
	public PagingSampleService(CommonCodeRepository repository) {
		super(CommonCode.class, repository);
	}
	
	QCommonCode qCommonCode = QCommonCode.commonCode;
	
	public Page<CommonCode> get(ParamsVo vo) {
		
		String filter = vo.getString("filter");
		
		BooleanBuilder builder = new BooleanBuilder();
		if(StringUtils.isNotBlank(filter)) {
			builder.and(qCommonCode.groupNm.startsWithIgnoreCase(filter));
		}
		
		Page<CommonCode> list = (Page<CommonCode>) readPage(query().from(qCommonCode).where(builder), vo.getPageable());
		System.out.println(list.getContent());
		return list;
	}

}
