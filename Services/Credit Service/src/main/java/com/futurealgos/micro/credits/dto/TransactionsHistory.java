/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.dto;


import com.futurealgos.micro.credits.model.UserTransactions;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionsHistory {
    private String email;
    private String name;
    private String createdOn;
    private Double credits;
    private String category;
    private String mode;


    public static TransactionsHistory build(UserTransactions transactions) {
        return TransactionsHistory.builder()
                .category(transactions.getCategory().toString())
                .createdOn(transactions.getCreatedOn().toString())
                .mode(transactions.getMode().toString())
                .credits(transactions.getCredits())
                .build();
    }
}
