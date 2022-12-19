package com.futurealgos.micro.assessments.services;

import com.futurealgos.micro.assessments.dto.payload.ReportPayload;
import com.futurealgos.micro.assessments.dto.response.ReportInfo;
import com.futurealgos.micro.assessments.exceptions.NotFoundException;
import com.futurealgos.micro.assessments.models.base.*;
import com.futurealgos.micro.assessments.models.embedded.*;
import com.futurealgos.micro.assessments.repos.QuestionRepository;
import com.futurealgos.micro.assessments.repos.ReportsRepo;
import com.futurealgos.micro.assessments.repos.ScoreRepository;
import com.futurealgos.micro.assessments.repos.ScoreTableRepository;
import com.futurealgos.micro.assessments.utils.specs.services.FetcherSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

@Service
public class ReportService implements FetcherSpec<Report> {

    @Autowired
    ReportsRepo reportsRepo;

    @Autowired
    ScoreTableRepository scoreTableRepo;
    @Autowired
    TestService testService;
    @Autowired
    AssigneeService assigneeService;

    @Autowired
    ScoreRepository scoreRepo;

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    QuestionRepository questionRepo;


    @Override
    public Report fetch(String identifier) throws NotFoundException {
        return fetch(new ObjectId(identifier));
    }

    @Override
    public Report fetch(ObjectId identifier) throws NotFoundException {
        return reportsRepo.findById(identifier)
                .orElseThrow(() -> new NotFoundException("Could not find any Report with ID " + identifier));
    }

    @Override
    public List<Report> fetchAll(List<String> identifier) {
        List<Report> result = new ArrayList<>();
        reportsRepo.findAllById(identifier.stream()
                .map(ObjectId::new).collect(Collectors.toList())).forEach(result::add);
        return result;
    }


    public List<ScoreTable> generateScoreTable(Assignee assignee, Test test) {
        Set<ScoreNormMap> scoreNormMap = new HashSet<>();

        for (RequiredDataHolder dataHolder : assignee.getData()) {
            Norm.Type type = dataHolder.getNorm().getType();
            Classification val = dataHolder.getNorm().getClassification().stream()
                    .filter(classification -> {
                        switch (type) {
                            case RANGE -> {
                                int min = Integer.parseInt(classification.getValue().split("-")[0]);
                                int max = Integer.parseInt(classification.getValue().split("-")[1]);
                                return min <= Integer.parseInt(dataHolder.getValue()) && Integer.parseInt(dataHolder.getValue()) <= max;
                            }
                            case NUMBER -> {
                                return Integer.parseInt(classification.getValue()) == Integer.parseInt(dataHolder.getValue());
                            }
                            case TEXT -> {
                                return classification.getValue().equals(dataHolder.getValue());
                            }
                        }
                        return false;
                    }).findFirst().orElse(null);

            if (val != null) {
                scoreNormMap.add(new ScoreNormMap(dataHolder.getNorm(), val));
            }
        }

        List<ScoreTable> toReturn = new ArrayList<>();
        for (ScoreTable table : test.getScoreTables()) {
            if (table.getNormMap().containsAll(scoreNormMap)) {
                toReturn.add(table);
            }
        }

        return toReturn;
    }


    /**
     * @param min    number in the Range
     * @param max    number in the Range
     * @param number Number to be Compared
     * @return true if the number exists between min or max
     */
    public boolean checkInBetween(String min, String max, String number) {
        long newMin = Long.parseLong(min);
        long newMax = Long.parseLong(max);
        long newNumber = Long.parseLong(number);
        return newMin < newNumber && newNumber < newMax;
    }


    /**
     * @param normsName
     * @return
     */
    public List<String> generateTablename(List<String> normsName, List<String> subscaleNames) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i <= normsName.size(); i++) {
            prefix = prefix.append(normsName.get(i)).append("_");
        }

        List<String> finalName = new ArrayList<>();

        finalName.add(prefix.toString() + "_" + "overall");

        for (String subscaleName : subscaleNames) {
            String newString = prefix.toString() + "_" + subscaleName;
            finalName.add(newString);
        }
        return finalName;
    }


    public ReportInfo generateReport(ReportPayload payload) {
        Assignee assignee = assigneeService.fetch(payload.assigneeId());
        if (assignee.getReport() != null) return ReportInfo.build(assignee.getReport());

        Test test = testService.fetch(assignee.getTestId());
        List<ScoreTable> tables = generateScoreTable(assignee, test);
        Partner partner = assignee.getRequest().getPartner();

        Examinee examinee = assignee.getExaminee();
        Map<Question, Integer> scoreSheet = generateScoreSheet(test, assignee.getAnswerSheet());

        InterpretationReport overallReport = null;
        List<InterpretationReport> subscaleReport = generateSubscaleInterpretationReport(scoreSheet, tables);

        Report report = Report.builder()
                .dob(examinee.getDob())
                .gender(examinee.getGender())
                .uniqueId(examinee.getClientId())
                .reason(assignee.getReason())
                .assessor(assignee.getAssessor())
                .examineeName(examinee.getName())
                .Test(test)
                .overview(test.getOverview())
                .introduction(test.getIntroduction())
                .answerSheet(generateAnswerSheet(assignee.getAnswerSheet()))
                .subscaleIntrReports(subscaleReport)
                .scoreSheet(scoreSheet)
                .partner(partner)
                .description(test.getDescription())
                .build();

        Report finalReport = reportsRepo.save(report);
        return ReportInfo.build(finalReport);
    }

    public List<InterpretationReport> generateSubscaleInterpretationReport(Map<Question, Integer> scoreSheet, List<ScoreTable> tables) {
        Map<Subscale, Integer> subscaleWiseScore = scoreSheet.keySet()
                .stream().collect(Collectors.groupingBy(Question::getSubscale, summingInt(scoreSheet::get)));

        Map<Subscale, ScoreTable> tablesMap = tables.stream()
                .collect(Collectors.toMap(ScoreTable::getSubscale, table -> table));

        List<InterpretationReport> reports = new ArrayList<>();

        for (Subscale subscale : subscaleWiseScore.keySet()) {
            ScoreTable table = tablesMap.get(subscale);
            Integer actualScore = subscaleWiseScore.get(subscale);
            Score score = table.getScores()
                    .stream().filter(sc -> sc.getMinRawScore() <= actualScore && actualScore <= sc.getMaxRawScore()).findFirst()
                    .orElse(null);

            reports.add(InterpretationReport.builder()
                    .type(InterpretationReport.Type.DIMENSIONAL)
                    .heading(subscale.getName())
                    .description(subscale.getDescription())
                    .actualScore(actualScore)
                    .score(score)
                    .build());
        }

        return reports;

    }

    public Map<Question, Integer> generateScoreSheet(Test test, AnswerSheet sheet) {
        Map<Question, Integer> scoreSheet = new HashMap<>();
        Map<ObjectId, Question> questionsIdMap = test.getQuestions()
                .stream().collect(Collectors.toMap(Question::getId, question -> question));

        Map<String, Integer> answers = sheet.getAnswers();

        for (String qsId : answers.keySet()) {
            if (answers.get(qsId) != null) {
                Question question = questionsIdMap.get(new ObjectId(qsId));
                scoreSheet.put(question, question.getOptions()
                        .get(answers.get(qsId)).getScore());
            }
        }

        return scoreSheet;
    }

    public void createSubscaleTable(Assignee assignee) {

        Test test = testService.fetch(assignee.getTestId());
        Map<String, Integer> answers = assignee.getAnswerSheet().getAnswers();

        List<Question> questions = getQuestions(answers);
        Map<String, Integer> data = new HashMap<>();

        questions.forEach(qs -> {
            Integer selected = answers.get(qs.getId().toHexString());
            if (selected != null) {
                Option option = qs.getOptions().stream()
                        .filter(op -> op.getIndex() == selected).findFirst().orElse(null);

                if (option != null) {
                    String subscale = qs.getSubscale().getName();
                    data.put(subscale, data.containsKey(subscale) ?
                            data.get(subscale) + option.getScore() : option.getScore());
                }
            }
        });
    }

    public Map<Integer, Integer> generateAnswerSheet(AnswerSheet answerSheet) {

        Map<String, Integer> originalSheet = answerSheet.getAnswers();
        Map<Integer, Integer> finalSheet = new HashMap<>();

        List<Question> questions = getQuestions(originalSheet);
        originalSheet.forEach((k, v) -> {
            for (Question question : questions) {
                finalSheet.put(question.getIndex(), v);
            }
        });

        return finalSheet;

    }

    public List<Question> getQuestions(Map<String, Integer> originalSheet) {
        List<Question> questions = new ArrayList<>();
        questionRepo.findAllById(originalSheet.keySet().stream()
                .map(ObjectId::new).collect(Collectors.toList())).forEach(questions::add);

        return questions;
    }

}
