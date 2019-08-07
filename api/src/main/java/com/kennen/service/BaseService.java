package com.kennen.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

/**
 * Service 에서 AccessToken 을 통해 추가 된 UserId를 사용하기 위함
 * ResourceTokenConverter 참고
 */
public class BaseService {

    protected String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    protected Long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails authenticationDetails = ((OAuth2AuthenticationDetails) authentication.getDetails());
        Map<String, Object> decodedDetails = (Map<String, Object>) authenticationDetails.getDecodedDetails();

        if (!decodedDetails.containsKey("userId") ||
                decodedDetails.get("userId") == null) {
            throw new UsernameNotFoundException(authentication.getName());
        }
        return Long.parseLong(decodedDetails.get("userId").toString());
    }
}
