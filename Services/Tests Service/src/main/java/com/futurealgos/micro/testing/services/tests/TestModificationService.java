/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services.tests;

import com.futurealgos.micro.testing.dto.payload.StatusModificationPayload;
import com.futurealgos.micro.testing.dto.payload.score.NewScore;
import com.futurealgos.micro.testing.dto.payload.score.NewScoreValues;
import com.futurealgos.micro.testing.dto.payload.tests.*;
import com.futurealgos.micro.testing.dto.response.tests.TestInfo;
import com.futurealgos.micro.testing.exceptions.NotFoundException;
import com.futurealgos.micro.testing.exceptions.UnauthorizedException;
import com.futurealgos.micro.testing.models.base.*;
import com.futurealgos.micro.testing.models.embedded.Option;
import com.futurealgos.micro.testing.models.embedded.Score;
import com.futurealgos.micro.testing.models.embedded.TestCredit;
import com.futurealgos.micro.testing.repos.*;
import com.futurealgos.micro.testing.services.CategoryService;
import com.futurealgos.micro.testing.services.LanguageService;
import com.futurealgos.micro.testing.services.NormsService;
import com.futurealgos.micro.testing.utils.enums.*;
import com.futurealgos.micro.testing.utils.specs.services.UpdateSpec;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Package: com.futurealgos.micro.testing.services.tests
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class TestModificationService implements UpdateSpec<Test, EditTest, TestInfo> {

    private TestDataService testService;
    private NormsService normsService;
    private LanguageService languageService;
    private CategoryService categoryService;
    private TestDataProcessor testDataProcessor;
    private SubscaleRepository subscaleRepository;
    private QuestionRepository questionRepository;
    private DetailedInterpretationRepository detIRepository;
    private DescriptiveInterpretationRepository qualDesRepository;
    private ScoreRepository scoreRepository;
    private ScoreTableRepository tableRepository;
    private TestRepository testRepository;

    @Autowired
    public TestModificationService(TestDataService testService,
                                   NormsService normsService,
                                   LanguageService languageService,
                                   CategoryService categoryService,
                                   TestDataProcessor testDataProcessor,
                                   SubscaleRepository subscaleRepository,
                                   QuestionRepository questionRepository,
                                   DetailedInterpretationRepository detIRepository,
                                   DescriptiveInterpretationRepository desIRepository,
                                   ScoreRepository scoreRepository,
                                   ScoreTableRepository tableRepository,
                                   TestRepository testRepository) {
        this.testService = testService;
        this.normsService = normsService;
        this.languageService = languageService;
        this.categoryService = categoryService;
        this.testDataProcessor = testDataProcessor;
        this.subscaleRepository = subscaleRepository;
        this.questionRepository = questionRepository;
        this.detIRepository = detIRepository;
        this.qualDesRepository = desIRepository;
        this.scoreRepository = scoreRepository;
        this.tableRepository = tableRepository;
    }


    /**
     * @param payload the payload required to edit the test
     * @param admin   the admin who is performing the edit
     * @return Info about the test that was edited
     */
    @Override
    public TestInfo update(EditTest payload, String admin) {
        Test test = testService.fetch(payload.id());

        if (payload.block().equals(EditBlock.BASIC_DETAILS_BLOCK))
            return modifyBasicDetails(test, payload, admin);

        if (payload.block().equals(EditBlock.INTRO_DESCRIPTION_BLOCK))
            return modifyIntroDescription(test, payload, admin);

        if (payload.block().equals(EditBlock.AGE_ACCESS_LEVEL_BLOCK))
            return modifyAgeAndAccessLevel(test, payload, admin);

        if (payload.block().equals(EditBlock.TEMPLATES_BLOCK))
            return modifyTemplates(test, payload, admin);

        if (payload.block().equals(EditBlock.QUESTIONS_BLOCK))
            return modifyQuestions(test, payload, admin);

        if (payload.block().equals(EditBlock.NORMS_BLOCK))
            return modifyNorms(test, payload, admin);

        else return modifySubscale(test, payload, admin);
    }


    private TestInfo modifyBasicDetails(Test test, EditTest payload, String admin) {
        LanguageStore languageStore = languageService.fetch(payload.language());
        TestCategory category = categoryService.fetch(payload.category());

        test.setLanguage(languageStore);
        test.setTestCategory(category);
        test.setAuthor(payload.author());
        test.setAdministrationType(payload.administrationType());
        test.setTestType(payload.testType());
        test.setTotalTime(test.getTestType().equals(TimingType.TIMED) ? payload.totalTime() : null);

        return TestInfo.populate(testService.update(test, admin));
    }

    private TestInfo modifyIntroDescription(Test test, EditTest payload, String admin) {
        test.setInstructions(payload.instructions());
        test.setDescription(payload.description());
        test.setIntroduction(payload.introduction());
        test.setOverview(payload.overview());
        return TestInfo.populate(testService.update(test, admin));
    }

    private TestInfo modifyAgeAndAccessLevel(Test test, EditTest payload, String admin) {
        Integer minAge = (payload.minAgeYear() * 12) + payload.minAgeMonth();
        Integer maxAge = (payload.maxAgeYear() * 12) + payload.maxAgeMonth();
        test.setMinAge(minAge);
        test.setMaxAge(maxAge);
        test.setAccessLevel(payload.accessLevel());

        return TestInfo.populate(testService.update(test, admin));
    }

    private TestInfo modifyTemplates(Test test, EditTest payload, String admin) {
        if (payload.templates() != null) {
            List<String> defaultTemplates = new ArrayList<>(Arrays.asList("Qualitative Descriptor", "Detailed Interpretation", "Raw Score"));
            if (payload.templates().containsAll(defaultTemplates)) {
                test.setTemplates(payload.templates());
                test = testService.update(testDataProcessor.wipeScoreTables(test), admin);
                ;
                testDataProcessor.buildScoreTables(test, admin);
            } else {
                throw new UnauthorizedException("You cannot change default templates");
            }
        }
        return TestInfo.populate(testService.update(test, admin));
    }

    private TestInfo modifyNorms(Test test, EditTest payload, String admin) {
        List<Norm> norms = normsService.fetchAll(payload.norms());
        if ((payload.norms() != null) && (norms == null || (payload.norms().size() != norms.size())))
            throw new NotFoundException("Could not map one or more norms");

        test = testService.update(testDataProcessor.wipeScoreTables(test), admin);
        ;
        test.setNorms(norms);
        test = testService.update(test, admin);

        testDataProcessor.buildScoreTables(test, admin);

        return TestInfo.populate(testService.update(test, admin));
    }

    private TestInfo modifyQuestions(Test test, EditTest payload, String admin) {
        List<Question> questions = new ArrayList<>();
        if (payload.questions() == null || payload.questions().isEmpty()) {
            if (test.getQuestions() != null) questionRepository.deleteAll(test.getQuestions());
            test.setQuestions(new ArrayList<>());
            return TestInfo.populate(testService.update(test, admin));
        }

        for (NewQuestion qsPayload : payload.questions()) {
            Subscale subscale = subscaleRepository.findById(new ObjectId(qsPayload.subscale())).orElse(null);
            if (qsPayload.id() != null) {
                Question question = questionRepository.findById(new ObjectId(qsPayload.id())).orElse(null);
                Assert.notNull(question, "Question with ID " + qsPayload.id() + " not found");

                question.setDescription(qsPayload.description());
                question.setType(qsPayload.type());
                question.setLikertScoring(qsPayload.scoring());
                question.setSubscale(subscale);
                question.setIndex(qsPayload.index());

                Collections.sort(question.getOptions());
                Collections.sort(qsPayload.options());

                int len = Math.min(question.getOptions().size(), qsPayload.options().size());

                for (int i = 0; i < len; i++) {
                    question.getOptions().set(i,
                            mapOptionData(qsPayload.options().get(i), question.getOptions().get(i), i, qsPayload));
                }

                if (qsPayload.options().size() > question.getOptions().size()) {
                    for (int i = question.getOptions().size(); i < qsPayload.options().size(); i++) {
                        question.getOptions().add(mapOptionData(qsPayload.options().get(i), new Option(), i, qsPayload));
                    }
                }
                if (qsPayload.options().size() < question.getOptions().size()) {
                    question.getOptions().removeIf(o -> o.getIndex() >= len);
                }

                questionRepository.save(question);
                questions.add(question);
            } else questions.add(testDataProcessor.buildQuestion(qsPayload, new HashSet<>(), subscale, admin));
        }

        test.setQuestions(questions);

        return TestInfo.populate(testService.update(test, admin));
    }

    private Option mapOptionData(NewOption newOption, Option option, Integer i, NewQuestion qsPayload) {
        option.setDescription(newOption.description());
        Integer score = newOption.score();

        if (qsPayload.type().equals(QuestionType.LIKERT))
            score = qsPayload.scoring().isReverse() ?
                    (i + 1) : qsPayload.options().size() - i;

        option.setScore(score);
        return option;
    }

    private TestInfo modifySubscale(Test test, EditTest payload, String admin) {
        boolean toWipe = false;

        for (SubscaleRequest request : payload.subscales()) {
            if (request.opType().equals(EditType.CREATE)) {
                Subscale subscale = request.shell().test(test).build();
                subscale = subscaleRepository.save(subscale);
                test.getSubscales().add(subscale);
                toWipe = true;

            } else {
                Subscale subscale = subscaleRepository.findById(new ObjectId(request.id())).orElse(null);
                assert subscale != null;
                switch (request.opType()) {
                    case EDIT -> {
                        subscale.setDescription(request.description());
                        subscale.setType(request.timing());
                        subscale.setTotalTime(request.time());
                        subscale.setSubScaleType(request.type());
                        subscale.setInstructions(request.instructions());
                        subscaleRepository.save(subscale);
                    }
                    case DELETE -> {
                        test.getSubscales().remove(subscale);
                        subscaleRepository.delete(subscale);
                        toWipe = true;
                    }
                    case DELETE_AND_MOVE -> {
                        test.getSubscales().remove(subscale);
                        Subscale refSubscale = subscaleRepository.findById(new ObjectId(request.refSubscale())).orElse(null);
                        if (refSubscale != null) {
                            List<Question> questions = test.getQuestions().stream()
                                    .filter(q -> q.getSubscale().getName().equalsIgnoreCase(refSubscale.getName()))
                                    .map(q -> {
                                        q.setSubscale(subscale);
                                        return q;
                                    }).toList();

                            questionRepository.saveAll(questions);
                        }
                        subscaleRepository.delete(subscale);
                        toWipe = true;
                    }
                }
            }
        }

        testService.update(test, admin);

        if (toWipe) {
            testService.update(testDataProcessor.wipeScoreTables(test), admin);
            testDataProcessor.buildScoreTables(test, admin);
        }

        return TestInfo.populate(testService.update(test, admin));
    }


    public void modifyCredits(EditCredit payload, String admin) {
        Test test = testService.fetch(payload.id());
        if (test.getCredits() != null) {
            test.getCredits().setValue(payload.credits());
        } else {
            TestCredit credits = new TestCredit();
            credits.setValue(payload.credits());
            test.setCredits(credits);
        }

        testService.update(test, admin);
    }

    /**
     * Updates the status of the Test as required
     *
     * @param payload Payload containing the status and identifier of the Test
     * @param admin   ID of Administrator Modifying the status of the test
     * @throws NotFoundException if the Test is not found
     */
    public void updateTestStatus(StatusModificationPayload payload, String admin) {
        Test test = testService.fetch(payload.identifier());
        Assert.notNull(payload.status(), "Status cannot be null");

        test.setStatus(payload.status() ? TestStatus.ACTIVE : TestStatus.INACTIVE);
        testService.update(test, admin);
    }

    public ScoreTable fetchTable(String identifier) {
        return tableRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Score Table with given ID " + identifier));
    }

    public Score fetchScore(String identifier) {
        return scoreRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Score with given ID " + identifier));
    }

    public TestInfo deleteTable(String id, String admin) {
        ScoreTable scoreTable = fetchTable(id);
        Test test = scoreTable.getTest();
        test.getScoreTables().remove(scoreTable);


        scoreRepository.deleteAll(scoreTable.getScores());
        tableRepository.delete(scoreTable);

        return TestInfo.populate(testService.update(test, "admin"));
    }

    public TestInfo removeScore(String id, String admin) {
        Score score = fetchScore(id);
        ScoreTable table = score.getScoreTable();

        table.getScores().remove(score);
        scoreRepository.delete(score);
        table = tableRepository.save(table, admin);
        return TestInfo.populate(table.getTest());
    }

    public TestInfo addScore(NewScore payload, String admin) {
        ScoreTable scoreTable = fetchTable(payload.scoreTable());

        Map<String, DetailedInterpretation> detIntrMap = new HashMap<>();
        Map<String, QualitativeDescriptor> desIntrMap = new HashMap<>();

        for (NewScoreValues newScoreValues : payload.scores()) {
            Score score = payload.convert(newScoreValues);
            DetailedInterpretation detailedInterpretation;
            QualitativeDescriptor qualitativeDescriptor;

            if (desIntrMap.containsKey(newScoreValues.qualitativeDescriptor()))
                qualitativeDescriptor = desIntrMap.get(newScoreValues.qualitativeDescriptor());
            else {
                qualitativeDescriptor = fetchQualitativeDescriptor(newScoreValues.qualitativeDescriptor());
                desIntrMap.put(newScoreValues.qualitativeDescriptor(), qualitativeDescriptor);
            }

            if (detIntrMap.containsKey(newScoreValues.detailedInterpretation()))
                detailedInterpretation = detIntrMap.get(newScoreValues.detailedInterpretation());
            else {
                detailedInterpretation = fetchDetailedInterpretation(newScoreValues.detailedInterpretation());
                detIntrMap.put(newScoreValues.detailedInterpretation(), detailedInterpretation);
            }

            score.setQualitativeDescriptor(qualitativeDescriptor);
            score.setDetailedInterpretation(detailedInterpretation);
            score.setScoreTable(scoreTable);
            score = scoreRepository.save(score, admin);
            scoreTable.getScores().add(score);
            scoreTable = tableRepository.save(scoreTable);
        }

        return testService.info(payload.testId());
    }

    public DetailedInterpretation fetchDetailedInterpretation(String identifier) {
        return detIRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Detailed Interpretations with given ID " + identifier));
    }

    public QualitativeDescriptor fetchQualitativeDescriptor(String identifier) {
        return qualDesRepository.findById(new ObjectId(identifier))
                .orElseThrow(() -> new NotFoundException("Could not find Qualitative Descriptor with given ID " + identifier));
    }

    public TestInfo deleteInterpretation(String testId, String interpretation, InterpretationType type, String admin) {
        Test test = testService.fetch(testId);
        if (type.equals(InterpretationType.QUALITATIVE)) {

            QualitativeDescriptor intr = qualDesRepository.findById(new ObjectId(interpretation))
                    .orElseThrow(() -> new NotFoundException("Interpretation not found"));

            wipeScoreTable(intr, test.getScoreTables());

            test.getQualitativeDescriptors().remove(intr);
            qualDesRepository.delete(intr);


        } else {
            DetailedInterpretation intr = detIRepository.findById(new ObjectId(interpretation))
                    .orElseThrow(() -> new NotFoundException("Interpretation not found"));

            wipeScoreTable(intr, test.getScoreTables());

            test.getDetailedInterpretations().remove(intr);
            detIRepository.delete(intr);
        }

        return TestInfo.populate(testService.update(test, admin));
    }

    public void wipeScoreTable(QualitativeDescriptor intr, List<ScoreTable> tables) {
        for (ScoreTable table : tables) {
            Set<Score> scores = table.getScores();
            Set<Score> toBeDeleted = new HashSet<>();
            for (Score score : scores) {
                if (score.getQualitativeDescriptor().equals(intr)) {
                    toBeDeleted.add(score);
                }
            }

            table.getScores().removeAll(toBeDeleted);
            scoreRepository.deleteAll(toBeDeleted);
        }
    }

    public void wipeScoreTable(DetailedInterpretation intr, List<ScoreTable> tables) {
        for (ScoreTable table : tables) {
            Set<Score> scores = table.getScores();
            Set<Score> toBeDeleted = new HashSet<>();
            for (Score score : scores) {
                if (score.getDetailedInterpretation().equals(intr)) {
                    toBeDeleted.add(score);
                }
            }

            table.getScores().removeAll(toBeDeleted);
            scoreRepository.deleteAll(toBeDeleted);
        }
    }

}
