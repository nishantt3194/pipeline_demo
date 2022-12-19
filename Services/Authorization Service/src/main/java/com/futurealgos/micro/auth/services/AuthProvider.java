/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;


import com.futurealgos.micro.auth.models.base.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    DetailsService detailsService;

    @Autowired
    PasswordEncoder encoder;

//    @Autowired
//    private HttpServletRequest request;
//
//
//    @Autowired
//    private HttpServletResponse response;
//
//    static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
//        parameterMap.forEach((key, values) -> {
//            if (values.length > 0) {
//                for (String value : values) {
//                    parameters.add(key, value);
//                }
//            }
//        });
//        return parameters;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        User user = detailsService.loadUserByUsername(authentication.getName());
        String password = authentication.getCredentials().toString();
        return verify(user, password);
    }

    private Authentication verify(UserDetails user, String password) {
        if (encoder.matches(password, user.getPassword()))
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );

        throw new BadCredentialsException("Incorrect Username or Password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
