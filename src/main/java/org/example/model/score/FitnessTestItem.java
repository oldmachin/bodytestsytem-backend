package org.example.model.fitness;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class FitnessTestItem {
    private String itemName;
    private String unit;
}
