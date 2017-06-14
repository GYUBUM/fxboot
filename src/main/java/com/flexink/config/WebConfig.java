package com.flexink.config;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Context;
import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.flexink.common.error.ErrorController;
import com.flexink.common.interceptor.LogInterceptor;
import com.flexink.config.resolver.ParamsVoArgumentResolver;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("/index");
	}
	
	/********************************************************************
	 * @메소드명	: siteMeshFilter
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: SiteMesh 필터 등록
	 ********************************************************************/
	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CustomSiteMeshFilter());
		return filterRegistrationBean;
	}
	
	/********************************************************************
	 * @메소드명	: addInterceptors
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 인터셉터 등록
	 ********************************************************************/
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(csrfTokenAddingInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
	}
	
	/********************************************************************
	 * @메소드명	: localeChangeInterceptor
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Request로 넘어오는 language parameter를 받아서 locale로 설정
	 ********************************************************************/
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");	// 파라미터 name
		return localeChangeInterceptor;
	}

	/********************************************************************
	 * @메소드명	: sessionLocaleResolver
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Locale 설정 기준
	 ********************************************************************/
	@Bean(name="localeResolver")
	public LocaleResolver localeResolver() {
		// 헤더 기준
		//AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		// 세션 기준
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// 쿠키 기준
		// CookieLocaleResolver localeResolver = new CookieLocaleResolver();

		// 최초 기본 로케일 강제 설정 (헤더 리졸버 제외)
		localeResolver.setDefaultLocale(new Locale("ko_KR"));
		return localeResolver;
	}
	
	/********************************************************************
	 * @메소드명	: messageSource
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 메세지 소스
	 * @return
	 * ResourceBundleMessageSource
	 ********************************************************************/
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(3600);
		return messageSource;
	}
	
	
	/********************************************************************
	 * @메소드명	: getMessageSourceAccessor
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Message Helper
	 * @return
	 * MessageSourceAccessor
	 ********************************************************************/
	@Bean
	public MessageSourceAccessor messageSourceAccessor(){
		ReloadableResourceBundleMessageSource messageSource = messageSource();
		return new MessageSourceAccessor(messageSource);
	}
	
	
	/*@Bean
	public HandlerInterceptor sessionFactoryTransctionInterceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				sessionFactory.getCurrentSession().beginTransaction();
				return true;
			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				sessionFactory.getCurrentSession().getTransaction().commit();
			}
		};
	}*/
	
	/********************************************************************
	 * @메소드명	: csrfTokenAddingInterceptor
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: CSRF 토큰 생성 인터셉터 
	 ********************************************************************/
	@Bean
	public HandlerInterceptor csrfTokenAddingInterceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView view) {
				CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (token != null && null != view) {
					System.out.println(token.getParameterName());
					System.out.println(token.getParameterName());
					System.out.println(token.getParameterName());
					System.out.println(token.getParameterName());
					System.out.println(token.getParameterName());
					view.addObject(token.getParameterName(), token);
				}
			}
		};
	}
	
	/********************************************************************
	 * @메소드명	: containerCustomizer
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 에러 처리
	 ********************************************************************/
	@Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.addErrorPages(
					new ErrorPage(HttpStatus.UNAUTHORIZED, 			ErrorController.PATH_ROOT + ErrorController.PATH_ERROR_401),
					new ErrorPage(HttpStatus.FORBIDDEN, 			ErrorController.PATH_ROOT + ErrorController.PATH_ERROR_403),
					new ErrorPage(HttpStatus.NOT_FOUND,				ErrorController.PATH_ROOT + ErrorController.PATH_ERROR_404),
					new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorController.PATH_ROOT + ErrorController.PATH_ERROR_500),
					new ErrorPage(Throwable.class, 					ErrorController.PATH_ROOT + ErrorController.PATH_ERROR_DEFAULT)
				);
			}
		};
    }
	
	
	/********************************************************************
	 * @메소드명	: addResourceHandlers
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 리소스 패스 설정
	 ********************************************************************/
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resource/**")	.addResourceLocations("/resource/");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/static/**")	.addResourceLocations("/static/");
		registry.addResourceHandler("/img/**")		.addResourceLocations("/img/");
		registry.addResourceHandler("/image/**")	.addResourceLocations("/image/");
		registry.addResourceHandler("/images/**")	.addResourceLocations("/images/");
		registry.addResourceHandler("/css/**")		.addResourceLocations("/css/");
		registry.addResourceHandler("/style/**")	.addResourceLocations("/style/");
		registry.addResourceHandler("/webjars/**")	.addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver();
        pageableHandlerMethodArgumentResolver.setPageParameterName("pageNumber");
        pageableHandlerMethodArgumentResolver.setSizeParameterName("pageSize");
        argumentResolvers.add(pageableHandlerMethodArgumentResolver);
        argumentResolvers.add(new ParamsVoArgumentResolver());
    }
	
	/********************************************************************
	 * @메소드명	: customizer
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Tomcat 8.0 이상일 경우 Cookie Parser 사용
	 ********************************************************************/
	@Bean
	public EmbeddedServletContainerCustomizer customizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				if (container instanceof TomcatEmbeddedServletContainerFactory) {
	                ((TomcatEmbeddedServletContainerFactory) container).addContextCustomizers(new TomcatContextCustomizer() {
	                    @Override
	                    public void customize(Context context) {
	                        context.setCookieProcessor(new LegacyCookieProcessor());
	                    }
	                });
	            }
			}
		};
	}
	
	@Bean
	public HandlerInterceptor csrfTokenAddingIntserceptor() {
		return new HandlerInterceptorAdapter() {
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView view) {
				CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (token != null && null != view) {
					view.addObject(token.getParameterName(), token);
				}
			}
		};
	}

	
	
}
