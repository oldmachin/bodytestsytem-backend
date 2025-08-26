package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.FieldDTO;
import org.example.model.Field;
import org.example.repository.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;

    public FieldDTO getFieldDTOById(Long fieldId) {
        Field field = getFieldById(fieldId);
        return toDTO(field);
    }

    public List<FieldDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FieldDTO addField(String fieldName, Field.FieldType fieldType) {
        Field field = new Field(fieldName, fieldType);
        fieldRepository.save(field);
        return toDTO(field);
    }

    @Transactional
    public void deleteField(Long fieldId) {
        Field field = getFieldById(fieldId);
        fieldRepository.delete(field);
    }

    @Transactional
    public FieldDTO updateField(Long fieldId, String fieldName, Field.FieldType fieldType) {
        Field field = getFieldById(fieldId);

        if (fieldName != null && !fieldName.isBlank()) {
            field.setName(fieldName);
        }
        if (fieldType != null) {
            field.setFieldType(fieldType);
        }

        fieldRepository.save(field);

        return toDTO(field);
    }

    private FieldDTO toDTO(Field field) {
        return new FieldDTO(
                field.getId(),
                field.getName(),
                field.getFieldType()
        );
    }

    private Field getFieldById(Long fieldId) {
        return fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + fieldId));
    }
}