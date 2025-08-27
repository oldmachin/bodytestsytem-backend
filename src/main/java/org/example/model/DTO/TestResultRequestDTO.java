package org.example.model.DTO;

import org.example.model.user.Grade;

public record TestResultRequestDTO(
        Long studentId,
        Long testItemId,
        String testAcademicYear,
        double score
) {
}
