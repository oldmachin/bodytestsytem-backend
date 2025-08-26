package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.ScoreStandardRequest;
import org.example.model.score.ScoreMapping;
import org.example.model.score.ScoreStandard;
import org.example.model.score.TestItem;
import org.example.model.user.Grade;
import org.example.repository.ScoreMappingRepository;
import org.example.repository.ScoreStandardRepository;
import org.example.repository.TestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreStandardService {
    @Autowired
    private ScoreStandardRepository scoreStandardRepository;

    @Autowired
    private ScoreMappingRepository scoreMappingRepository;

    @Autowired
    private TestItemRepository testItemRepository;

    public ScoreStandard getScoreStandardById(Long scoreStandardId) {
        return scoreStandardRepository.findById(scoreStandardId)
                .orElseThrow(() -> new EntityNotFoundException("ScoreStandard not found with ID: " + scoreStandardId));
    }

    @Transactional
    public ScoreStandard createScoreStandard(Long testItemId, String gender, Grade grade, List<ScoreStandardRequest.ScoreMappingData> mappingsData) {
        TestItem testItem = testItemRepository.findById(testItemId)
                .orElseThrow(() -> new EntityNotFoundException("TestItem not found with ID: " + testItemId));

        ScoreStandard newScoreStandard = new ScoreStandard(testItem, grade, gender);
        scoreStandardRepository.save(newScoreStandard);

        List<ScoreMapping> newScoreMappings = new ArrayList<>();
        for (ScoreStandardRequest.ScoreMappingData data : mappingsData) {
            ScoreMapping newScoreMapping = new ScoreMapping(data.scoreLevel(), data.score(), data.grade(), data.threshold(), newScoreStandard);
            newScoreMappings.add(newScoreMapping);
        }

        scoreMappingRepository.saveAll(newScoreMappings);

        return newScoreStandard;
    }

    @Transactional
    public ScoreStandard updateScoreStandard(Long scoreStandardId, String gender, Grade grade, List<ScoreStandardRequest.ScoreMappingData> mappingData) {
        ScoreStandard scoreStandard = scoreStandardRepository.findById(scoreStandardId)
                .orElseThrow(() -> new EntityNotFoundException("ScoreStandard not found with ID: " + scoreStandardId));
        scoreStandard.setGender(gender);
        scoreStandard.setGrade(grade);

        return scoreStandardRepository.save(scoreStandard);
    }

    @Transactional
    public void deleteScoreStandard(Long standardId) {
        ScoreStandard standard = scoreStandardRepository.findById(standardId)
                .orElseThrow(() -> new EntityNotFoundException("ScoreStandard not found with ID: " + standardId));

        scoreStandardRepository.delete(standard);
    }
}
