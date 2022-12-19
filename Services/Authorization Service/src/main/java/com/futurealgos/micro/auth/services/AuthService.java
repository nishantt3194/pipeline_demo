/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.futurealgos.micro.auth.models.main.AuthStore;
import com.futurealgos.micro.auth.repository.main.AuthStoreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AuthService implements OAuth2AuthorizationService {

    private final AuthStoreRepo authStoreRepo;

    private final RegisteredClientRepository registeredClientRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AuthService(AuthStoreRepo authStoreRepo, RegisteredClientRepository registeredClientRepository) {
        Assert.notNull(authStoreRepo, "Authorization Repository cannot be null");
        Assert.notNull(registeredClientRepository, "Registered Client Repository cannot be null");

        this.authStoreRepo = authStoreRepo;
        this.registeredClientRepository = registeredClientRepository;
        ClassLoader loader = AuthService.class.getClassLoader();
        List<Module> securityModules = SecurityJackson2Modules.getModules(loader);
        this.mapper.registerModules(securityModules);
        this.mapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
    }

    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.authStoreRepo.save(build(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        this.authStoreRepo.deleteById(authorization.getId());
    }

    @Override
    public OAuth2Authorization findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return this.authStoreRepo.findById(id).map(this::convert).orElse(null);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");

        Optional<AuthStore> result;
        if (tokenType == null) {
            result = this.authStoreRepo.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(token, token, token, token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByAuthorizationCodeValue(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByAccessTokenValue(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByRefreshTokenValue(token);
        } else {
            result = Optional.empty();
        }

        return result.map(this::convert).orElse(null);
    }

    public void revokeViaToken(String token, OAuth2TokenType tokenType) {
        Optional<AuthStore> result;
        if (tokenType == null) {
            result = this.authStoreRepo.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(token, token, token, token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByAuthorizationCodeValue(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByAccessTokenValue(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType.getValue())) {
            result = this.authStoreRepo.findByRefreshTokenValue(token);
        } else {
            result = Optional.empty();
        }


        result.ifPresent(this.authStoreRepo::delete);

    }

    private void setTokenValues(
            OAuth2Authorization.Token<?> token,
            Consumer<String> tokenValueConsumer,
            Consumer<Instant> issuedAtConsumer,
            Consumer<Instant> expiresAtConsumer,
            Consumer<String> metadataConsumer) {
        if (token != null) {
            OAuth2Token oAuth2Token = token.getToken();
            tokenValueConsumer.accept(oAuth2Token.getTokenValue());
            issuedAtConsumer.accept(oAuth2Token.getIssuedAt());
            expiresAtConsumer.accept(oAuth2Token.getExpiresAt());
            metadataConsumer.accept(writeMap(token.getMetadata()));
        }
    }

    public AuthStore build(OAuth2Authorization authorization) {
        AuthStore entity = new AuthStore();
        entity.setId(authorization.getId());
        entity.setClientId(authorization.getRegisteredClientId());
        entity.setPrincipalName(authorization.getPrincipalName());
        entity.setAuthorizationGrantType(authorization.getAuthorizationGrantType().getValue());
        entity.setAttributes(writeMap(authorization.getAttributes()));
        entity.setState(authorization.getAttribute(OAuth2ParameterNames.STATE));

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                authorization.getToken(OAuth2AuthorizationCode.class);
        setTokenValues(
                authorizationCode,
                entity::setAuthorizationCodeValue,
                entity::setAuthorizationCodeIssuedAt,
                entity::setAuthorizationCodeExpiresAt,
                entity::setAuthorizationCodeMetadata
        );

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                authorization.getToken(OAuth2AccessToken.class);
        setTokenValues(
                accessToken,
                entity::setAccessTokenValue,
                entity::setAccessTokenIssuedAt,
                entity::setAccessTokenExpiresAt,
                entity::setAccessTokenMetadata
        );
        if (accessToken != null && accessToken.getToken().getScopes() != null) {
            entity.setAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.getToken().getScopes(), ","));
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken =
                authorization.getToken(OAuth2RefreshToken.class);
        setTokenValues(
                refreshToken,
                entity::setRefreshTokenValue,
                entity::setRefreshTokenIssuedAt,
                entity::setRefreshTokenExpiresAt,
                entity::setRefreshTokenMetadata
        );

        OAuth2Authorization.Token<OidcIdToken> oidcIdToken =
                authorization.getToken(OidcIdToken.class);
        setTokenValues(
                oidcIdToken,
                entity::setOidcIdTokenValue,
                entity::setOidcIdTokenIssuedAt,
                entity::setOidcIdTokenExpiresAt,
                entity::setOidcIdTokenMetadata
        );
        if (oidcIdToken != null) {
            entity.setOidcIdTokenClaims(writeMap(oidcIdToken.getClaims()));
        }

        return entity;
    }

    public OAuth2Authorization convert(AuthStore store) {
        RegisteredClient client = registeredClientRepository.findById(store.getClientId());
        if (client == null) {
            throw new IllegalStateException("Client not found: " + store.getClientId());
        }
        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(client)
                .id(store.getId())
                .principalName(store.getPrincipalName())
                .authorizationGrantType(resolveAuthorizationGrantType(store.getAuthorizationGrantType()))
                .attributes(attributes -> attributes.putAll(parseMap(store.getAttributes())));

        if (store.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, store.getState());
        }

        if (store.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    store.getAuthorizationCodeValue(),
                    store.getAuthorizationCodeIssuedAt(),
                    store.getAuthorizationCodeExpiresAt());
            builder.token(authorizationCode, metadata -> metadata.putAll(parseMap(store.getAuthorizationCodeMetadata())));
        }

        if (store.getAccessTokenValue() != null) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    store.getAccessTokenValue(),
                    store.getAccessTokenIssuedAt(),
                    store.getAccessTokenExpiresAt(),
                    StringUtils.commaDelimitedListToSet(store.getAccessTokenScopes()));
            builder.token(accessToken, metadata -> metadata.putAll(parseMap(store.getAccessTokenMetadata())));
        }

        if (store.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    store.getRefreshTokenValue(),
                    store.getRefreshTokenIssuedAt(),
                    store.getRefreshTokenExpiresAt());
            builder.token(refreshToken, metadata -> metadata.putAll(parseMap(store.getRefreshTokenMetadata())));
        }

        if (store.getOidcIdTokenValue() != null) {
            OidcIdToken idToken = new OidcIdToken(
                    store.getOidcIdTokenValue(),
                    store.getOidcIdTokenIssuedAt(),
                    store.getOidcIdTokenExpiresAt(),
                    parseMap(store.getOidcIdTokenClaims()));
            builder.token(idToken, metadata -> metadata.putAll(parseMap(store.getOidcIdTokenMetadata())));
        }

        return builder.build();

    }

    private Map<String, Object> parseMap(String data) {
        try {
            return this.mapper.readValue(data, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private String writeMap(Map<String, Object> metadata) {
        try {
            return this.mapper.writeValueAsString(metadata);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }
}
