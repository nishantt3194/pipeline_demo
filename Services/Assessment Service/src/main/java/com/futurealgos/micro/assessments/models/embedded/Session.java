/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.embedded;

import com.futurealgos.micro.assessments.utils.enums.SessionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Package: com.futurealgos.micro.assessments.models.embedded
 * Project: Prasad Psycho
 * Created On: 03/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Builder
@Getter
@Setter
public class Session implements Comparable<Session>, Serializable {

    private Date start;

    private Date end;

    @Field("ip_address")
    private String ipAddress;

    private SessionState state;

    @Field("total_time")
    private Long totalTime;


    public void calculateTotalTime() {
        if(this.state.equals(SessionState.CONNECTED)) this.totalTime = new Date().getTime() - this.start.getTime();
        else this.totalTime = this.end.getTime() - this.start.getTime();
    }

    @Override
    public int compareTo(Session session) {
        return session.start.compareTo(this.start);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("start", start)
                .append("end", end)
                .append("ipAddress", ipAddress)
                .append("state", state)
                .append("totalTime", totalTime)
                .toString();
    }
}
