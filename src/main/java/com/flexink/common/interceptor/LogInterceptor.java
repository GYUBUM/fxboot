package com.flexink.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.flexink.config.web.security.user.UserDetailsHelper;
import com.flexink.security.SessionUserDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		if (log.isDebugEnabled()) {
			SessionUserDetail sessionUser = UserDetailsHelper.getLoginUserDetails();
			log.debug("======================================          START         ======================================");
			log.debug("Request URI \t:  " + request.getRequestURI());
			//log.debug("Request IP Address :  " + HttpUtils.getRemoteAddress(request));
			if(sessionUser != null) {
				log.debug("Request Username \t:  " + sessionUser.getUsername());
				log.debug("Request Authorities:  " + sessionUser.getAuthorities());
			}
		}
		return super.preHandle(request, response, obj);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("======================================           END          ======================================\n");
		}
		super.postHandle(request, response, obj, modelAndView);
	}
	
}


