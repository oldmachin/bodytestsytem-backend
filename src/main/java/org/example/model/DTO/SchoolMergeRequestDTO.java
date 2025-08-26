package org.example.model.DTO;

import java.util.List;

public record SchoolMergeRequestDTO(
        String newSchoolName,
        List<Long> schoolIdsToMerge
) {

}
