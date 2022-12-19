/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.config;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if(!response.containsHeader("Access-Control-Allow-Origin")) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }

        if(!response.containsHeader("Access-Control-Allow-Credentials")) {
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }

        if(!response.containsHeader("Access-Control-Allow-Methods")) {
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }

        if(!response.containsHeader("Access-Control-Allow-Headers")) {
            response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        }

        if(!response.containsHeader("Access-Control-Max-Age")) {
            response.setHeader("Access-Control-Max-Age", "3600");
        }

        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

}
