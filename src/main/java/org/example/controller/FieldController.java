package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.DTO.FieldDTO;
import org.example.model.DTO.FieldRequest;
import org.example.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @GetMapping
    public ResponseEntity<List<FieldDTO>> getAllFields() {
        List<FieldDTO> fields = fieldService.getAllFields();
        return ResponseEntity.ok(fields);
    }

    @PostMapping
    private ResponseEntity<FieldDTO> addField(@RequestBody FieldRequest request) {
        FieldDTO fieldDTO = fieldService.addField(request.fieldName(), request.fieldType());
        return new ResponseEntity<>(fieldDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{fieldId}")
    public ResponseEntity<FieldDTO> getFieldById(@PathVariable Long fieldId) {
        try {
            FieldDTO field = fieldService.getFieldDTOById(fieldId);
            return ResponseEntity.ok(field);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{fieldId}")
    public ResponseEntity<FieldDTO> updateField(
            @PathVariable Long fieldId,
            @RequestBody FieldRequest request) {
        try {
            FieldDTO updatedField = fieldService.updateField(
                    fieldId,
                    request.fieldName(),
                    request.fieldType()
            );
            return ResponseEntity.ok(updatedField);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{fieldId}")
    public ResponseEntity<Void> deleteField(@PathVariable Long fieldId) {
        try {
            fieldService.deleteField(fieldId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
