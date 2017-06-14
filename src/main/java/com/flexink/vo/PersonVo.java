package com.flexink.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class PersonVo {
	
	// JSR-303, JSR-380 참조
	
	@NotEmpty(message="필수 입력사항입니다.")
	@Size(min = 2, max = 30, message="최소 {min} ~ {max} ...")
	private String name;

	@NotNull
	@Min(18)
	private Integer age;
	
	@Email
	private String email;
	
	@Range(min=10, max=50)
	private int range;

	
}
