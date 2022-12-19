package com.futurealgos.micro.assessments.dto.response;

import com.futurealgos.micro.assessments.models.base.Question;
import com.futurealgos.micro.assessments.models.base.Report;
import com.futurealgos.micro.assessments.models.base.ScoreTable;
import com.futurealgos.micro.assessments.models.embedded.InterpretationReport;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class ReportInfo {

    public LocalDate  dob;
    public String gender;
    public String uniqueId;
    public String reason;
    public String name;
    public String overview;
    public String introduction;
    public Map<Integer,Integer> answerSheet;
    private Map<Question, Integer> scoreSheet;
    public String suggestion;
    public String partner;
    public List<InterpretationReport> subscaleIntrReports;
    public InterpretationReport overallIntrReport;
    public String assessor;
    public String description;
    public static ReportInfo build(Report report){
        return  ReportInfo.builder()
                .dob(report.getDob())
                .gender(report.getGender().name())
                .uniqueId(report.getUniqueId())
                .reason(report.getReason())
                .name(report.getExamineeName())
                .overview(report.getOverview())
                .introduction(report.getIntroduction())
                .answerSheet(report.getAnswerSheet())
                .partner(report.getPartner().getName())
                .assessor(report.getAssessor().fullName())
                .overallIntrReport(report.getOverallIntrReport())
                .subscaleIntrReports(report.getSubscaleIntrReports())
                .scoreSheet(report.getScoreSheet())
                .description(report.getDescription())
                .build();
    }
}
