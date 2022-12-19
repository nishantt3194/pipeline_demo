/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.filters;

import com.futurealgos.micro.auth.models.main.AuthDirectory;
import com.futurealgos.micro.auth.services.ClientService;
import com.futurealgos.micro.auth.services.DetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationPermissionEvaluator extends UsernamePasswordAuthenticationFilter {

    ClientService clientService;

    DetailsService detailsService;

    public AuthenticationPermissionEvaluator(AuthenticationManager authenticationManager, ClientService clientService, DetailsService detailsService) {
        super(authenticationManager);
        setAuthenticationManager(authenticationManager);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.clientService = clientService;
        this.detailsService = detailsService;
    }


    /**
     * Initiates the Authentication Process, also validates the if directory obtained
     * from credentials and the directory obtained from the token are the same.
     *
     * @param request  from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     *                 redirect as part of a multi-stage authentication process (such as OpenID).
     * @return Authentication object representing the authenticated user.
     * @throws AuthenticationException in case of Credentials Mismatch or other authentication failure.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String clientId = request.getParameter("client_id");
        AuthDirectory directory = clientService.findClientDirectory(clientId);

        if (detailsService.doesUserExistInDirectory(request.getParameter("username"), directory)) {
            return super.attemptAuthentication(request, response);
        } else {
            throw new BadCredentialsException("User does not exist in directory");
        }
    }
}
