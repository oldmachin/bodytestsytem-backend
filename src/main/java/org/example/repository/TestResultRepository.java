package org.example.repository;

import org.example.model.score.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    TestResult save(TestResult testResult);

    List<TestResult> findByTranscriptId(Long transcriptId);

    Optional<TestResult> findByTranscriptIdAndTestItemId(Long transcriptId, Long testItemId);
}
