package com.flexink.sample.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.common.code.FxBootType.Used;
import com.flexink.domain.code.CommonCode;
import com.flexink.domain.code.QCommonCode;
import com.flexink.domain.code.repository.CommonCodeRepositorySupport;
import com.flexink.sample.mapper.CommonCodeMapper;
import com.flexink.vo.ParamsVo;
import com.querydsl.core.BooleanBuilder;

@EnableCaching
@Service
public class CommonCodeService {

    private QCommonCode qCommonCode = QCommonCode.commonCode;
    
    @Autowired
    private CommonCodeMapper commonCodeMapper;
    
    @Autowired
    private CommonCodeRepositorySupport commonCodeRepositorySupport;

    
    @Cacheable(cacheNames="commonCode", key="#commonCode.groupCd")
    public List<CommonCode> getList(CommonCode commonCode) {
    	ParamsVo param = new ParamsVo();
    	param.put("groupCd", commonCode.getGroupCd());
    	return get(param).getContent();
    }
    
    public Page<CommonCode> get(ParamsVo paramsVo) {
    	
    	paramsVo.addSort("groupCd", Sort.Direction.ASC);
        paramsVo.addSort("sort", Sort.Direction.ASC);

        String filter = paramsVo.getString("filter");
        
        BooleanBuilder builder = new BooleanBuilder();
        
        if (StringUtils.isNotEmpty(filter)) {
            builder.and(qCommonCode.groupCd.like("%"+filter+"%")
            		.or(qCommonCode.groupNm.like("%"+filter+"%"))
            		.or(qCommonCode.code.like("%"+filter+"%"))
            		.or(qCommonCode.name.like("%"+filter+"%")));
        }
        if (StringUtils.isNotEmpty(paramsVo.getString("groupCd"))) {
        	builder.and(qCommonCode.groupCd.eq(paramsVo.getString("groupCd")));
        }
        if (paramsVo.get("useYn") != null) {
        	builder.and(qCommonCode.useYn.eq((Used) paramsVo.get("useYn")));
        }
        
        return commonCodeRepositorySupport.getCommonCodePage(builder, paramsVo.getPageable());
    }
    
    @Transactional
    @CacheEvict(cacheNames="commonCode", allEntries=true)
    public void saveCommonCode(List<CommonCode> basicCodes) {
    	commonCodeRepositorySupport.save(basicCodes);
    }
}
