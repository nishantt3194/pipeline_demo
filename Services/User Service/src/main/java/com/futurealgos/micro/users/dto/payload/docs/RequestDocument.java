/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.dto.payload.docs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDocument {

    public String id;

    public String status;

    public String name;

    public String message;
}
