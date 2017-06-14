package com.flexink.config.web.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.servlet.LocaleResolver;

import com.flexink.config.web.WebSecurityConfigureAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFailureHandler
		implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

	private RedirectStrategy redirectStrategy = null;

	@Autowired
	MessageSourceAccessor messageSource;

	public AuthenticationFailureHandler() {
		redirectStrategy = new DefaultRedirectStrategy();
	}
	
	LocaleResolver localeResolver= null;
	
	public void setLocaleResolver(LocaleResolver localeResolver){
		this.localeResolver = localeResolver;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		log.debug("AuthenticationFailureHandler : {}", exception.getMessage());
		
		/*System.out.println(request.getLocale());
		System.out.println(response.getLocale());
		System.out.println(LocaleContextHolder.getLocale());*/

		/*String failMessage = "";

		try {
			throw exception;
		} catch (DisabledException e) {
			failMessage = messageSource.getMessage("error.login.fail.disable");
		} catch (LockedException e) {
			failMessage = messageSource.getMessage("error.login.fail.locked");
		} catch (AccountExpiredException e) {
			failMessage = messageSource.getMessage("error.login.fail.accountExpired");
		} catch (UsernameNotFoundException e) {
			failMessage = messageSource.getMessage("error.login.fail.usernameNotFound");
		} catch (CredentialsExpiredException e) {
			failMessage = messageSource.getMessage("error.login.fail.credentialsExpired");
		} catch (BadCredentialsException e) {
			failMessage = messageSource.getMessage("error.login.fail.badCredentials");
		} catch (SessionAuthenticationException e) {
			failMessage = messageSource.getMessage("error.login.fail.sessionAuth");
		} catch (Exception e) {
			failMessage = messageSource.getMessage("error.login.fail");
		}*/

		if (request.getSession(false) != null) {
			//request.getSession().setAttribute("errorMessage", failMessage);
			request.getSession().setAttribute("errorMessage", exception.getMessage());
			//request.getSession().setAttribute("errorCode", "AnonymousAuthenticationProvider.incorrectKey");
		}

		redirectStrategy.sendRedirect(request, response, WebSecurityConfigureAdapter.LOGIN_PAGE);
	}

}
