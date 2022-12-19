/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.main;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "registered_client")
public class AuthClient {

    @Id
    private String id;
    @Indexed(unique = true)
    @Field("client_id")
    private String clientId;
    @Field("issued_at")
    private Instant clientIdIssuedAt;
    @Field("secret")
    private String clientSecret;
    @Field("expires_at")
    private Instant clientSecretExpiresAt;
    @Field("client_name")
    private String clientName;

    @Indexed
    @DocumentReference(collection = "auth_directory")
    private AuthDirectory directory;

    @Field("methods")
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    @Field("grant_types")
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    @Field("redirect_uris")
    private Set<String> redirectUris;
    @Field("scope")
    private Set<String> scopes;

    public static AuthClient build(RegisteredClient client) {
        return AuthClient.builder()
                .clientId(client.getClientId())
                .clientName(client.getClientName())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(client.getClientIdIssuedAt() != null ? client.getClientIdIssuedAt() : Instant.now())
                .clientSecretExpiresAt(client.getClientSecretExpiresAt() != null ? client.getClientSecretExpiresAt() : Instant.now().plus(120, ChronoUnit.DAYS))
                .clientAuthenticationMethods(client.getClientAuthenticationMethods())
                .authorizationGrantTypes(client.getAuthorizationGrantTypes())
                .redirectUris(client.getRedirectUris())
                .scopes(client.getScopes())
                .build();
    }

    public RegisteredClient convert() {
        return RegisteredClient.withId(id)
                .clientId(clientId)
                .clientName(clientName)
                .clientSecret(clientSecret)
                .clientIdIssuedAt(clientIdIssuedAt)
                .clientSecretExpiresAt(clientSecretExpiresAt)
                .clientAuthenticationMethods(methods -> methods.addAll(clientAuthenticationMethods))
                .authorizationGrantTypes(authGrants -> authGrants.addAll(authorizationGrantTypes))
                .redirectUris((uris -> uris.addAll(redirectUris)))
                .scopes(scopeSet -> scopeSet.addAll(scopes))
                .clientSettings(
                        ClientSettings.builder()
                                .requireAuthorizationConsent(true)
                                .requireProofKey(true)
                                .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofHours(8)).build())
                .build();
    }
}
