/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;

import com.futurealgos.micro.testing.models.base.StagedTest;
import com.futurealgos.micro.testing.models.base.Test;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitProcessingWrapper {

    public Test test;

    public StagedTest payload;

    public String admin;


}
