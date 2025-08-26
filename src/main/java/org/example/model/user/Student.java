package org.example.model.user;

import jakarta.persistence.*;

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

    private Long ethnicCode;

    private String grade;

    private String major;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Student(Long id, String studentId, String idCardNumber, String name, String gender, Long ethnicCode, String grade, String major, User user) {
        this.id = id;
        this.studentId = studentId;
        this.idCardNumber = idCardNumber;
        this.name = name;
        this.gender = gender;
        this.ethnicCode = ethnicCode;
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

    public Long getEthnicCode() {
        return ethnicCode;
    }

    public String getGrade() {
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

    public void setEthnicCode(Long ethnicCode) {
        this.ethnicCode = ethnicCode;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
