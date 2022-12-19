/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.response;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.testing.dto.response
 * Project: Prasad Psycho
 * Created On: 14/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public class WeightedResponse implements Serializable {

    public String message;

    public Object data;

    public WeightedResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
