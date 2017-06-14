package com.flexink.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.flexink.security.domain.User;

/**************************************************************
 * @FileName	: UserValidator.java
 * @Project		: fxBoot
 * @Package_Name: com.flexink.config.web.security.user
 * @Date		: 2017. 3. 23. 
 * @작성자		: KIMSEOKHOON
 * @변경이력		:
 * @프로그램 설명 	: 사용자 커스텀 validator
 **************************************************************/
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "common.required.msg", new Object[]{"ID"});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "common.required.msg", new Object[]{"password"});
		
		User user = (User) target;
		
		if(!user.getPassword().equals(user.getRetypePassword())) {
			errors.rejectValue("retypePassword", "fail.user.passwordUpdate2", "password unmatchie");
		}
		
		if(StringUtils.isNotBlank(user.getEmail())) {
			// email 정규식 체크
			String emailExp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			Pattern pattern = Pattern.compile(emailExp);
			Matcher matcher = pattern.matcher(user.getEmail());
			if(!matcher.matches()) {
				errors.rejectValue("email", "fail.email.msg");
			};
		}
	}
	
	

}
