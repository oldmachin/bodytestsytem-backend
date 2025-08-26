package org.example.model.DTO;

import java.util.List;

public record SchoolSplitRequestDTO(
        Long originalScoolId,
        List<NewSchoolInfo> newSchools
) {
    public record NewSchoolInfo(
            String newSchoolName,
            List<Long> majorIds
    ) {

    }
}
