package com.flexink.domain.board.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.flexink.common.code.FxBootType;
import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.domain.board.Board;
import com.flexink.domain.board.QBoard;
import com.flexink.domain.board.QBoardType;
import com.flexink.domain.board.QComment;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;

@Repository
public class BoardRepositorySupport extends BaseQueryDslRepository<Board, Long> {

	@Autowired
	public BoardRepositorySupport(BoardRepository repository) {
		super(Board.class, repository);
	}
	
	QBoard qBoard = QBoard.board;
	QComment qComment = QComment.comment;
	QBoardType qBoardType = QBoardType.boardType;
	
	public Board search(Long id) {
		return from(qBoard).where(qBoard.id.eq(id)).fetchOne();
	}
	
	public Page<Map<String, Object>> getBoardPage(Predicate predicate, Pageable pageable) {
		Page<Map<String, Object>> mapList = readPageToMap(
				query().select(Projections.map(qBoard, qComment.id.count().as("commentCount")))
				.from(qBoard).leftJoin(qBoard.comments, qComment)
				.groupBy(qBoard.id)
				.where(predicate)
				.orderBy(qBoard.id.desc())
				, pageable);
		return mapList;
	}
	
	public Board getArticle(Board board) {
		return getArticle(board.getId());
	}
	
	public Board getArticle(Long id) {
		/********************************************************************************************
		 * 	### 단건 조회 ###
		 *	@ SpringData JPA
		 *	Board article = repository.findOne(board.getId());
		 ********************************************************************************************/
		// QueryDsl
		Board article = query().from(qBoard).join(qBoard.type, qBoardType).fetchJoin().where(qBoard.id.eq(id)).fetchOne();
		return article;
	}
	
	public void delete(Long boardId) {
		/********************************************************************************************
		 * 	### Entity 삭제 ###
		 * 	@ EntityManager 직접 이용
		 *	getEntityManager().remove(board);
		 *
		 *	@ SpringData JPA
		 *	repository.delete(board.getId());
		 ********************************************************************************************/
		// QueryDsl
		//delete(qBoard).where(qBoard.id.eq(boardId)).execute();
		update(qBoard).set(qBoard.delYn, FxBootType.Deleted.Y).where(qBoard.id.eq(boardId)).execute();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/********************************************************************************************
	 *  ### 페이징 처리 ###
	 *	@ SpringData JPA
	 *	Page<Board> list = repository.findAll(builder, params.getPageable());	
	 ********************************************************************************************/
	// QueryDsl	(Page<Entity>)
	//Page<Board> list = (Page<Board>) readPage(query().from(qBoard).where(builder).orderBy(qBoard.id.desc()), params.getPageable());

	// Page<Map>
	/*Page<Map<String, Object>> mapList = readPageToMap(
			query().select(Projections.map(qBoard, qComment.id.count().as("commentCount")))
			.from(qBoard).leftJoin(qBoard.comments, qComment)
			.groupBy(qBoard.id)
			.where(builder.and(qBoard.delYn.eq(FxBootType.Deleted.N)))
			.orderBy(qBoard.id.desc())
			, params.getPageable());*/
	
	// Page<vo>
	//Page<BoardVo> voList = (Page<BoardVo>) readPage(query().select(Projections.bean(BoardVo.class, qBoard.title, qBoard.content, qBoard.comments.size().as("commentCount"))).from(qBoard).where(builder).orderBy(qBoard.id.desc()),  params.getPageable());
	//log.debug("Page<BoardVo> : " + voList.getContent());
	
	// List
	//List<Tuple> tuple = query().select(qBoard, qBoard.comments.size().as("size")).from(qBoard).where(builder).orderBy(qBoard.id.desc()).fetch();
	//List<Map<Expression<?>, ?>> expList = query().select(Projections.map(qBoard.title, qBoard.content, qBoard.comments.size().as("count"), qBoard.viewCount)).from(qBoard).where(builder).orderBy(qBoard.id.desc()).fetch();
	//List<BoardVo> l = query().select(Projections.bean(BoardVo.class, qBoard.title, qBoard.content, qBoard.comments.size().as("count"))).from(qBoard).where(builder).orderBy(qBoard.id.desc()).fetch();

}
