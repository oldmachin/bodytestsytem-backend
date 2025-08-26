package org.example.model.score;

import jakarta.persistence.*;
import org.example.model.user.Grade;
import org.example.model.user.Student;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 类别：实体类
 * 功能：存储学生每年对应的体侧成绩
 * */
@Entity
@Table(name = "transcripts")
public class Transcript {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String academicYear;

    private Grade testGrade;

    private Double testScore;

    private ScoreLevel testLevel;

    private String semester;

    private LocalDateTime entryTime = LocalDateTime.now();

    @OneToMany(mappedBy = "transcript", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TestResult> results;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Transcript() {}

    public Transcript(String academicYear, Grade testGrade, String semester, Student student) {
        this.academicYear = academicYear;
        this.testGrade = testGrade;
        this.semester = semester;
        this.student = student;
    }

    public ScoreLevel getTestLevel() {
        return testLevel;
    }

    public Long getId() {
        return id;
    }

    public Double getTestScore() {
        return testScore;
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

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Set<TestResult> getResults() {
        return results;
    }

    public Student getStudent() {
        return student;
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

    public void setResults(Set<TestResult> results) {
        this.results = results;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public void setTestLevel(ScoreLevel testLevel) {
        this.testLevel = testLevel;
    }
}
