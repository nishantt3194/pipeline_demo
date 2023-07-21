package com.futurealgos.admin.security.filters;

import com.futurealgos.admin.models.primary.User;
import com.futurealgos.admin.services.administration.UserService;
import com.futurealgos.admin.utils.constants.ConstantService;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class UserAuthorityPopulator extends GenericFilter {

    @Autowired
    ConstantService constants;

    @Autowired
    UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof Jwt prn) {
            var username = (String) prn.getClaims().get(constants.id());

            User user = userService.getUser(username);

            Authentication enrichedAuthentication = new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(enrichedAuthentication);
        }

        chain.doFilter(request, response);

    }
}
