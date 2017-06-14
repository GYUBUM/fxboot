package com.flexink.config.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.flexink.config.web.WebSecurityConfigureAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogoutSuccessHandler
		implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

	/*private String targetUrl;
	public public LogoutSuccessHandler(String targetUrl) {
		this.targetUrl = targetUrl;
	}*/
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		log.debug("onLogoutSuccess:"+request+"     "+response+ "   "+authentication);

		if (authentication != null && authentication.getDetails() != null) {
			try {
				request.getSession().invalidate();
				log.debug("User Successfully Logout");
				//you can add more codes here when the admin successfully logs out,
				//such as updating the database for last active.
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.setStatus(HttpServletResponse.SC_OK);
		//redirect to login
		response.sendRedirect(WebSecurityConfigureAdapter.LOGOUT_SUCCESS_URL);
		//super.onLogoutSuccess(request,response,authentication);
	}

}
