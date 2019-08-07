package com.kennen.auth;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * AccessToken 에 UserId 값을 추가하기 위해 사용
 */
public class AuthenticationTokenConverter extends JwtAccessTokenConverter {

    private static final String USER_ID = "userId";
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        TodoUserDetails todoUserDetails = (TodoUserDetails) authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put(USER_ID, todoUserDetails.getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        OAuth2AccessToken enhance = super.enhance(accessToken, authentication);
        return enhance;
    }



}
