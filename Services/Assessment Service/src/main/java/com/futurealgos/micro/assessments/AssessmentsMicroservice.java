/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Assessments Service", version = "1.0", description = "Central Service to manage Assessments"),
        tags = {}, servers = {@Server(url = "http://localhost:8095/local/assessments", description = "Local Environment Setup")})
public class AssessmentsMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(AssessmentsMicroservice.class, args);
    }

}
