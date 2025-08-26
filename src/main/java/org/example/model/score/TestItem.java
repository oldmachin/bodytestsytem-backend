package org.example.model.score;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Set;

/**
 * 类别：实体类
 * 功能：定义具体的体侧项目，如：50m跑、引体向上、肺活量等
 * */
@Entity
@Table(name = "test_items")
public class TestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String unit;

    private Double scoreWeight;

    @OneToMany(mappedBy = "testItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestResult> results;

    @OneToMany(mappedBy = "testItem", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<ScoreStandard> scoreStandards;

    public TestItem() {

    }

    public TestItem(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public Double getScoreWeight() {
        return scoreWeight;
    }

    public String getUnit() {
        return unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setScoreWeight(Double scoreWeight) {
        this.scoreWeight = scoreWeight;
    }
}
