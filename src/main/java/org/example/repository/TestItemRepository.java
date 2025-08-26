package org.example.repository;

import org.example.model.score.TestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestItemRepository extends JpaRepository<TestItem, Long> {
    Optional<TestItem> findByName(String name);

    TestItem save(TestItem testItem);
}
