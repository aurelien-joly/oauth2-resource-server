package com.jolya.oauth2.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		OAuth2AccessToken enhancedAcessToken = super.enhance(accessToken, authentication);
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(enhancedAcessToken);
		customAccessToken.getScope().addAll(userService.getRolesForUser(authentication.getPrincipal().toString()));
		return customAccessToken;
	}


}
