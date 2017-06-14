package com.flexink.config.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.flexink.config.web.WebSecurityConfigureAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationSuccessHandler
		implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	
	@Autowired
    protected AuthenticationManager authenticationManager;
    private RequestCache requestCache = null;
    private RedirectStrategy redirectStrategy = null;

    public AuthenticationSuccessHandler(){
        requestCache        = new HttpSessionRequestCache();
        redirectStrategy    = new DefaultRedirectStrategy();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	log.debug("onAuthenticationSuccess::"+authentication);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY+"_AUTHENTICATION",authentication);
        // redirect 지정
        sendRedirectDefaultUrl(request,response);
    }

    /********************************************************************
     * @메소드명	: sendRedirectSessionUrl
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 세션에 저장되어있는 URL로 이동
     ********************************************************************/
    private void sendRedirectSessionUrl(HttpServletRequest request,HttpServletResponse response) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String targetUrl = savedRequest.getRedirectUrl();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /********************************************************************
     * @메소드명	: sendRedirectRefererUrl
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 요청된 Request Refferer URL로 이동
     ********************************************************************/
    private void sendRedirectRefererUrl(HttpServletRequest request,	HttpServletResponse response) throws IOException {
        String targetUrl = request.getHeader("REFERER");
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /********************************************************************
     * @메소드명	: sendRedirectDefaultUrl
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 기본설정된 URL로 이동
     ********************************************************************/
    private void sendRedirectDefaultUrl(HttpServletRequest request,	HttpServletResponse response) throws IOException {
        redirectStrategy.sendRedirect(request, response, WebSecurityConfigureAdapter.DEFAULT_SUCCESS_URL);
    }
    
}
