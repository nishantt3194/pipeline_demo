/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.models.embedded;

import com.futurealgos.micro.testing.utils.mappings.TestMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 * Package: com.futurealgos.micro.testing.models
 * Project: Prasad Psycho
 * Created On: 13/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCredit {

    @Field
    private Double value;

    @Field(TestMap.DISCOUNT)
    private Discount discount;

    @Field(TestMap.DISCOUNT_HISTORY)
    private List<Discount> history = new LinkedList<>();

    public TestCredit(Double value) {
        this.value = value;
    }

    public Double getCalculatedValue() {
        if(discount == null || discount.getExpiry().isBefore(Instant.now())) return value;


        if(discount.getType() == Discount.Type.PERCENTAGE) {
            return value - (value * discount.getValue() / 100);
        } else {
            return value - discount.getValue();
        }
    }

    public boolean hasDiscount() {
        return discount != null && discount.getExpiry().isAfter(Instant.now());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("discount", discount)
                .toString();
    }
}
