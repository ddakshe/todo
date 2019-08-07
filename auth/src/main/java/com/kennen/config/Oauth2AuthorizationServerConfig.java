package com.kennen.config;

import com.kennen.auth.AuthenticationTokenConverter;
import com.kennen.auth.TodoClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(todoClientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .reuseRefreshTokens(true)
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager);
    }

    /*
     * 아래 메소드에서 cors 필터를 tokenEndPoint 에 추가하는 이유는 다음과 같다.
     * OAuth2AuthorizationServer 설정을 하는 경우
     * SpringSecurityFilterChain 은 두 개의 filterChain(FilterChainProxy) 을 갖는다.
     * 하나는 일반적인 요청에 대한 filterChain, 다른 하나는 인증 요청에 대한 filterChain 이다.
     * 따라서 일반 요청에 대대해서 cors filter 를 추가하더라도 인증 요청에 대해서는 추가되지 않았기 때문에
     * cors filter 가 동작하지 않으므로 preflight 가 정상 동작하지 않기 때문에 별도로 추가해야 한다.
     * */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        source.registerCorsConfiguration("/oauth/token", configuration);
        CorsFilter corsFilter = new CorsFilter(source);
        security.addTokenEndpointAuthenticationFilter(corsFilter);
    }

    @Bean
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setRefreshTokenValiditySeconds(3600);
        return defaultTokenServices;
    }

    @Bean
    public AuthenticationTokenConverter accessTokenConverter() {
        AuthenticationTokenConverter tokenConverter = new AuthenticationTokenConverter();
        tokenConverter.setSigningKey("todo");
        return tokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
        return jwtTokenStore;
    }

    @Bean
    public TodoClientDetailsService todoClientDetailsService(){
        TodoClientDetailsService jdbcClientDetailsService = new TodoClientDetailsService();
        return jdbcClientDetailsService;
    }
}
