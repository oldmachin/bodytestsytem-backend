package org.example.model.DTO;

import org.example.model.entity.department.School;

import java.time.Year;
import java.util.List;

public record MajorSplitRequestDTO(
        Long originalMajorId,
        List<newMajorsInfo> newMajors
)  {
    public record newMajorsInfo(
            String newMajorCode,
            String newMajorName,
            String newMajorCategory,
            String newMajorAcademicField,
            String newMajorDegreeCategory,
            Year newMajorApproeYear,
            boolean newMajorEnrollmentStatus,
            Long newMajorSchoolId,
            List<Long> classIds
    ){

    }
}
