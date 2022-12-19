/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.main;


import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authorization")
public class AuthStore {

    @Id
    private String id;

    @Indexed
    @Field("client")
    private String clientId;

    @Field("principal_name")
    private String principalName;

    @Indexed
    @Field("grant_type")
    private String authorizationGrantType;

    private String attributes;

    @Indexed
    private String state;

    @Indexed
    @Field("code_value")
    private String authorizationCodeValue;

    @Field("code_issued_at")
    private Instant authorizationCodeIssuedAt;

    @Field("code_expires_at")
    private Instant authorizationCodeExpiresAt;

    @Field("code_metadata")
    private String authorizationCodeMetadata;

    @Indexed
    @Field("access_token_value")
    private String accessTokenValue;

    @Field("access_token_issued_at")
    private Instant accessTokenIssuedAt;

    @Field("access_token_expires_at")
    private Instant accessTokenExpiresAt;

    @Field("access_token_metadata")
    private String accessTokenMetadata;

    @Field("access_token_type")
    private String accessTokenType;

    @Field("access_token_scope")
    private String accessTokenScopes;

    @Indexed
    @Field("refresh_token_value")
    private String refreshTokenValue;

    @Field("refresh_token_issued_at")
    private Instant refreshTokenIssuedAt;

    @Field("refresh_token_expires_at")
    private Instant refreshTokenExpiresAt;

    @Field("refresh_token_metadata")
    private String refreshTokenMetadata;

    @Indexed
    @Field("oidc_token_value")
    private String oidcIdTokenValue;

    @Field("oidc_token_issued_at")
    private Instant oidcIdTokenIssuedAt;

    @Field("oidc_token_expires_at")
    private Instant oidcIdTokenExpiresAt;

    @Field("oidc_token_metadata")
    private String oidcIdTokenMetadata;

    @Field("oidc_token_claims")
    private String oidcIdTokenClaims;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("clientId", clientId)
                .append("principalName", principalName)
                .append("authorizationGrantType", authorizationGrantType)
                .append("attributes", attributes)
                .append("state", state)
                .append("authorizationCodeValue", authorizationCodeValue)
                .append("authorizationCodeIssuedAt", authorizationCodeIssuedAt)
                .append("authorizationCodeExpiresAt", authorizationCodeExpiresAt)
                .append("authorizationCodeMetadata", authorizationCodeMetadata)
                .append("accessTokenValue", accessTokenValue)
                .append("accessTokenIssuedAt", accessTokenIssuedAt)
                .append("accessTokenExpiresAt", accessTokenExpiresAt)
                .append("accessTokenMetadata", accessTokenMetadata)
                .append("accessTokenType", accessTokenType)
                .append("accessTokenScopes", accessTokenScopes)
                .append("refreshTokenValue", refreshTokenValue)
                .append("refreshTokenIssuedAt", refreshTokenIssuedAt)
                .append("refreshTokenExpiresAt", refreshTokenExpiresAt)
                .append("refreshTokenMetadata", refreshTokenMetadata)
                .append("oidcIdTokenValue", oidcIdTokenValue)
                .append("oidcIdTokenIssuedAt", oidcIdTokenIssuedAt)
                .append("oidcIdTokenExpiresAt", oidcIdTokenExpiresAt)
                .append("oidcIdTokenMetadata", oidcIdTokenMetadata)
                .append("oidcIdTokenClaims", oidcIdTokenClaims)
                .toString();
    }
}
