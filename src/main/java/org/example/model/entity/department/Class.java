package org.example.model.entity.department;

import jakarta.persistence.*;
import org.example.model.user.Grade;

@Entity
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int studentCount;

    @Column(nullable = false)
    private Grade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

    public Class() {

    }

    public Class(String name, int studentCount, Grade grade, Major major) {
        this.name = name;
        this.studentCount = studentCount;
        this.grade = grade;
        this.major = major;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public Grade getGrade() {
        return grade;
    }

    public Major getMajor() {
        return major;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
