/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload.norms;

/**
 * Package: com.futurealgos.micro.testing.dto.payload.norms
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record NewClassification(
        String text,
        Integer value,
        Integer min,
        Integer max) {
}
