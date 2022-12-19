/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.repository.main;

import com.futurealgos.micro.auth.models.main.AuthStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthStoreRepo extends MongoRepository<AuthStore, String> {

    Optional<AuthStore> findByState(String state);

    Optional<AuthStore> findByAuthorizationCodeValue(String authorizationCodeValue);

    Optional<AuthStore> findByAccessTokenValue(String accessTokenValue);

    Optional<AuthStore> findByRefreshTokenValue(String refreshTokenValue);

    Optional<AuthStore> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(String state, String authorizationCodeValue, String accessTokenValue, String refreshTokenValue);


}
