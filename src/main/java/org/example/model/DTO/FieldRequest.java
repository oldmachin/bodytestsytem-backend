package org.example.model.DTO;

import org.example.model.Field;

public record FieldRequest(
        String fieldName,
        Field.FieldType fieldType
) {
}
