package org.example.model.score;

public class ScoreMapping {

    private ScoreLevel scoreLevel;

    private int score;

    private double grade_1_2;

    private double grade_3_4;

    public ScoreMapping(ScoreLevel scoreLevel, int score, double grade_1_2, double grade_3_4) {
        this.scoreLevel = scoreLevel;
        this.score = score;
        this.grade_1_2 = grade_1_2;
        this.grade_3_4 = grade_3_4;
    }

    public double getGrade_1_2() {
        return grade_1_2;
    }

    public double getGrade_3_4() {
        return grade_3_4;
    }

    @Override
    public String toString() {
        return "ScoreMapping{" +
                "level=" + scoreLevel +
                ", score=" + score +
                ", grade_1_2=" + grade_1_2 +
                ", grade_3_4=" + grade_3_4 +
                '}';
    }
}
