/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.handlers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SSOEntryPoint implements AuthenticationEntryPoint, InitializingBean {

    private static final Log logger = LogFactory.getLog(LoginUrlAuthenticationEntryPoint.class);
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PortMapper portMapper = new PortMapperImpl();
    private PortResolver portResolver = new PortResolverImpl();
    private String loginFormUrl;
    private boolean forceHttps = false;
    private boolean useForward = false;


    public SSOEntryPoint(String loginFormUrl) {
        Assert.notNull(loginFormUrl, "loginFormUrl cannot be null");
        this.loginFormUrl = loginFormUrl;
    }


    @Override
    public void afterPropertiesSet() {
        Assert.isTrue(StringUtils.hasText(this.loginFormUrl) && UrlUtils.isValidRedirectUrl(this.loginFormUrl),
                "loginFormUrl must be specified and must be a valid redirect URL");
        Assert.isTrue(!this.useForward || !UrlUtils.isAbsoluteUrl(this.loginFormUrl),
                "useForward must be false if using an absolute loginFormURL");
        Assert.notNull(this.portMapper, "portMapper must be specified");
        Assert.notNull(this.portResolver, "portResolver must be specified");
    }

    protected String determineUrlToUseForThisRequest(String clientId, HttpServletRequest request, HttpServletResponse response,
                                                     AuthenticationException exception) {
        if (clientId == null) {
            return this.loginFormUrl;
        }

        return getLoginFormUrl() + "?client_id=" + clientId;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {


        String clientId = request.getParameter("client_id");

        if (!this.useForward) {
            // redirect to login page. Use https if forceHttps true
            String redirectUrl = buildRedirectUrlToLoginPage(clientId, request, response, authException);
            this.redirectStrategy.sendRedirect(request, response, redirectUrl);
            return;
        }
        String redirectUrl = null;
        if (this.forceHttps && "http".equals(request.getScheme())) {
            // First redirect the current request to HTTPS. When that request is received,
            // the forward to the login page will be used.
            redirectUrl = buildHttpsRedirectUrlForRequest(request);
        }
        if (redirectUrl != null) {
            this.redirectStrategy.sendRedirect(request, response, redirectUrl);
            return;
        }
        String loginForm = determineUrlToUseForThisRequest(clientId, request, response, authException);
        logger.debug(LogMessage.format("Server side forward to: %s", loginForm));
        RequestDispatcher dispatcher = request.getRequestDispatcher(loginForm);
        dispatcher.forward(request, response);
        return;

    }

    protected String buildRedirectUrlToLoginPage(String clientId, HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException authException) {
        String loginForm = determineUrlToUseForThisRequest(clientId, request, response, authException);
        if (UrlUtils.isAbsoluteUrl(loginForm)) {
            return loginForm;
        }
        int serverPort = this.portResolver.getServerPort(request);
        String scheme = request.getScheme();
        RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
        urlBuilder.setScheme(scheme);
        urlBuilder.setServerName(request.getServerName());
        urlBuilder.setPort(serverPort);
        urlBuilder.setContextPath(request.getContextPath());
        urlBuilder.setPathInfo(loginForm);
        if (this.forceHttps && "http".equals(scheme)) {
            Integer httpsPort = this.portMapper.lookupHttpsPort(serverPort);
            if (httpsPort != null) {
                // Overwrite scheme and port in the redirect URL
                urlBuilder.setScheme("https");
                urlBuilder.setPort(httpsPort);
            } else {
                logger.warn(LogMessage.format("Unable to redirect to HTTPS as no port mapping found for HTTP port %s",
                        serverPort));
            }
        }
        return urlBuilder.getUrl();
    }

    /**
     * Builds a URL to redirect the supplied request to HTTPS. Used to redirect the
     * current request to HTTPS, before doing a forward to the login page.
     */
    protected String buildHttpsRedirectUrlForRequest(HttpServletRequest request) throws IOException, ServletException {
        int serverPort = this.portResolver.getServerPort(request);
        Integer httpsPort = this.portMapper.lookupHttpsPort(serverPort);
        if (httpsPort != null) {
            RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
            urlBuilder.setScheme("https");
            urlBuilder.setServerName(request.getServerName());
            urlBuilder.setPort(httpsPort);
            urlBuilder.setContextPath(request.getContextPath());
            urlBuilder.setServletPath(request.getServletPath());
            urlBuilder.setPathInfo(request.getPathInfo());
            urlBuilder.setQuery(request.getQueryString());
            return urlBuilder.getUrl();
        }
        // Fall through to server-side forward with warning message
        logger.warn(
                LogMessage.format("Unable to redirect to HTTPS as no port mapping found for HTTP port %s", serverPort));
        return null;
    }

    protected boolean isForceHttps() {
        return this.forceHttps;
    }

    public void setForceHttps(boolean forceHttps) {
        this.forceHttps = forceHttps;
    }

    public String getLoginFormUrl() {
        return this.loginFormUrl;
    }

    protected PortMapper getPortMapper() {
        return this.portMapper;
    }

    public void setPortMapper(PortMapper portMapper) {
        Assert.notNull(portMapper, "portMapper cannot be null");
        this.portMapper = portMapper;
    }

    protected PortResolver getPortResolver() {
        return this.portResolver;
    }

    public void setPortResolver(PortResolver portResolver) {
        Assert.notNull(portResolver, "portResolver cannot be null");
        this.portResolver = portResolver;
    }

    protected boolean isUseForward() {
        return this.useForward;
    }

    public void setUseForward(boolean useForward) {
        this.useForward = useForward;
    }

}
