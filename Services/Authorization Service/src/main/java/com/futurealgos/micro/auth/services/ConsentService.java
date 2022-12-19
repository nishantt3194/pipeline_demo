/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;

import com.futurealgos.micro.auth.models.main.AuthConsent;
import com.futurealgos.micro.auth.repository.main.AuthConsentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
public class ConsentService implements OAuth2AuthorizationConsentService {

    @Autowired
    AuthConsentRepo authConsentRepo;

    @Autowired
    ClientService clientService;

    @Override
    public void save(OAuth2AuthorizationConsent consent) {
        AuthConsent authConsent = AuthConsent.build(consent, clientService.findById(consent.getRegisteredClientId()));

        authConsentRepo.save(authConsent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        RegisteredClient registeredClient = clientService.findById(oAuth2AuthorizationConsent.getRegisteredClientId());
        authConsentRepo
                .deleteByPrincipalNameAndRegisteredClient(oAuth2AuthorizationConsent.getPrincipalName(),
                        registeredClient);
    }

    @Override
    public OAuth2AuthorizationConsent findById(String clientId, String principal) {
        RegisteredClient registeredClient = clientService.findById(clientId);
        AuthConsent authConsent = authConsentRepo
                .findByPrincipalNameAndRegisteredClient(principal, registeredClient).orElse(null);

        return authConsent != null ? authConsent.convert() : null;
    }
}
