package org.example.model.score;

import jakarta.persistence.*;
import org.example.model.user.Grade;
import org.example.model.user.Student;

import java.util.Map;

@Entity
@Table(name = "transcripts")
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String academicYear;

    private Grade testGrade;

    private String semester;

    private double lungCapacityResult;

    private double run50mResult;

    private double sitAndReachResult;

    private double standingJumpResult;

    private int pullUpsResult;

    private double run1000mResult;

    private Double totalScore;

    private ScoreLevel overallLevel;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    public Transcript(Student student) {
        this.userId = student.getUserId();
    }

    public Long getId() {
        return id;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public Grade getTestGrade() {
        return testGrade;
    }

    public String getSemester() {
        return semester;
    }

    public double getLungCapacityResult() {
        return lungCapacityResult;
    }

    public double getRun50mResult() {
        return run50mResult;
    }

    public double getSitAndReachResult() {
        return sitAndReachResult;
    }

    public double getStandingJumpResult() {
        return standingJumpResult;
    }

    public int getPullUpsResult() {
        return pullUpsResult;
    }

    public double getRun1000mResult() {
        return run1000mResult;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public ScoreLevel getOverallLevel() {
        return overallLevel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public void setTestGrade(Grade testGrade) {
        this.testGrade = testGrade;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setLungCapacityResult(double lungCapacityResult) {
        this.lungCapacityResult = lungCapacityResult;
    }

    public void setRun50mResult(double run50mResult) {
        this.run50mResult = run50mResult;
    }

    public void setSitAndReachResult(double sitAndReachResult) {
        this.sitAndReachResult = sitAndReachResult;
    }

    public void setStandingJumpResult(double standingJumpResult) {
        this.standingJumpResult = standingJumpResult;
    }

    public void setPullUpsResult(int pullUpsResult) {
        this.pullUpsResult = pullUpsResult;
    }

    public void setRun1000mResult(double run1000mResult) {
        this.run1000mResult = run1000mResult;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public void setOverallLevel(ScoreLevel overallLevel) {
        this.overallLevel = overallLevel;
    }
}
