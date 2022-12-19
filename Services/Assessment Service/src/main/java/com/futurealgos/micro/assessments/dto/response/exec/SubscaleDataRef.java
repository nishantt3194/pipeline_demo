/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.dto.response.exec;

import com.futurealgos.micro.assessments.models.base.Subscale;
import com.futurealgos.micro.assessments.utils.enums.TimingType;
import lombok.Builder;

import java.io.Serializable;
import java.time.Duration;

/**
 * Package: com.futurealgos.micro.assessments.dto.response.exec
 * Project: Prasad Psycho
 * Created On: 01/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
public class SubscaleDataRef implements Serializable {

    public String id;
    public String name;
    public String status;
    public String instructions;
    public Long time;
    public TimingType type;


    public static SubscaleDataRef build(Subscale subscale) {
        return SubscaleDataRef.builder()
                .id(subscale.getId().toHexString())
                .name(subscale.getName())
                .status("not_started")
                .instructions(subscale.getInstructions())
                .type(subscale.getType())
                .time(subscale.getType().equals(TimingType.TIMED) ? Duration.ofMinutes(subscale.getTotalTime()).toMillis() : null)
                .build();
    }
}
