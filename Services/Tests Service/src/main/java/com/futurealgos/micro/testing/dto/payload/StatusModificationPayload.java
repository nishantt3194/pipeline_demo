/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.dto.payload;

/**
 * Package: com.futurealgos.micro.testing.dto.payload
 * Project: Prasad Psycho
 * Created On: 08/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record StatusModificationPayload(
        String identifier,
        Boolean status,
        String statusEnum) {
}
