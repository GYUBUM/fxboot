package com.flexink.domain.board.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.flexink.common.code.FxBootType;
import com.flexink.common.domain.BaseQueryDslRepository;
import com.flexink.domain.board.Board;
import com.flexink.domain.board.Comment;
import com.flexink.domain.board.QBoard;
import com.flexink.domain.board.QComment;

@Repository
public class CommentRepositorySupport extends BaseQueryDslRepository<Comment, Long> {

	@Autowired
	public CommentRepositorySupport(CommentRepository repository) {
		super(Comment.class, repository);
	}
	
	QBoard qBoard = QBoard.board;
	QComment qComment = QComment.comment;
	
	public List<Comment> getComments(Board board) {
		List<Comment> comments = query().from(qComment).where(qComment.board.eq(board).and(qComment.delYn.eq(FxBootType.Deleted.N))).fetch();
		return comments;
	}
	
	
}
