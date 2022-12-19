package com.futurealgos.micro.credits.dto;

import com.futurealgos.micro.credits.model.UserTransactions;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PartnerTransaction {
    private String createdOn;
    private Double credits;
    private String category;
    private String mode;


    public static PartnerTransaction build(UserTransactions transactions) {
        return PartnerTransaction.builder()
                .category(transactions.getCategory().toString())
                .createdOn(transactions.getCreatedOn().toString())
                .mode(transactions.getMode().toString())
                .credits(transactions.getCredits())
                .build();
    }
}
