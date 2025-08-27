package org.example.model.DTO;

public record TestItemDTO(
        Long testItemId,
        String testItemName,
        String testItemUnit,
        Double testItemScoreWeight
) {
}
