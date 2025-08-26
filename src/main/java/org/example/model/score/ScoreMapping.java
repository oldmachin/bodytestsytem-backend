package org.example.model.score;

import jakarta.persistence.*;
import org.example.model.user.Grade;

@Entity
@Table(name = "score_mappings")
public class ScoreMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 该分数等级对应的评分等级
    @Enumerated(EnumType.STRING)
    private ScoreLevel scoreLevel;

    // 该分数等级对应的得分
    private int score;

    // 该分数等级对应的应用年级
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    // 达到该分数等级所需的原始成绩阈值
    private double threshold;

    // 该分数等级对应的项目标准
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_standard_id", nullable = false)
    private ScoreStandard scoreStandard;

    public ScoreMapping() {}

    public ScoreMapping(ScoreLevel scoreLevel, int score, Grade grade, double threshold, ScoreStandard scoreStandard) {
        this.scoreLevel = scoreLevel;
        this.score = score;
        this.grade = grade;
        this.threshold = threshold;
        this.scoreStandard = scoreStandard;
    }

    public Grade getGrade() {
        return grade;
    }

    public double getThreshold() {
        return threshold;
    }

    public ScoreLevel getScoreLevel() {
        return scoreLevel;
    }

    public int getScore() {
        return score;
    }

    public ScoreStandard getScoreStandard() {
        return scoreStandard;
    }

    public void setScoreLevel(ScoreLevel scoreLevel) {
        this.scoreLevel = scoreLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void setScoreStandard(ScoreStandard scoreStandard) {
        this.scoreStandard = scoreStandard;
    }
}
