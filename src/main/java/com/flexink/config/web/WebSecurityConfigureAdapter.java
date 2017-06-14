package com.flexink.config.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.flexink.common.utils.PhaseUtils;
import com.flexink.config.web.security.AuthenticationFailureHandler;
import com.flexink.config.web.security.AuthenticationSuccessHandler;
import com.flexink.security.Role;
import com.flexink.security.filter.FilterMetadataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfigureAdapter extends WebSecurityConfigurerAdapter {

	@Value("${spring.h2.console.path}")
	String h2ConsolePath;

	
	public static final String ROOT_PATH = "/";
	public static final String SECURITY_PATH = "/security";
	
	public static final String LOGIN_PAGE = SECURITY_PATH + "/login";				// 로그인 페이지 주소
	
	public static final String DEFAULT_SUCCESS_URL = "/sample";						// 로그인 성공시 이동할 기본 주소
	public static final String LOGOUT_SUCCESS_URL = LOGIN_PAGE;						// 로그아웃 성공시 이동할 주소
	public static final String FAILURE_URL = SECURITY_PATH + "/fail";				// 로그인 실패시 이동할 주소
	
	public static final String LOGIN_PROCESSING_URL = SECURITY_PATH + "/sign_in";	// 로그인 프로세싱 주소 (로그인 폼 action)
	public static final String LOGOUT_URL = SECURITY_PATH + "/logout";				// 로그아웃 프로세스 주소
	
	public static final String SESSION_EXPIRED_URL = LOGIN_PAGE + "?expred";		// 중복 로그인시 기존 로그인 사용자가 이동할 주소
	public static final String SESSION_INVALIDSESSION_URL = LOGIN_PAGE + "?invalid";// 세션이 유효하지않은경우 이동할 주소
	
	public static final String USERNAME_PARAMETER = "username";						// 로그인 폼 Input name
	public static final String PASSWORD_PARAMETER = "password";						// 로그인 폼 Input name
	
	public static final String REMEMBER_ME_KEY = "REMEBMER_ME_KEY";
	public static final String REMEMBER_ME_COOKE_NAME = "REMEMBER_ME_COOKE";

	
	public final String[] ignorePages = new String[] { "/resources/**", "/static/**", "/images/**", "/webjars/**", "/error/**", };

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		if (PhaseUtils.isDevelopmentMode()) {
			webSecurity.ignoring().antMatchers("/security/encodePw/**", "/security/matches/**");
		}
		webSecurity.ignoring().antMatchers(ignorePages);
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.debug("----- HttpSecurity -----" + httpSecurity);
		httpSecurity.anonymous()
				.and()
				.authorizeRequests().antMatchers(ROOT_PATH).permitAll()
				
				.antMatchers(HttpMethod.POST, LOGIN_PROCESSING_URL).permitAll()
				.antMatchers(LOGIN_PAGE).permitAll()
				.antMatchers(SECURITY_PATH + "/register").permitAll()
				.antMatchers(h2ConsolePath + "/**").hasRole("SYSTEM")
				.antMatchers("/actuator/**").hasRole("SYSTEM")

				
				.antMatchers("/system/**").hasRole("SYSTEM")
				.antMatchers("/sample*").hasRole("ADMIN")
				.antMatchers("/sample**").hasRole("ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				// .anyRequest().authenticated()
				
				.and()
				.formLogin()
				.usernameParameter(USERNAME_PARAMETER)
				.passwordParameter(PASSWORD_PARAMETER)
				.loginPage(LOGIN_PAGE)
				.loginProcessingUrl(LOGIN_PROCESSING_URL)
				.successHandler(authenticationSuccessHandler())
				.failureUrl(FAILURE_URL)
				.failureHandler(authenticationFailureHandler())
				.permitAll()
				
				.and()
				.logout()
				.logoutUrl(LOGOUT_URL)
				.deleteCookies(REMEMBER_ME_COOKE_NAME)
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.logoutSuccessUrl(LOGOUT_SUCCESS_URL)
				.logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_URL))
				.permitAll()

				.and()
				.sessionManagement().maximumSessions(1).expiredUrl(SESSION_EXPIRED_URL)
				.maxSessionsPreventsLogin(false).and().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.invalidSessionUrl(SESSION_INVALIDSESSION_URL)
				
				.and().csrf().disable().headers().frameOptions().disable()
				.and().rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(tokenBasedRememberMeServices());
				
				// 페이지 동적 권한 확인시 추가 (추후 구현)
				//.and().addFilter(filterSecurityInterceptor());
	}

	@Autowired
	UserDetailsService  userDetailService;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailService;
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	/********************************************************************
	 * @메소드명	: daoAuthenticationProvider
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 사용자 인증 provider
	 ********************************************************************/
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailService);
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		return daoAuthenticationProvider;
	}

	/********************************************************************
	 * @메소드명	: authenticationSuccessHandler
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 로그인 성공시 핸들러
	 ********************************************************************/
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler();
	}
	
	/********************************************************************
	 * @메소드명	: authenticationFailureHandler
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 로그인 실패시 핸들러
	 ********************************************************************/
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler();
	}
	

	/********************************************************************
	 * @메소드명	: filterSecurityInterceptor
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 페이지 권한 확인 인터셉터
	 ********************************************************************/
	@Bean
	public FilterSecurityInterceptor filterSecurityInterceptor() {
		FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
		filterSecurityInterceptor.setAuthenticationManager(authenticationManager);
		filterSecurityInterceptor.setSecurityMetadataSource(filterMetadataSource());
		filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
		return filterSecurityInterceptor;
	}
	
	@Bean
	public FilterMetadataSource filterMetadataSource() {
		return new FilterMetadataSource();
	}
	

	@Bean
	public AffirmativeBased affirmativeBased() {
		List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
		accessDecisionVoters.add(roleVoter());
		AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
		return affirmativeBased;
	}

	/********************************************************************
	 * @메소드명	: roleVoter
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Security Voter
	 ********************************************************************/
	@Bean
	public RoleHierarchyVoter roleVoter() {
		RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
		roleHierarchyVoter.setRolePrefix("ROLE_");
		return roleHierarchyVoter;
	}

	/********************************************************************
	 * @메소드명	: roleHierarchy
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: RoleHierarchy 설정 (하드코딩) (추후 DB연계 설정 필요)
	 ********************************************************************/
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy(Role.ROLE_SYSTEM + " > " + Role.ROLE_ADMIN + " > " + Role.ROLE_USER + " > ROLE_USER3 > ROLE_USER2");
		return roleHierarchy;
	}

	/********************************************************************
	 * @메소드명 : tokenBasedRememberMeServices
	 * @작성자 : KIMSEOKHOON
	 * @메소드 내용 : 리멤버미 토큰 (쿠키용)
	 ********************************************************************/
	@Bean
	public RememberMeServices tokenBasedRememberMeServices() {
		TokenBasedRememberMeServices tokenBasedRememberMeServices = new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService());
		//tokenBasedRememberMeServices.setAlwaysRemember(true);
		tokenBasedRememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 31);
		tokenBasedRememberMeServices.setCookieName(REMEMBER_ME_COOKE_NAME);
		return tokenBasedRememberMeServices;
	}

	
	/********************************************************************
	 * @메소드명	: persistentTokenRepository
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 리멤버미 토큰 (DB 저장용)
	 ********************************************************************/
	/*@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}*/
}
