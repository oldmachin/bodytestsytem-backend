package org.example.repository;

import org.example.model.score.ScoreStandard;
import org.example.model.score.TestItem;
import org.example.model.user.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreStandardRepository extends JpaRepository<ScoreStandard, Long> {
    Optional<ScoreStandard> findByTestItemAndGenderAndGrade(TestItem testItem, String gender, Grade grade);
}
