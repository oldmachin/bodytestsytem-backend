package org.example.model.fitness;

import lombok.Data;

@Data
public class FitnessScoreRule {
    private FitnessTestItem testItem;
    private String gender;
    private Double minThreshold;
    private Double maxThreshold;
    private Integer score;
    private String rating;
    private String gradeGroup;
}
