package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.DTO.TestItemDTO;
import org.example.service.TestItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-items")
public class TestItemController {
    @Autowired
    private TestItemService testItemService;

    /**
     * 根据ID获取单个体测项目详情。
     * GET /api/test-items/{testItemId}
     */
    @GetMapping("/{testItemId}")
    public ResponseEntity<TestItemDTO> getTestItemById(@PathVariable Long testItemId) {
        try {
            TestItemDTO testItemDTO = testItemService.getTestItemDTOById(testItemId);
            return ResponseEntity.ok(testItemDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取所有体测项目。
     * GET /api/test-items
     */
    @GetMapping
    public ResponseEntity<List<TestItemDTO>> getAllTestItems() {
        List<TestItemDTO> testItems = testItemService.getAllTestItem();
        return ResponseEntity.ok(testItems);
    }

    /**
     * 新增一个体测项目。
     * POST /api/test-items
     * 请求体格式: { "testItemName": "...", "testItemUnit": "...", "testItemScoreWeight": ... }
     */
    @PostMapping
    public ResponseEntity<TestItemDTO> addTestItem(@RequestBody TestItemDTO requestDTO) {
        TestItemDTO newTestItem = testItemService.addTestItem(
                requestDTO.testItemName(),
                requestDTO.testItemUnit(),
                requestDTO.testItemScoreWeight()
        );
        return new ResponseEntity<>(newTestItem, HttpStatus.CREATED);
    }

    /**
     * 更新一个已存在的体测项目。
     * PUT /api/test-items/{testItemId}
     * 请求体格式: { "testItemName": "...", "testItemUnit": "...", "testItemScoreWeight": ... }
     */
    @PutMapping("/{testItemId}")
    public ResponseEntity<TestItemDTO> updateTestItem(
            @PathVariable Long testItemId,
            @RequestBody TestItemDTO requestDTO) {
        try {
            TestItemDTO updatedDTO = new TestItemDTO(
                    testItemId,
                    requestDTO.testItemName(),
                    requestDTO.testItemUnit(),
                    requestDTO.testItemScoreWeight()
            );
            TestItemDTO updatedTestItem = testItemService.updateTestItem(updatedDTO);
            return ResponseEntity.ok(updatedTestItem);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据ID删除一个体测项目。
     * DELETE /api/test-items/{testItemId}
     */
    @DeleteMapping("/{testItemId}")
    public ResponseEntity<Void> deleteTestItem(@PathVariable Long testItemId) {
        try {
            testItemService.deleteTestItem(testItemId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}