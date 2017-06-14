package com.flexink.security.repository;

import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.flexink.security.domain.QResources;
import com.flexink.security.domain.QRole;
import com.flexink.security.domain.QRoleResource;
import com.flexink.security.domain.Resources;
import com.flexink.security.dto.AuthoritiesDto;
import com.querydsl.core.types.Projections;

public class ResourceRepositoryImpl extends QueryDslRepositorySupport implements CustomResourceRepository {

    public ResourceRepositoryImpl() {
        super(Resources.class);
    }

    @Override
    public List<AuthoritiesDto> findAllAuthorities() {
        QRole r = QRole.role;
        QRoleResource ra = QRoleResource.roleResource;
        QResources a = QResources.resources;
        List<AuthoritiesDto> list = from(r).select(Projections.constructor(AuthoritiesDto.class, a.url, r.roleName))
                .innerJoin(r.roleResources, ra)
                .where(r.eq(ra.role))
                .innerJoin(ra.resources, a)
                .where(ra.resources.eq(a)).fetch();
        return list;
    }
}