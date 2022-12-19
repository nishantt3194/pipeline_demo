/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.credits.dto;



import com.futurealgos.micro.credits.model.UserTransactions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public record SendBothDto(
        String createdOn,
        Double credits,
        String mode

) {
    public static String convertDateToString(Date date) {
        DateFormat Formatter = new SimpleDateFormat("yyyy-MM-dd");
        return Formatter.format(date);
    }

    public static SendBothDto build(UserTransactions transactions) {

        return new SendBothDto(

                convertDateToString(transactions.getCreatedOn()),
                transactions.getCredits(),
                transactions.getMode().toString()
        );


    }
}





