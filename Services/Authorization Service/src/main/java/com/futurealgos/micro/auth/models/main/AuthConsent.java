/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.models.main;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "authorization_consent")
public class AuthConsent {
    @Id
    String id;

    @DocumentReference(collection = "registered_client")
    RegisteredClient registeredClient;

    @Field("principal_name")
    private String principalName;

    @Field("authorities")
    private Set<GrantedAuthority> authorities;



    public static AuthConsent build(OAuth2AuthorizationConsent consent, RegisteredClient client) {
        return AuthConsent.builder()
                .principalName(consent.getPrincipalName())
                .registeredClient(client)
                .authorities(consent.getAuthorities())
                .build();
    }


    public OAuth2AuthorizationConsent convert() {
        return OAuth2AuthorizationConsent.withId(
                        registeredClient.getId(), principalName)
                .authorities(grantedAuthorities
                        -> grantedAuthorities.addAll(authorities))
                .build();
    }


}
