/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.repository.main;

import com.futurealgos.micro.auth.models.main.AuthConsent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthConsentRepo extends MongoRepository<AuthConsent, ObjectId> {

    void deleteByPrincipalNameAndRegisteredClient(String principal, RegisteredClient registeredClient);

    Optional<AuthConsent> findByPrincipalNameAndRegisteredClient(String principal, RegisteredClient registeredClient);

}
