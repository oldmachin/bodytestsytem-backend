package org.example.model.entity.department;

import jakarta.persistence.*;

import java.time.Year;
import java.util.List;

@Entity
@Table(name = "majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String majorCode;

    @Column(nullable = false, unique = true)
    private String majorName;

    @Column(nullable = false)
    private String academicField;

    @Column(nullable = false)
    private String majorCategory;

    @Column(nullable = false)
    private String degreeCategory;

    @Column(nullable = false)
    private Year approveYear;

    @Column(nullable = false)
    private boolean enrollmentStatus;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Class> classList;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false)
    private School school;

    public Major() {

    }

    public Major(String majorCode, String majorName, String majorCategory, String academicField, String degreeCategory, Year approveYear, boolean enrollmentStatus, School school) {
        this.majorCode = majorCode;
        this.majorName = majorName;
        this.majorCategory = majorCategory;
        this.academicField = academicField;
        this.degreeCategory = degreeCategory;
        this.approveYear = approveYear;
        this.enrollmentStatus = enrollmentStatus;
        this.school = school;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public Long getId() {
        return id;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public String getAcademicField() {
        return academicField;
    }

    public String getMajorCategory() {
        return majorCategory;
    }

    public String getDegreeCategory() {
        return degreeCategory;
    }

    public Year getApproveYear() {
        return approveYear;
    }

    public boolean isEnrollmentStatus() {
        return enrollmentStatus;
    }

    public School getSchool() {
        return school;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public void setAcademicField(String academicField) {
        this.academicField = academicField;
    }

    public void setMajorCategory(String majorCategory) {
        this.majorCategory = majorCategory;
    }

    public void setDegreeCategory(String degreeCategory) {
        this.degreeCategory = degreeCategory;
    }

    public void setApproveYear(Year approveYear) {
        this.approveYear = approveYear;
    }

    public void setEnrollmentStatus(boolean enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
