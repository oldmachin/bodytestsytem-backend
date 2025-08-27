package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.TestResultDTO;
import org.example.model.DTO.TestResultRequestDTO;
import org.example.model.score.TestItem;
import org.example.model.score.TestResult;
import org.example.model.score.Transcript;
import org.example.repository.TestItemRepository;
import org.example.repository.TestResultRepository;
import org.example.repository.TranscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private TestItemRepository testItemRepository;

    private TestResult getTestResultById(Long testResultId) {
        TestResult testResult = testResultRepository.findById(testResultId)
                .orElseThrow(() -> new EntityNotFoundException("TestResult not found with ID: " + testResultId));
        return testResult;
    }

    private TestResultDTO toDTO(TestResult testResult) {
        return new TestResultDTO(
                testResult.getId(),
                testResult.getTranscript().getId(),
                testResult.getTestItem().getId(),
                testResult.getScore()
        );
    }

    @Transactional
    public TestResultDTO createTestResult(TestResultRequestDTO request) {
        Transcript transcript = transcriptRepository.findByStudentIdAndAcademicYear(request.studentId(), request.testAcademicYear())
                .orElseThrow(() -> new EntityNotFoundException("Transcript not found with student ID: " + request.studentId() + " and academic year: " + request.testAcademicYear()));
        TestItem testItem = testItemRepository.findById(request.testItemId())
                .orElseThrow(() -> new EntityNotFoundException("TestItem not found with Id: " + request.testItemId()));
        TestResult testResult = new TestResult(transcript, testItem, request.score());
        testResultRepository.save(testResult);
        return toDTO(testResult);
    }

    @Transactional
    public TestResultDTO updateTestResult(TestResultDTO request) {
        TestResult testResult = getTestResultById(request.testResultId());
        TestItem testItem = testItemRepository.findById(request.testItemId())
                .orElseThrow(() -> new EntityNotFoundException("TestItem not found with Id: " + request.testItemId()));
        if (testItem != testResult.getTestItem()) {
            testResult.setTestItem(testItem);
        }
        testResult.setScore(request.testResultScore());
        testResultRepository.save(testResult);
        return toDTO(testResult);
    }

    @Transactional
    public void deleteTestResult(Long testResultId) {
        if (!testResultRepository.existsById(testResultId)) {
            throw new EntityNotFoundException("TestResult not found with ID: " + testResultId);
        }
        testResultRepository.deleteById(testResultId);
    }
}
