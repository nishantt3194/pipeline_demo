/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.embedded.Session;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.SessionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Package: com.futurealgos.micro.assessments.models.base<br/>
 * Project: Prasad Psycho<br/>
 * Created On: 02/08/22<br/>
 * Created By: Animeshh Parashar<br/>
 * animeshh@futurealgos.com<br/>
 **/
@Getter
@Setter
@Builder
@Document(collection = "session_data")
public class SessionData extends BaseEntity implements Serializable {


    @Field("ip_address")
    private String ipAddress;

    private SessionState state;

    @Field("last_login")
    private Date lastLogin;

    private String currentSection;

    @Builder.Default
    public Set<SectionData> sections = new TreeSet<>();

    @Builder.Default
    public SortedSet<Session> sessionsHistory = new TreeSet<>();

    public static SessionData shell() { return SessionData.builder().build(); }

}
