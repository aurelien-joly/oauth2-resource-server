package com.jolya.oauth2.example.swagger;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * The type Swagger 2 config.
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
	private static final List<AuthorizationScope> AUTH_SCOPE = Collections.singletonList(
			new AuthorizationScope("admin", "admin operations"));

	private static final String SECURITY_SCHEMA_OAUTH2 = "oauth2";

	@Value("${security.oauth2.resource.jwt.token-uri}")
	private static final String SWAGGER_TOKEN_URL = "";

	private static final String SWAGGER_CLIENT_ID = "";

	private static final String SWAGGER_CLIENT_SECRET = "";

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${management.endpoints.web.base-path:/actuator}")
	private String actuatorRootPath;

	/**
	 * Api internal docket.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket apiInternal() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
						.basePackage("com.jolya.oauth2.example.controller"))
				.paths(PathSelectors.regex("/api/.*"))
				.build()
				.apiInfo(apiEndPointsInfo())
				.securitySchemes(Collections.singletonList(oauth2()))
				.securityContexts(Collections.singletonList(securityContext()))
				.groupName("Demo API");
	}

	/**
	 * Api actuator docket.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket apiActuator() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
						.basePackage("org.springframework"))
				.paths(PathSelectors.regex(actuatorRootPath + "/.*"))
				.build()
				.apiInfo(actuatorEndPointsInfo())
				.securitySchemes(Collections.singletonList(oauth2()))
				.securityContexts(Collections.singletonList(securityContext()))
				.groupName("Actuator API");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(internalPaths())
				.build();
	}


	@Bean
	public SecurityScheme oauth2() {
		return new OAuthBuilder()
				.name(SECURITY_SCHEMA_OAUTH2)
				.grantTypes(grantTypes())
				.scopes(AUTH_SCOPE)
				.build();
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
				.clientId(SWAGGER_CLIENT_ID)
				.clientSecret(SWAGGER_CLIENT_SECRET)
				.scopeSeparator(" ")
				.build();
	}

	private Predicate<String> internalPaths() {
		return PathSelectors.regex(".*");
	}


	private List<SecurityReference> defaultAuth() {
		return Collections.singletonList(
				new SecurityReference("oauth2", AUTH_SCOPE.toArray(new AuthorizationScope[0]))
		);
	}


	private List<GrantType> grantTypes() {
		GrantType grantType = new ClientCredentialsGrant(SWAGGER_TOKEN_URL);
		return Collections.singletonList(grantType);
	}


	private ApiInfo apiEndPointsInfo() {
		return new ApiInfoBuilder().title(applicationName + " REST API")
				.description(applicationName + " REST API")
				.contact(ApiInfo.DEFAULT_CONTACT)
				.build();
	}

	private ApiInfo actuatorEndPointsInfo() {
		return new ApiInfoBuilder().title(applicationName + " ACTUATOR REST API")
				.description(applicationName + " ACTUATOR REST API")
				.contact(ApiInfo.DEFAULT_CONTACT)
				.build();
	}
}
