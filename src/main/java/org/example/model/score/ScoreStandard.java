package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "score_standards")
public class ScoreStandard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_item_id", nullable = false)
    private TestItem testItem;

    @Column(nullable = false, length = 10)
    private String gender; // "男", "女"

    @Column(nullable = false)
    private Integer score; // 对应的分数

    @Column(nullable = false)
    private Double minOriginalValue; // 原始数据的最小值（例如：50米跑的最高分对应的秒数）

    @Column(nullable = false)
    private Double maxOriginalValue; // 原始数据的最大值
}
