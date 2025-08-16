package org.example.model.test;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "test_items")
public class TestItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 20)
    private String unit;

    @Column(length = 20)
    private String category;
}
