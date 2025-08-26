package org.example.repository;

import org.example.model.entity.department.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<org.example.model.entity.department.Class, Long> {
    List<Class> findByMajorId(Long majorId);
}
