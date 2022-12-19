/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.models.base;

import com.futurealgos.micro.users.models.root.BaseEntity;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.users.models.base
 * Project: Prasad Psycho
 * Created On: 20/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test")
public class Test extends BaseEntity implements Serializable {

    @Indexed(unique = true)
    @Field("test_id")
    private String testId;

    @Indexed
    @Field("test_name")
    private String testName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(testId, test.testId).append(testName, test.testName).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(testId).toHashCode();
    }
}
