/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.payload;

import com.futurealgos.micro.assessments.models.base.Assignee;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.assessments.dto.payload
 * Project: Prasad Psycho
 * Created On: 27/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public record ExecLogin(
        String request,
        String email,
        String phone,
        String extn,
        String name,
        String token,
        Assignee.ExecState state) implements Serializable {
    public static ExecLogin of(Assignee assignee) {

        return new ExecLogin(null,
                assignee.getExaminee().getEmail(),
                assignee.getExaminee().getPhone(),
                assignee.getExaminee().getExtn(),
                assignee.getExaminee().getName(),
                null, assignee.getExecState());
    }
}
