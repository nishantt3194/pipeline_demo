/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.notifications.payloads;

import com.futurealgos.micro.notifications.utils.enums.SessionState;

import java.io.Serializable;
import java.util.Date;

/**
 * Package: com.futurealgos.micro.notifications.payloads
 * Project: Prasad Psycho
 * Created On: 03/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record SessionNotification(
        String id,
        String ip,
        SessionState state,
        Date timestamp
) implements Serializable {
}
