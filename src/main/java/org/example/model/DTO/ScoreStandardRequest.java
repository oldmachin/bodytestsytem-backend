package org.example.model.DTO;

import org.example.model.score.ScoreLevel;
import org.example.model.user.Grade;

import java.util.List;

public record ScoreStandardRequest(
        Long testItemId,
        String gender,
        Grade grade,
        List<ScoreMappingData> scoreMappings
) {
    public record ScoreMappingData(
            ScoreLevel scoreLevel,
            int score,
            Grade grade,
            double threshold
    ) {}
}
