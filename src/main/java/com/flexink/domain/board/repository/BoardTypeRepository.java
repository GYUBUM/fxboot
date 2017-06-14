package com.flexink.domain.board.repository;

import org.springframework.stereotype.Repository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.board.BoardType;

@Repository
public interface BoardTypeRepository extends JpaQueryDslRepository<BoardType, String> {

}
