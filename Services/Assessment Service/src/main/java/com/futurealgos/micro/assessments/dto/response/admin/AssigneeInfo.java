/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.admin;

import com.futurealgos.micro.assessments.models.base.Assignee;

import java.io.Serializable;

/**
 * Package: com.futurealgos.micro.assessments.dto.response.admin
 * Project: Prasad Psycho
 * Created On: 04/09/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public class AssigneeInfo implements Serializable {

    public static AssigneeInfo from(Assignee assignee) {
        return new AssigneeInfo();
    }

}
