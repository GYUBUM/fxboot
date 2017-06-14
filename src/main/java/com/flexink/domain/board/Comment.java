package com.flexink.domain.board;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.flexink.common.code.FxBootType.Deleted;
import com.flexink.common.code.FxBootType.Secret;
import com.flexink.common.domain.BaseJpaModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="T_COMMENT")
public class Comment extends BaseJpaModel<Long> {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="COMMENT_ID")
	private Long id;
	
	@Column(name="WRITER", length=50)
	private String writer;
	
	@Column(name="PASSWORD", length=100)
	private String password;
	
	@Column(name="CONTENT", nullable=false)
	@Lob
	private String content;
	
	@Enumerated(EnumType.STRING)
	@Column(name="SECRET", length=1)
	private Secret secret;
	
	@Enumerated(EnumType.STRING)
	@Column(name="DEL_YN", length=1)
	private Deleted delYn = Deleted.N;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="BOARD_ID")
	private Board board;
	
	public void setBoard(Board board) {
		board.addComment(this);
		this.board = board;
	}
	
}
