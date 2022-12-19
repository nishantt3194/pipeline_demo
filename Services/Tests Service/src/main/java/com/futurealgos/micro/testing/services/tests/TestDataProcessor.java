/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.testing.services.tests;

import com.futurealgos.micro.testing.dto.payload.NormCartesianWrapper;
import com.futurealgos.micro.testing.dto.payload.STEvalWrapper;
import com.futurealgos.micro.testing.dto.payload.tests.NewOption;
import com.futurealgos.micro.testing.dto.payload.tests.NewQuestion;
import com.futurealgos.micro.testing.models.base.*;
import com.futurealgos.micro.testing.models.embedded.Option;
import com.futurealgos.micro.testing.models.embedded.QueuedObject;
import com.futurealgos.micro.testing.models.embedded.ScoreNormMap;
import com.futurealgos.micro.testing.repos.QuestionRepository;
import com.futurealgos.micro.testing.repos.ScoreRepository;
import com.futurealgos.micro.testing.repos.ScoreTableRepository;
import com.futurealgos.micro.testing.services.MetadataService;
import com.futurealgos.micro.testing.utils.enums.EvalWrapperType;
import com.futurealgos.micro.testing.utils.enums.Mode;
import com.futurealgos.micro.testing.utils.enums.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Package: com.futurealgos.micro.testing.utils
 * Project: Prasad Psycho
 * Created On: 26/07/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
@Service
public class TestDataProcessor {

    ScoreTableRepository tableRepository;
    QuestionRepository questionRepository;
    MetadataService metadataService;
    ScoreTableRepository scoreTableRepo;
    ScoreRepository scoreRepo;


    @Autowired
    public TestDataProcessor(ScoreTableRepository tableRepository,
                             QuestionRepository questionRepository,
                             MetadataService metadataService,
                             ScoreTableRepository scoreTableRepo,
                             ScoreRepository scoreRepo) {
        this.tableRepository = tableRepository;
        this.questionRepository = questionRepository;
        this.metadataService = metadataService;
        this.scoreTableRepo = scoreTableRepo;
        this.scoreRepo = scoreRepo;
    }

    public Question buildQuestion(NewQuestion qsPayload, Set<QueuedObject> queues, Subscale subscale, String admin) {
        Question.QuestionBuilder qsBuilder = qsPayload.shell();
        List<Option> options = new ArrayList<>();

        if (qsPayload.tag() != null) {
            for (QueuedObject queue : queues) {
                if (qsPayload.tag().equals(queue.getTag())) {
                    DocumentMetadata documentMetadata = metadataService.fetch(queue.getId());
                    qsBuilder.questionImage(documentMetadata);
                    queues.remove(queue);
                    break;
                }
            }
        }

        for (int i = 0; i < qsPayload.options().size(); i++) {
            options.add(buildOption(qsPayload, i, queues));
        }

        Question question = qsBuilder.options(options).subscale(subscale).build();

        return questionRepository.save(question, admin);
    }


    private Option buildOption(NewQuestion qsPayload, int index, Set<QueuedObject> queues) {
        NewOption opDto = qsPayload.options().get(index);
        Option optionData = opDto.convert();

        if (qsPayload.type().equals(QuestionType.LIKERT)) {
            Integer score = qsPayload.scoring().isReverse() ?
                    (index + 1) : qsPayload.options().size() - index;
            optionData.setScore(score);
        }

        if (opDto.tag() != null) {
            for (QueuedObject queue : queues) {
                if (opDto.tag().equals(queue.getTag())) {
                    DocumentMetadata documentMetadata = metadataService.fetch(queue.getId());
                    optionData.setImage(documentMetadata);
                    queues.remove(queue);
                    break;
                }
            }
        }

        return optionData;
    }

    public Test wipeScoreTables(Test test) {
        for (ScoreTable scoreTable : test.getScoreTables()) {
            scoreRepo.deleteAll(scoreTable.getScores());
        }
        scoreTableRepo.deleteAll(test.getScoreTables());

        test.setScoreTables(new ArrayList<>());
        return test;
    }

    public Test buildScoreTables(Test test, String admin) {
        List<ScoreTable> tables = new ArrayList<>(List.of(ScoreTable.defaultTable()));
        Subscale defaultSubscale = test.getDefaultSubscale();
        List<List<STEvalWrapper>> evaluations = new ArrayList<>();
        test.getNorms().stream()
                .map(norms -> norms.getClassification()
                        .stream().map(classification ->
                                new STEvalWrapper(classification, norms, EvalWrapperType.NORM))
                        .toList()).forEach(evaluations::add);

        if (test.getMode().equals(Mode.USER_DEFINED))
            evaluations.add(test.getSubscales().stream()
                    .map(subscale -> new STEvalWrapper(subscale.getName(), subscale, EvalWrapperType.SUBSCALE))
                    .toList());

        List<List<NormCartesianWrapper>> normEvaluations = test.getNorms().stream().map(norm -> {
            List<NormCartesianWrapper> wrappers = new ArrayList<>();
            norm.getClassification().forEach(classification -> {
                wrappers.add(new NormCartesianWrapper(classification, norm));
            });
            return wrappers;
        }).toList();

        tables.addAll(findNormsCartesian(normEvaluations));

        tables.addAll(findCartesian(evaluations));

        tables.forEach(table -> {

            table.setTest(test);
            if (table.getSubscale() == null) table.setSubscale(defaultSubscale);
            table = tableRepository.save(table, admin);
            test.getScoreTables().add(table);

        });

        return test;
    }

    private List<ScoreTable> findNormsCartesian(List<List<NormCartesianWrapper>> evaluations) {
        List<ScoreTable> cartesian = new ArrayList<>();
        int[] indexes = new int[evaluations.size()];

        for (; ; ) {
            ScoreTable table = ScoreTable.shell();
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < evaluations.size(); i++) {
                if (i != 0)
                    name.append("_");
                NormCartesianWrapper wrapper = evaluations.get(i).get(indexes[i]);
                name.append(wrapper.classification.getValue());
                table.getNormMap().add(new ScoreNormMap(wrapper.norm, wrapper.classification));
                table.getNormMap().add(new ScoreNormMap(wrapper.norm, wrapper.classification));
            }
            name.append("_Overall");
            table.setName(name.toString());
            table.setMode(Mode.DEFAULT);
            cartesian.add(table);

            for (int i = evaluations.size() - 1; ++indexes[i] == evaluations.get(i).size(); ) {
                indexes[i] = 0;
                if (--i < 0)
                    return cartesian;
            }
        }
    }


    private List<ScoreTable> findCartesian(List<List<STEvalWrapper>> tables) {
        List<ScoreTable> cartesian = new ArrayList<>();
        int[] indexes = new int[tables.size()];

        for (; ; ) {
            ScoreTable table = ScoreTable.shell();
            StringBuilder name = new StringBuilder();
            for (int i = 0; i < tables.size(); i++) {
                if (i != 0)
                    name.append("_");
                STEvalWrapper wrapper = tables.get(i).get(indexes[i]);
                name.append(wrapper.name);
                if (wrapper.type.equals(EvalWrapperType.SUBSCALE)) table.setSubscale(wrapper.subscale);
                else if (wrapper.type.equals(EvalWrapperType.NORM)) table.getNormMap()
                        .add(new ScoreNormMap(wrapper.norm, wrapper.classification));
            }
            table.setName(name.toString());
            table.setMode(Mode.USER_DEFINED);
            cartesian.add(table);

            for (int i = tables.size() - 1; ++indexes[i] == tables.get(i).size(); ) {
                indexes[i] = 0;
                if (--i < 0)
                    return cartesian;
            }
        }
    }
}
