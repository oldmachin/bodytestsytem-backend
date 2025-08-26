package org.example.repository;

import org.example.model.score.Transcript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {
    /**
     * 根据待查询学生的学号和学年查询成绩单
     * @param studentId 待查询学生学号, e.g. "202205560001"
     * @param academicYear 待查询的学年，e.g. "2023-2024"
     * @return 匹配的成绩单
     */
    Optional<Transcript> findByStudentIdAndAcademicYear(Long studentId, String academicYear);

    /**
     * 根据待查询学生的学号查询所有的成绩单
     * @param studentId 待查询学生的学号
     * @return 匹配的成绩单列表
     */
    List<Transcript> findByStudentId(Long studentId);
}
