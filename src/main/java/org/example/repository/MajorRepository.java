package org.example.repository;

import org.example.model.entity.department.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByMajorName(String majorName);

    Optional<Major> findByMajorCode(String MajorCode);

    List<Major> findBySchoolId(Long schoolId);

    boolean existsByMajorName(String majorName);

    boolean existsByMajorCode(String majorCode);

    void deleteById(Long id);
}
