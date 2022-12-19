/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.config;

import com.futurealgos.micro.auth.services.AuthService;
import com.futurealgos.micro.auth.services.UserInfoService;
import com.futurealgos.micro.auth.utils.filters.CORSFilter;
import com.futurealgos.micro.auth.utils.handlers.RevocationSuccessHandler;
import com.futurealgos.micro.auth.utils.handlers.SSOEntryPoint;
import com.futurealgos.micro.auth.utils.jose.Jwks;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
public class AuthServerConfig {

    public static final String CONSENT_ENDPOINT = "/oauth/consent";

    @Value("${fal.auth.issuer}")
    String issuer;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http,
                                                             CORSFilter corsFilter,
                                                             UserInfoService userInfoService,
                                                             AuthService authService) throws Exception {
        OAuth2AuthorizationServerConfigurer<HttpSecurity> configurer =
                new OAuth2AuthorizationServerConfigurer<>();

        RequestMatcher matcher = configurer.getEndpointsMatcher();
        Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper = context -> {
            OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
            JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();
            return userInfoService.build(principal);
        };

        configurer
                .authorizationEndpoint(endpoint ->
                        endpoint.consentPage(CONSENT_ENDPOINT));

        http.exceptionHandling().authenticationEntryPoint(new SSOEntryPoint("/login"));

        http.requestMatcher(matcher)
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .authorizeRequests(requests ->
                        requests
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(matcher))
                .apply(configurer)
                .oidc(oidc ->
                        oidc.userInfoEndpoint(info -> info.userInfoMapper(userInfoMapper)));


        configurer.tokenRevocationEndpoint(endpoint -> endpoint.revocationResponseHandler(new RevocationSuccessHandler(authService)));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
        return http.formLogin(Customizer.withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer(issuer)
                .build();
    }

}
