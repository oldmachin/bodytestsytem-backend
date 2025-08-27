package org.example.model.DTO;

import org.example.model.user.Grade;

public record TranscriptRequestDTO(
        Long studentId,
        Grade testGrade,
        String testAcademicYear,
        String testSemester
) {
}
