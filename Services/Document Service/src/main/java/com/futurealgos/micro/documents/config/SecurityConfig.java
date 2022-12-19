/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.documents.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CORSFilter corsFilter) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/health").permitAll()
                .antMatchers("/**").permitAll();
//                .anyRequest().hasAnyAuthority("SCOPE_api.read")
//                .and()
//                .oauth2ResourceServer()
//                .jwt();


        http.headers()
                .httpStrictTransportSecurity().includeSubDomains(true).maxAgeInSeconds(31536000).and()
                .frameOptions().and()
                .contentSecurityPolicy("frame-ancestors 'self', 'http://127.0.0.1:4200', 'http://127.0.0.1:4401', 'https://admin.prasadpsycho.industrialdata.in','https://partner.prasadpsycho.industrialdata.in'")
                .and()
                .xssProtection().xssProtectionEnabled(true)
                .block(true);

        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
        return http.build();
    }

}