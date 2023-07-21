package com.futurealgos.admin.utils.constants;


import org.springframework.stereotype.Service;

@Service
//@Profile("production")
public class ProductionConstants implements ConstantService {

    @Override
    public String id() {
        return "unique_name";
    }

//    @Override
//    public String extractSubject(AADOAuth2AuthenticatedPrincipal principal) {
//        return principal.getClaim(id()).toString();
//    }
//
//    @Override
//    public String extractSubject(Jwt jwt) {
//        return jwt.getClaim(id());
//    }

    @Override
    public String defaultArea() {
        return "All";
    }

    public String utilizationLossCategory() {
        return "Utilization Loss";
    }

//    @Override
//    public AWSCredentialsProvider credentials() {
//        return new AWSStaticCredentialsProvider(
//                new BasicAWSCredentials(accessID, accessKey));
//    }
}
