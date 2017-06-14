package com.flexink.sample.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.flexink.domain.board.BoardType;
import com.flexink.domain.board.QBoardType;
import com.flexink.domain.board.repository.BoardTypeRepositorySupport;
import com.flexink.vo.ParamsVo;
import com.querydsl.core.BooleanBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardTypeService {
	
	@Autowired
	BoardTypeRepositorySupport boardTypeRepositorySupport;
	
	QBoardType qBoardType = QBoardType.boardType;
	
	public BoardType getBoardType(BoardType boardType) {
		return boardTypeRepositorySupport.getRepository().findOne(boardType.getId());
				
	}
	
	public Page<BoardType> getBoardTypes(ParamsVo params) {
		BooleanBuilder builder = new BooleanBuilder();
		String filter = params.getString("filter");
		if(StringUtils.isNotBlank(filter)) {
			builder.and(qBoardType.type.eq(filter));
		}
		return boardTypeRepositorySupport.getBoardTypes(builder, params.getPageable());
	}
	
	public void saveBoardType(List<BoardType> boardTypes) {
		boardTypeRepositorySupport.save(boardTypes);
	}
	
	
	
}
