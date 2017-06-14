package com.flexink.vo;

import com.flexink.config.web.security.user.UserDetailsHelper;
import com.flexink.security.SessionUserDetail;

public class CommonVo {

	private SessionUserDetail sessionUserDetail;
	
	public CommonVo() {
		Object authenticatedObj = UserDetailsHelper.getAuthenticatedUser();
		if(authenticatedObj instanceof SessionUserDetail) {
			this.sessionUserDetail = (SessionUserDetail) authenticatedObj;
		} else {
			this.sessionUserDetail = null;
		}
	}
	
	/********************************************************************
	 * @메소드명	: getUserDetails
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 인증된 로그인정보 객체
	 ********************************************************************/
	public SessionUserDetail getUserDetails() {
		return this.sessionUserDetail;
	}
	
	/********************************************************************
	 * @메소드명	: getLoginId
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 인증된 사용자 ID
	 ********************************************************************/
	public String getLoginId() {
		if(sessionUserDetail != null && !sessionUserDetail.equals("anonymousUser")) {
			return sessionUserDetail.getUsername();
		}
		return null;
	}

	
}
