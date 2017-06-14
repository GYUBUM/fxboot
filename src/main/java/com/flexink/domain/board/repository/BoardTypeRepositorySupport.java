package com.flexink.domain.board.repository;

import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.domain.board.BoardType;
import com.flexink.domain.board.QBoardType;
import com.querydsl.core.types.Predicate;

@Repository
public class BoardTypeRepositorySupport extends BaseQueryDslRepository<BoardType, String> {

	@Autowired
	public BoardTypeRepositorySupport(BoardTypeRepository repository) {
		super(BoardType.class, repository);
	}
	
	QBoardType qBoardType = QBoardType.boardType;
	
	public Page<BoardType> getBoardTypes(Predicate predicate, Pageable pageable) {
		return (Page<BoardType>) readPage(query().from(qBoardType).where(predicate).setHint(QueryHints.HINT_CACHEABLE, true), pageable);
	}
	

}
