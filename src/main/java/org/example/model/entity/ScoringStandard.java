package org.example.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ScoringStandard {
    @Id
    @GeneratedValue
    private Long id;

    private String gender;
    private String event;
    private String score;
    private String level;
    private String gradeRange;
    private Long data;
}
