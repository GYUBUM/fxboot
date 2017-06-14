package com.flexink.domain.code.repository;



import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.code.CommonCode;
import com.flexink.domain.code.CommonCodeId;
import com.flexink.domain.code.QCommonCode;
import com.querydsl.core.types.Predicate;

@Repository
public class CommonCodeRepositorySupport extends BaseQueryDslRepository<CommonCode, CommonCodeId> {

	@Autowired
	public CommonCodeRepositorySupport(JpaQueryDslRepository<CommonCode, CommonCodeId> repository) {
		super(CommonCode.class, repository);
	}
	
	private QCommonCode qCommonCode = QCommonCode.commonCode;
	
	public Page<CommonCode> getCommonCodePage(Predicate predicate, Pageable pageable) {
		// NATIVE QUERY
        //List<CommonCode> list = getEntityManager().createNativeQuery("select * from common_code_m where group_cd = 'user'", CommonCode.class).getResultList();
        
        // JPQL
        //List<CommonCode> list2 = getEntityManager().createQuery("SELECT C FROM COMMON_CODE_M C WHERE C.GROUP_CD = 'USER'", CommonCode.class).getResultList();
        
        // Criteria
        /*CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CommonCode> cQuery = cb.createQuery(CommonCode.class);
        Root<CommonCode> c = cQuery.from(CommonCode.class);
        CriteriaQuery<CommonCode> cQuery2 = cQuery.select(c).where(cb.equal(c.get("group_cd"), "user"));
        List<CommonCode> list3 = cQuery2.createQuery(cQuery2).getResualtList();*/

        // Spring Data Jpa
        //List<CommonCode> list4 = repository.findByGroupCdEquals("user");
        
        // QueryDSL
        //List<CommonCode> list5 = query().from(qCommonCode).where(qCommonCode.groupCd.eq("user")).fetch();
        
        // Mybatis
        //List<EgovMap> list6 = commonCodeMapper.readPage(paramsVo);
        
        //List<Tuple> result = query().select(qCommonCode.data1, qCommonCode.data2).from(qCommonCode).fetch();
		Page<CommonCode> list = readPage(query().from(qCommonCode).where(predicate).setHint(QueryHints.HINT_CACHEABLE, true), pageable);
		return list;
	}
	
}
