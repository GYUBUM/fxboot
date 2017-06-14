package com.flexink.domain.board;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.flexink.common.domain.BaseJpaModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Cacheable
@Entity
@Table(name="T_BOARD_TYPE")
public class BoardType extends BaseJpaModel<String> {
	
	public BoardType(String type) {
		this.type = type;
	}

	@Id
	@Column(name="TYPE", length=10)
	private String type;
	
	@Column(length=100)
	private String name;
	
	@Column(name="TYPE_DESC")
	private String desc;

	@Override
	public String getId() {
		return this.type;
	}
	
}
