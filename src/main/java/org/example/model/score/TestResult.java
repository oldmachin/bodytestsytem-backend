package org.example.model.score;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 类别：实体类
 * 功能：用于存储成绩单中对应个项目的成绩
 */
@Entity
@Table(name = "test_results")
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transcript_id", nullable = false)
    private Transcript transcript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_item_id", nullable = false)
    private TestItem testItem;

    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public TestResult() {

    }

    public TestResult(Transcript transcript, TestItem testItem, double score) {
        this.transcript = transcript;
        this.testItem = testItem;
        this.score = score;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public double getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
