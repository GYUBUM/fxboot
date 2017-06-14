package com.flexink.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:/application.yml")
public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
	
	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addDecoratorPath("/*", "/WEB-INF/decorators/sample/decorator.jsp")
			.addDecoratorPath("/security/login*", "/WEB-INF/decorators/sample/header.jsp")
			.addDecoratorPath("/security/register*", "/WEB-INF/decorators/sample/header.jsp")
			.addDecoratorPath("/error/**", "/WEB-INF/decorators/sample/header.jsp")
			.addExcludedPath("/h2-console/**")
			.addExcludedPath("/security/**")
			.addExcludedPath("/popup/**")
			.addExcludedPath("/files/**")
			.addExcludedPath("/");
    }
}
