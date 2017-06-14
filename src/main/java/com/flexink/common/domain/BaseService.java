package com.flexink.common.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.common.code.FxBootType;
import com.flexink.common.utils.ExpressionToMap;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;


public class BaseService<T, ID extends Serializable> extends QueryDslRepositorySupport{

	protected JpaQueryDslRepository<T, ID> repository;
	
	public BaseService(Class<T> clazz) {
		super(clazz);
	}

	public BaseService(Class<T> clazz, JpaQueryDslRepository<T, ID> repository) {
		super(clazz);
		this.repository = repository;
	}
	
	/********************************************************************
	 * @메소드명	: query
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: JPAQuery 객체 반환
	 * @return
	 * JPAQuery<T>
	 ********************************************************************/
	public JPAQuery<T> query() {
		JPAQuery<T> query = new JPAQuery<T>(getEntityManager());
		return query;
	}
	
	public void insert(Object entity) {
		getEntityManager().persist(entity);
	}
	
	/********************************************************************
	 * @메소드명	: save
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 그리드용 CUD
	 * @param var
	 * @return
	 * S
	 ********************************************************************/
	@Transactional
    public <S extends T> S save(S var) {
        boolean deleted = false;

        if (var instanceof FxBootCrudModel) {
        	FxBootCrudModel crudModel = (FxBootCrudModel) var;

            if (crudModel.getDataStatus() == FxBootType.DataStatus.DELETED) {
                deleted = true;
            }
        }

        if (deleted) {
            repository.delete(var);
        } else {
            repository.save(var);
        }

        return var;
    }

    @Transactional
    public <S extends T> Collection<S> save(Collection<S> vars) {
    	for(S var : vars) {
    		this.save(var);
    	}
        return vars;
    }
    
	/********************************************************************
	 * @메소드명	: readPage
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: QueryDSL 페이징 처리용
	 * @param query
	 * @param pageable
	 * @return
	 * Page<T>
	 ********************************************************************/
	protected Page<T> readPage(JPAQuery<T> query, Pageable pageable) {
		if (pageable == null) {
			return readPage(query, new QPageRequest(0, Integer.MAX_VALUE));
		}
        long total = query.clone(super.getEntityManager()).fetchCount();
		JPQLQuery<T> pagedQuery = getQuerydsl().applyPagination(pageable, query);
		List<T> content = total > pageable.getOffset() ? pagedQuery.fetch() : Collections.<T>emptyList();
		return new PageImpl<>(content, pageable, total);
	}
	
    protected Page<?> readPageToBean(JPAQuery<?> query, Pageable pageable) {
		if (pageable == null) {
			return readPageToBean(query, new QPageRequest(0, Integer.MAX_VALUE));
		}
        long total = query.clone(super.getEntityManager()).fetchCount();
		JPQLQuery<?> pagedQuery = getQuerydsl().applyPagination(pageable, query);
		List<?> content = total > pageable.getOffset() ? pagedQuery.fetch() : Collections.emptyList();
		return new PageImpl<>(content, pageable, total);
	} 
    
	protected Page<Map<String, Object>> readPageToMap(JPAQuery<Map<Expression<?>, ?>> jpaQuery, Pageable pageable) {
		if (pageable == null) {
			return readPageToMap(jpaQuery, new QPageRequest(0, Integer.MAX_VALUE));
		}
        long total = jpaQuery.clone(super.getEntityManager()).fetchCount();
		JPQLQuery<Map<Expression<?>, ?>> pagedQuery = getQuerydsl().applyPagination(pageable, jpaQuery);
		List<Map<Expression<?>, ?>> content = (List<Map<Expression<?>, ?>>) (total > pageable.getOffset() ? pagedQuery.fetch() : Collections.emptyList());
		return new PageImpl<>(ExpressionToMap.convert(content), pageable, total);
	}
	
	
	
	
		
}
