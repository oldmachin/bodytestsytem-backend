package org.example.repository;

import org.example.model.score.ScoreMapping;
import org.example.model.score.ScoreStandard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreMappingRepository extends JpaRepository<ScoreMapping, Long> {
    Optional<ScoreMapping> findByScoreStandardAndThresholdLessThanEqual(ScoreStandard standard, Double rawScore);
}
