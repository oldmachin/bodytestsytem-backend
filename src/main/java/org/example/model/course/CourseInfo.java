package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "courses")
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false, length = 50)
    private String teacher;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable =false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 50)
    private Address address;
}
