package org.example.model.DTO;

import org.example.model.score.ScoreLevel;
import org.example.model.user.Grade;

public record TranscriptDTO(
        Long transcriptId,
        String transcriptAcademicYear,
        Grade transcriptGrade,
        Double transcriptScore,
        ScoreLevel transcriptLevel,
        String transcriptSemester
) {
}
