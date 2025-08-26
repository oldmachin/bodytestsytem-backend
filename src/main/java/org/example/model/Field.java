package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fields")
public class Field {
    public enum FieldType {
        INDOOR,
        OUTDOOR
    }

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    FieldType fieldType;

    public Field() {}

    public Field(String name, FieldType fieldType) {
        this.name = name;
        this.fieldType = fieldType;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
