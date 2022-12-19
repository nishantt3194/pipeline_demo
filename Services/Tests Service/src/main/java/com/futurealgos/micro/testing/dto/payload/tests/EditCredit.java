/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.tests;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.testing.dto.payload.tests
 * Project: Prasad Psycho
 * Created On: 13/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record EditCredit(
        String id,
        Double credits) implements Serializable {
}
