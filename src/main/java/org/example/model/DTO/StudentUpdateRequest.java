package org.example.model.DTO;

import org.example.model.user.Student;

public record StudentUpdateRequest(
        String name,
        String major,
        Student studentClass
) {
}
