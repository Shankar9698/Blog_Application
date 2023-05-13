package com.blog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.AuthorizationScope;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}

	private List<SecurityReference> sf() {
		springfox.documentation.service.AuthorizationScope scope = new springfox.documentation.service.AuthorizationScope(
				"global", "accessEverything");

		return Arrays.asList(
				new SecurityReference("JWT", new springfox.documentation.service.AuthorizationScope[] { scope }));
	}

	@Bean // it will auto inject where ever needed
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys())).

				select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
		return new ApiInfo("Blog Application", "this project is developed by shankar", "1.0", "Terms and services",
				new Contact("shankar", "https://github.com/Shankar9698/Blog_Application",
						"shankarkamalapur6@gmail.com"),
				"License of Apis", "Api license urls", java.util.Collections.emptyList());
	}
}
