package com.flexink.sample.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flexink.common.file.service.CommonFileService;
import com.flexink.common.utils.CookieUtils;
import com.flexink.config.web.security.user.UserDetailsHelper;
import com.flexink.domain.board.Board;
import com.flexink.domain.board.BoardType;
import com.flexink.domain.board.QBoard;
import com.flexink.domain.board.QBoardType;
import com.flexink.domain.board.QComment;
import com.flexink.domain.board.repository.BoardRepository;
import com.flexink.domain.board.repository.BoardRepositorySupport;
import com.flexink.domain.board.repository.BoardTypeRepositorySupport;
import com.flexink.domain.file.CommonFile;
import com.flexink.domain.file.repository.CommonFileRepositorySupport;
import com.flexink.vo.ParamsVo;
import com.querydsl.core.BooleanBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	@Autowired
	CommonFileRepositorySupport commonFileRepositorySupport;
	
	@Autowired
	BoardTypeRepositorySupport boardTypeRepositorySupport;
	
	@Autowired
	BoardRepositorySupport boardRepositorySupport;
	
	QBoard qBoard = QBoard.board;
	QComment qComment = QComment.comment;
	QBoardType qBoardType = QBoardType.boardType;
	
	/********************************************************************
	 * @메소드명	: getList
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글목록 조회
	 ********************************************************************/
	@Transactional
	public Page<Map<String, Object>> getList(ParamsVo params) {
		
		BoardType boardType = boardTypeRepositorySupport.getRepository().findOne(params.getString("type"));
		BooleanBuilder builder = new BooleanBuilder();
		if(StringUtils.isNotEmpty(params.getString("type"))) {
			builder.and(qBoard.type.eq(boardType));
		}
		
		return boardRepositorySupport.getBoardPage(builder, params.getPageable());
	}
	

	/********************************************************************
	 * @메소드명	: getArticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 상세 조회
	 ********************************************************************/
	@Transactional
	public Board viewArticle(Board board) {
		Board article = boardRepositorySupport.getArticle(board);
		List<CommonFile> files = commonFileRepositorySupport.getCommonFiles(article.getType().getType(), String.valueOf(article.getId()));
		article.setFiles(files);
		
		// view count update
		String cookieView = CookieUtils.getCookieValue("view_count");
		String newCookieView = " | " + article.getType().getType() + ":" + article.getId();
		if(StringUtils.indexOfIgnoreCase(cookieView, newCookieView) < 0) {
			article.setViewCount(article.getViewCount() + 1);
			CookieUtils.addCookie("view_count", cookieView + newCookieView);
		}
		
		return article;
	}
	
	
	/********************************************************************
	 * @메소드명	: saveArticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 저장 및 수정
	 ********************************************************************/
	@Transactional
	public void saveArticle(Board board) {
		if(board.getId() == null) {
			/********************************************************************************************
			 * 	### Entity 저장 ###
			 * 	@ EntityManager 직접 이용
			 *	getEntityManager().persist(board);
			 *
			 *	@ SpringData JPA
			 *	repository.save(board);
			 *
			 * 	@ QueryDsl
			 * 	Insert 메소드 없음
			 ********************************************************************************************/
			boardRepositorySupport.insert(board);
		} else {
			/********************************************************************************************
			 * 	#### Entity 수정 ###
			 *	@ SpringData JPA
			 *	repository.save(board);
			 *
			 *  @ EntityManager 직접 이용
			 *	getEntityManager().merge(board)  
			 ********************************************************************************************/
			Board article = boardRepositorySupport.getArticle(board);
			if(article.getCreatedBy().equals(UserDetailsHelper.getLoginUserDetails().getUsername())) {
				article.setTitle(board.getTitle());
				article.setContent(board.getContent());
				article.setSecret(board.getSecret());
			}
		}
	}
	
	/********************************************************************
	 * @메소드명	: deleteArticle
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 글 삭제
	 ********************************************************************/
	@Transactional
	public void deleteArticle(Long boardId) {
		if(boardId != null) {
			Board article = boardRepositorySupport.getArticle(boardId);
			if(article.getCreatedBy().equals(UserDetailsHelper.getLoginUserDetails().getUsername()) || UserDetailsHelper.containsAuthority("ROLE_ADMIN", "ROLE_SYSTEM")) {
				boardRepositorySupport.delete(boardId);
			}
			
		}
	}
	
	

	
}
