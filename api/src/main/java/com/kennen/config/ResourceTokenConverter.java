package com.kennen.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * AccessToken 을 추출한 뒤 AuthorizationServer 에서 추가 했던 userId 값을
 * Authentication 객체의 details 에 설정한다.
 * 후에 SecurityContextHolder 에서 userId 를 사용하기 위함
 */
public class ResourceTokenConverter extends JwtAccessTokenConverter {

    private static final String USER_ID = "userId";


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        Map<String, Object> additionalInfo = new HashMap<>();
        if (map.containsKey(USER_ID)) {
            additionalInfo.put(USER_ID, map.get(USER_ID));
        }
        oAuth2Authentication.setDetails(additionalInfo);
        return oAuth2Authentication;
    }


}
