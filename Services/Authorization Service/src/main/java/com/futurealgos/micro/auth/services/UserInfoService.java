/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;

import com.futurealgos.micro.auth.models.base.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    @Autowired
    DetailsService detailsService;

    public OidcUserInfo build(JwtAuthenticationToken principal) {
        String username = principal.getName();

        User user = detailsService.loadUserByUsername(username);

        detailsService.disableFirstSetup(user.getUsername());

        if (user.getPrimaryGroup() != null && user.getPrimaryGroup().getGroupName().equals("Partners")) {
            return OidcUserInfo.builder()
                    .subject(user.getUsername())
                    .name(user.getFullName())
                    .givenName(user.getFirstName())
                    .familyName(user.getLastName())
                    .claims(claims -> claims.putAll(principal.getToken().getClaims()))
                    .claim("tag", user.getPartner().getName())
                    .claim("first_time_login", user.isFirstTimeLogin())
                    .claim("p_id", user.getPartner().getId().toHexString())
                    .claim("status", user.getPartner().getStatus().toString())
                    .claim("credits", user.getPartner().getCredits() != null &&
                            user.getPartner().getCredits() >= 0 ? user.getPartner().getCredits() : 0)
                    .build();
        }

        else if(user.getPrimaryGroup() != null) {
            return OidcUserInfo.builder()
                    .subject(user.getUsername())
                    .name(user.getFullName())
                    .givenName(user.getFirstName())
                    .familyName(user.getLastName())
                    .claims(claims -> claims.putAll(principal.getToken().getClaims()))
                    .claim("tag", user.getPrimaryGroup().getGroupName())
                    .build();
        }

        return OidcUserInfo.builder()
                .subject(user.getUsername())
                .name(user.getFullName())
                .claim("first_time_login", user.isFirstTimeLogin())
                .givenName(user.getFirstName())
                .familyName(user.getLastName())
                .claims(claims -> claims.putAll(principal.getToken().getClaims()))
                .claim("tag", "--")
                .build();
    }
}
