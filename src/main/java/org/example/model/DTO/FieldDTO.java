package org.example.model.DTO;

import org.example.model.Field;

public record FieldDTO(
        Long fieldId,
        String fieldName,
        Field.FieldType fieldType
) {
}
