package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.score.*;
import org.example.model.user.Grade;
import org.example.model.user.Student;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestScoreService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestItemRepository testItemRepository;

    @Autowired
    private TestResultRepository testResultRepository;

    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private ScoreStandardRepository scoreStandardRepository;

    @Autowired
    private ScoreMappingRepository scoreMappingRepository;

    /**
     * 创建一份新的体测成绩单总览
     * @param studentId     学生ID
     * @param testGrade     学生测试年级
     * @param academicYear  学年
     * @param semester      学期
     * @return 创建的成绩单实体
     */
    @Transactional
    public Transcript createTranscript(Long studentId, Grade testGrade, String academicYear, String semester) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with Id: " + studentId));
        Transcript transcript = new Transcript(academicYear, testGrade, semester, student);
        return transcriptRepository.save(transcript);
    }

    /**
     * 创建一份新的体测成绩单总览
     * @param transcriptId  成绩单ID
     * @param testItemName  测试项目的名字
     * @param score         录入的测试项目对应的成绩
     * @return 对应的测试结果的实体
     */
    @Transactional
    public TestResult saveOrUpdateTestResult(Long transcriptId, String testItemName, double score) {
        Transcript transcript = transcriptRepository.findById(transcriptId)
                .orElseThrow(() -> new EntityNotFoundException("Transcript not found with Id: " + transcriptId));

        TestItem testItem = testItemRepository.findByName(testItemName)
                .orElseThrow(() -> new EntityNotFoundException("TestItem not found with Name: " + testItemName));

        Optional<TestResult> existingResult = testResultRepository.findByTranscriptIdAndTestItemId(transcript.getId(), testItem.getId());

        TestResult result;

        if (existingResult.isPresent()) {
            result = existingResult.get();
            result.setScore(score);
        } else {
            result = new TestResult(transcript, testItem, score);
        }

        return testResultRepository.save(result);
    }

    /**
     * @param studentId 学生ID
     * @return 对应学生所有的测试成绩单
     * */
    public List<Transcript> getTranscriptByStudentId(Long studentId) {
        return transcriptRepository.findByStudentId(studentId);
    }

    /**
     * 根据测试结果的原始分数，获取对应的转换分值。
     *
     * @param result 学生的测试结果实体
     * @return 转换后的分值，如果没有找到则返回0.0
     */
    public Double getConvertedScore(TestResult result) {
        String studentGender = result.getTranscript().getStudent().getGender();
        Grade studentGrade = result.getTranscript().getTestGrade();
        TestItem testItem = result.getTestItem();
        double rawScore = result.getScore();

        Optional<ScoreStandard> standardOptional = scoreStandardRepository
                .findByTestItemAndGenderAndGrade(testItem, studentGender, studentGrade);

        if (!standardOptional.isPresent()) {
            return 0.0;
        }

        ScoreStandard standard = standardOptional.get();

        Optional<ScoreMapping> mappingOptional = scoreMappingRepository
                .findByScoreStandardAndThresholdLessThanEqual(standard, rawScore);

        if (!mappingOptional.isPresent()) {
            return 0.0;
        }

        return Double.valueOf(mappingOptional.get().getScore());
    }

    @Transactional
    public double totalScore(Student student, Transcript transcript) {
        double totalScore = 0.0;
        for (TestResult result : testResultRepository.findByTranscriptId(transcript.getId())) {
            TestItem item = result.getTestItem();
            totalScore += getConvertedScore(result) * item.getScoreWeight();
        }
        transcript.setTestScore(totalScore);
        transcript.setTestLevel(ScoreLevel.judgeLevel(totalScore));
        return totalScore;
    }
}
