/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.config;

import com.futurealgos.micro.auth.services.AuthProvider;
import com.futurealgos.micro.auth.services.ClientService;
import com.futurealgos.micro.auth.services.DetailsService;
import com.futurealgos.micro.auth.utils.filters.AuthenticationPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.RequestContextFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    AuthProvider authProvider;

    @Autowired
    ClientService clientService;

    @Autowired
    DetailsService detailsService;

    @Bean
    SecurityFilterChain defaultSecurityChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        http
                .authorizeRequests(requests ->
                        requests
                                .antMatchers(HttpMethod.OPTIONS, "/userinfo").permitAll()
                                .antMatchers("/actuator/**").permitAll()
                                .antMatchers("/").permitAll()
                                .antMatchers("/login/**").permitAll()
                                .antMatchers("/oauth2/**").permitAll()
                                .antMatchers("/.well-known/**").permitAll()
                                .antMatchers("/error").permitAll()
                                .antMatchers("/clients/**").permitAll()
                                .antMatchers("/assets/**").permitAll()
                                .antMatchers("/static/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin()
                .loginPage("/login")
                .failureForwardUrl("/login?error")
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .cors().disable()
                .csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);

//        http.logout()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");

        http.addFilter(new AuthenticationPermissionEvaluator(authenticationConfiguration.getAuthenticationManager(), clientService, detailsService));

        http.addFilterBefore(new RequestContextFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder builder) {
        builder
                .authenticationProvider(authProvider);
    }

}
