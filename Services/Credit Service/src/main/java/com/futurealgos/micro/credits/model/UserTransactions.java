/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Document(collection = "user_transaction")
public class UserTransactions extends Transactions implements Serializable {


    @Serial
    private static final long serialVersionUID = -1388498008954496077L;

    private Double initialBalance;

    private Double finalBalance;
}
