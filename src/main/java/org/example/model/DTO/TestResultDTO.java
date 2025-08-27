package org.example.model.DTO;

public record TestResultDTO(
        Long testResultId,
        Long transcriptId,
        Long testItemId,
        double testResultScore
) {
}
