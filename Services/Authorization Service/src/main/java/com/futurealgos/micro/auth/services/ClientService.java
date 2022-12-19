/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.services;

import com.futurealgos.micro.auth.models.main.AuthClient;
import com.futurealgos.micro.auth.models.main.AuthDirectory;
import com.futurealgos.micro.auth.repository.main.AuthClientRepo;
import com.futurealgos.micro.auth.repository.main.AuthDirectoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientService implements RegisteredClientRepository {

    @Autowired
    AuthClientRepo authClientRepo;

    @Autowired
    AuthDirectoryRepo authDirectoryRepo;

    @Override
    public void save(RegisteredClient registeredClient) {
        AuthClient client = AuthClient.build(registeredClient);
        authClientRepo.save(client);
    }

    @Override
    public RegisteredClient findById(String s) {
        AuthClient client = authClientRepo.findById(s)
                .orElse(null);
        return client != null ? client.convert() : null;
    }

    @Override
    public RegisteredClient findByClientId(String s) {
        AuthClient client = authClientRepo.findByClientId(s)
                .orElse(null);

        return client != null ? client.convert() : null;
    }


    public AuthDirectory findClientDirectory(String clientId) {
        AuthClient client = authClientRepo.findByClientId(clientId)
                .orElse(null);

        return client != null ? client.getDirectory() : null;
    }
}
