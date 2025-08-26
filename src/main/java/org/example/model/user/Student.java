package org.example.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.example.model.entity.department.Class;
import org.example.model.score.Transcript;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String studentId;

    private String idCardNumber;

    private String name;

    private String gender;

    private String ethnicGroup;

    private Grade grade;

    private String major;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class studentClass;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transcript> transcripts;

    public Student() {}

    public Student(String studentId, String idCardNumber, String name, String gender, String ethnicGroup, Grade grade, String major, User user) {
        this.studentId = studentId;
        this.idCardNumber = idCardNumber;
        this.name = name;
        this.gender = gender;
        this.ethnicGroup = ethnicGroup;
        this.grade = grade;
        this.major = major;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public User getUser() { return user; }

    public Grade getGrade() {
        return grade;
    }

    public String getMajor() {
        return major;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEthnicGroups(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStudentClass(Class studentClass) {
        this.studentClass = studentClass;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
