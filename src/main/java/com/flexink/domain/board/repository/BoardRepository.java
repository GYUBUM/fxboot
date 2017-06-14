package com.flexink.domain.board.repository;

import org.springframework.stereotype.Repository;

import com.flexink.common.domain.JpaQueryDslRepository;
import com.flexink.domain.board.Board;

@Repository
public interface BoardRepository extends JpaQueryDslRepository<Board, Long> {

}
