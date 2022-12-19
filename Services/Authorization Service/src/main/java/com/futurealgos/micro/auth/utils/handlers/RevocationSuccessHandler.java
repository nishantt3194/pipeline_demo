/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.handlers;

import com.futurealgos.micro.auth.services.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RevocationSuccessHandler implements AuthenticationSuccessHandler {

    AuthService authService;

    public RevocationSuccessHandler(AuthService authService) {
        this.authService = authService;
    }

    static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MultiValueMap<String, String> parameters = getParameters(request);

        String token = parameters.getFirst(OAuth2ParameterNames.TOKEN);
        String tokenTypeHint = parameters.getFirst(OAuth2ParameterNames.TOKEN_TYPE_HINT);

        assert tokenTypeHint != null;
        authService.revokeViaToken(token, new OAuth2TokenType(tokenTypeHint));

    }
}
