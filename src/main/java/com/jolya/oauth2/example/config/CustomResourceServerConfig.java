package com.jolya.oauth2.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
@EnableResourceServer
public class CustomResourceServerConfig extends ResourceServerConfigurerAdapter {


	//url for the recuperation of the public key
	@Value("${security.oauth2.resource.jwt.key-set-uri}")
	private String jwksUrl;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenServices(tokenServices());
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwkTokenStore(jwksUrl, createJwtAccessTokenConverter(), null);
	}

	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public JwtAccessTokenConverter createJwtAccessTokenConverter() {
		return new CustomJwtAccessTokenConverter();
	}


	/**
	 * Configure the security, each http verb as a scope defined, admin scope can use all http verb
	 * and loggers actuator enndpoint
	 *
	 * @param http
	 * @throws Exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasAnyScope('get','admin')")
				.antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasAnyScope('post','admin')")
				.antMatchers(HttpMethod.PUT, "/api/**").access("#oauth2.hasAnyScope('put','admin')")
				.antMatchers(HttpMethod.DELETE, "/api/**").access("#oauth2.hasAnyScope('delete','admin')")
				.antMatchers(HttpMethod.PATCH, "/api/**").access("#oauth2.hasAnyScope('patch','admin')")
				.requestMatchers(EndpointRequest.to("loggers")).access("#oauth2.hasScope('admin')");

	}
}
