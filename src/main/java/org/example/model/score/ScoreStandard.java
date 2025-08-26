package org.example.model.score;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.example.model.user.Grade;

import java.util.Set;

@Entity
@Table(name = "score_standards")
public class ScoreStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_item_id", nullable = false)
    @JsonBackReference
    private TestItem testItem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private String gender;

    @OneToMany(mappedBy = "scoreStandard", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ScoreMapping> mappings;

    public ScoreStandard() {

    }

    public ScoreStandard(TestItem testItem, Grade grade, String gender) {
        this.testItem = testItem;
        this.grade = grade;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getGender() {
        return gender;
    }

    public Set<ScoreMapping> getMappings() {
        return mappings;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMappings(Set<ScoreMapping> mappings) {
        this.mappings = mappings;
    }
}
