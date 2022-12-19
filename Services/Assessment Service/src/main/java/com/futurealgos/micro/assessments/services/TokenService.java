/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * Package: com.futurealgos.micro.assessments.services
 * Project: Prasad Psycho
 * Created On: 28/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class TokenService {

    public String generate(String id, String secret, Map<String, String> claims) {
        JWTCreator.Builder builder = JWT.create()
                .withSubject(id)
                .withIssuedAt(new Date());

        if(claims != null && !claims.isEmpty()) {
            claims.forEach(builder::withClaim);
        }

        return builder.sign(Algorithm.HMAC256(secret));
    }

    public Optional<DecodedJWT> decode(String token, String secret) {
        try {
            return Optional.of(JWT.decode(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean verify(String token, String secret) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            verifier.verify(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String getClaim(String token, String claim) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(claim).asString();
    }
}
