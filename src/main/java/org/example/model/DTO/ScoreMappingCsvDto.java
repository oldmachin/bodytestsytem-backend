package org.example.model.DTO;

import org.example.model.score.ScoreLevel;
import org.example.model.user.Grade;

public record ScoreMappingCsvDto(
        ScoreLevel scoreLevel,
        int score,
        String testItemName,
        String gender,
        Grade grade,
        double threshold
        ) {
}
