package org.example.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.model.user.Student;

import java.util.List;

@Data
@Entity
@Table(name = "classes")
public class ClassInfo { // 使用 ClassInfo 避免与 Java 关键字 Class 冲突
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentInfo department;

    @OneToMany(mappedBy = "classInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;
}