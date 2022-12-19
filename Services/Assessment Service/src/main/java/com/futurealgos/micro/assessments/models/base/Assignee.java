/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.assessments.models.base;

import com.futurealgos.micro.assessments.models.embedded.AssessmentToken;
import com.futurealgos.micro.assessments.models.embedded.RequiredDataHolder;
import com.futurealgos.micro.assessments.models.embedded.Session;
import com.futurealgos.micro.assessments.models.root.BaseEntity;
import com.futurealgos.micro.assessments.utils.enums.NormTemplate;
import com.futurealgos.micro.assessments.utils.enums.SessionState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@Document(collection = "assignee")
public final class Assignee extends BaseEntity {

    public enum ExecState {
        NOT_STARTED,
        IN_PROGRESS,
        COMPLETED
    }

    @Field("user_id")
    @Indexed
    private String uid;

    @Field("test_id")
    private ObjectId testId;

    @Indexed
    @DocumentReference(collection = "examinee", lazy = true)
    private Examinee examinee;

    @Field("exec_state")
    public ExecState execState;

    @Indexed
    @DocumentReference(lazy = true, collection = "assessment_request")
    private AssessmentRequest request;

    private Set<RequiredDataHolder> data;

    private String reason;

    private User assessor;

    @Indexed
    @Field("token")
    private AssessmentToken token;

    @Builder.Default
    @Field("report_generated")
    private boolean reportGenerated = false;

    @Field("session")
    @DocumentReference(collection = "session_data")
    private SessionData session;

    @DocumentReference(collection = "answer_sheet", lazy = true)
    private AnswerSheet answerSheet;

    @DocumentReference(collection = "report", lazy = true)
    private Report report;


    public SessionData initSession(String ipAddress, Date timestamp) {
        SessionData sessionData = this.session;
        if(sessionData == null) sessionData = SessionData.shell();

        if(this.session == null || !sessionData.getState().equals(SessionState.CONNECTED)) {
            Session session = Session.builder()
                    .ipAddress(ipAddress)
                    .start(timestamp)
                    .state(SessionState.CONNECTED)
                    .build();

            sessionData.sessionsHistory.add(session);
        }
        sessionData.setIpAddress(ipAddress);
        sessionData.setLastLogin(timestamp);
        sessionData.setState(SessionState.CONNECTED);
        return sessionData;
    }

    public SessionData suspendSession(Date timestamp) {
        Session session = this.session.sessionsHistory.first();
        session.setEnd(timestamp);
        session.setState(SessionState.SUSPENDED);
        session.calculateTotalTime();
        this.session.setState(SessionState.SUSPENDED);

        return this.session;
    }

    public SessionData terminateSession(Date timestamp) {
        Session session = this.session.sessionsHistory.first();
        session.setEnd(timestamp);
        session.setState(SessionState.DISCONNECTED);
        session.calculateTotalTime();
        this.session.setState(SessionState.DISCONNECTED);

        return this.session;
    }

    public static Assignee build(Examinee examinee, Set<Norm> norms) {
        Assignee.AssigneeBuilder builder = Assignee.builder();
        builder.execState(ExecState.NOT_STARTED);
        builder.examinee(examinee);
        builder.uid(examinee.getEmail() != null ?
                examinee.getEmail() : examinee.getExtn() + "-" + examinee.getPhone());

        builder.data(mapValues(examinee, norms));

        return builder.build();
    }

    public Long fetchElapsedTime() {
        if(this.session == null) return 0L;
        AtomicLong time = new AtomicLong();
        this.session.sessionsHistory.stream()
                .filter(s -> !s.getState().equals(SessionState.CONNECTED))
                .forEach(s -> time.addAndGet(s.getTotalTime()));

        long sectionTime = this.session.getSections() == null || this.session.getSections().isEmpty() ? 0 :
                this.session.getSections()
                .stream().filter(s -> s.getStatus().equals(SectionData.Status.COMPLETED))
                .mapToLong(SectionData::getTimeLimit).sum();

        return Math.max(time.get(), sectionTime);
    }


    public Long fetchElapsedTimeForCurrentSubscale() {
        if(this.session == null ||
                this.session.getSessionsHistory().size() < 1 ||
                this.session.getCurrentSection() == null) return 0L;

        if(session.getSessionsHistory().size() < 2)
            return session.getSessionsHistory().first().getTotalTime();

        AtomicLong time = new AtomicLong();

        SectionData section = this.session.getSections().stream()
                .filter(s -> s.getSubscale()
                        .equals(this.session.getCurrentSection()))
                .findFirst().orElse(null);

        if(section == null) return 0L;

        for (Session session : session.getSessionsHistory()) {
            if(session.getStart().before(section.getStartTime()) || session.getStart().equals(section.getStartTime())) {
                var period = session.getEnd().getTime() - section.getStartTime().getTime();
                time.addAndGet(period);
                break;
            }
            else {
                time.addAndGet(session.getTotalTime() != null ? session.getTotalTime() : 0L);
            }
        }

        return time.get();
    }


    private static Set<RequiredDataHolder> mapValues(Examinee examinee, Set<Norm> norms) {
        Set<RequiredDataHolder> data = norms.stream().map(RequiredDataHolder::new).collect(Collectors.toSet());
        for (RequiredDataHolder holder: data) {
            Norm norm = holder.getNorm();
            holder.setType(norm.getType().equals(Norm.Type.TEXT) ? RequiredDataHolder.Type.STRING : RequiredDataHolder.Type.NUMBER);

            if(norm.getIsPredefined() && norm.getTemplate() != null) {
                String value = resolveValue(examinee, norm.getTemplate());
                holder.setValue(value != null && value.length() > 0 ? value : null);
            }
        }

        return data;
    }

    private static String resolveValue(Examinee examinee, NormTemplate template) {
        switch (template) {
            case AGE -> {
                Period period = examinee.getDob().until(LocalDate.now());
                return String.valueOf(period.getYears());
            }
            case GENDER -> {
                return examinee.getGender().getTag();
            }
            case LANGUAGE -> {
                return examinee.getLanguage();
            }
            case INCOME -> {
                return String.valueOf(examinee.getIncome());
            }
            case MARITAL_STATUS -> {
                return examinee.getMaritalStatus().getTag();
            }
            case FAMILY_TYPE -> {
                return examinee.getFamily().getTag();
            }
            case PROFESSION -> {
                return examinee.getProfession();
            }
            default -> {
                return null;
            }
        }
    }

}
