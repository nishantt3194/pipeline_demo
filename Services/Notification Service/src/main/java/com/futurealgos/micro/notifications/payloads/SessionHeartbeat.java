/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.notifications.payloads
 * Project: Prasad Psycho
 * Created On: 02/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionHeartbeat implements Serializable {
    public String id;
}
