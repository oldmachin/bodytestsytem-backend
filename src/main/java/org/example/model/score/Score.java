package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(nullable = false)
    private Double originalValue;

    @Column(nullable = false)
    private Integer convertedScore;

    @Column(nullable = false)
    private LocalDate testDate;

    @Column(nullable = false)
    private Integer testYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "testitemId", nullable = false)
    private TestItem testItem;
}
