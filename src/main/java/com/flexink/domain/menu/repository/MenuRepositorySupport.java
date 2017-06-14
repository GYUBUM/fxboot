package com.flexink.domain.menu.repository;

import java.util.List;

import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.menu.Menu;
import com.flexink.domain.menu.QMenu;
import com.querydsl.core.types.Predicate;

@Repository
public class MenuRepositorySupport extends BaseQueryDslRepository<Menu, Long> {

	@Autowired
	public MenuRepositorySupport(JpaQueryDslRepository<Menu, Long> repository) {
		super(Menu.class, repository);
	}
	
	QMenu qMenu = QMenu.menu;
	
	public List<Menu> getMenus(Predicate predicate) {
		return query().from(qMenu).where(predicate).orderBy(qMenu.level.asc(), qMenu.sort.asc()).setHint(QueryHints.HINT_CACHEABLE, true).fetch();
	}

	public List<Menu> getTagLibMenus(Menu menu) {
		QMenu sMenu = new QMenu("sMenu");
		List<Menu> list = query().distinct().from(qMenu)
				.leftJoin(qMenu.children, sMenu)
				.fetchJoin()
				.where(qMenu.menuGrpCd.eq(menu.getMenuGrpCd())
				.and(qMenu.useYn.eq(Menu.UseYn.Y)
				.and(qMenu.level.eq(0))))
				.orderBy(qMenu.level.asc(), qMenu.sort.asc(), sMenu.level.asc(), sMenu.sort.asc())
				.fetch();
		return list;
	}
}
