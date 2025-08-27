package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.DTO.TestResultDTO;
import org.example.model.DTO.TestResultRequestDTO;
import org.example.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-results")
public class TestResultController {
    @Autowired
    private TestResultService testResultService;

    /**
     * 新增一个体测项目成绩。
     * POST /api/test-results
     * 请求体格式: { "studentId": ..., "testAcademicYear": "...", "testItemId": ..., "score": ... }
     */
    @PostMapping
    public ResponseEntity<TestResultDTO> createTestResult(@RequestBody TestResultRequestDTO requestDTO) {
        try {
            TestResultDTO newResult = testResultService.createTestResult(requestDTO);
            return new ResponseEntity<>(newResult, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 更新一个已存在的体测项目成绩。
     * PUT /api/test-results
     * 请求体格式: { "testResultId": ..., "testItemId": ..., "testResultScore": ... }
     */
    @PutMapping
    public ResponseEntity<TestResultDTO> updateTestResult(@RequestBody TestResultDTO requestDTO) {
        try {
            TestResultDTO updatedResult = testResultService.updateTestResult(requestDTO);
            return ResponseEntity.ok(updatedResult);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 根据 ID 删除一个体测项目成绩。
     * DELETE /api/test-results/{testResultId}
     */
    @DeleteMapping("/{testResultId}")
    public ResponseEntity<Void> deleteTestResult(@PathVariable Long testResultId) {
        try {
            testResultService.deleteTestResult(testResultId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}