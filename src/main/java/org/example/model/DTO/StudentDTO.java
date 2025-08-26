package org.example.model.DTO;

import org.example.model.entity.department.Major;
import org.example.model.user.Grade;

public record StudentDTO(
        Long id,
        String studentId,
        String studentName,
        String idCardNumber,
        String gender,
        Long ethnicCode,
        Grade grade,
        String major
) {
}
