package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.TestItemDTO;
import org.example.model.score.TestItem;
import org.example.repository.TestItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestItemService {
    @Autowired
    private TestItemRepository testItemRepository;

    private TestItem getTestItemById(Long testItemId) {
        return testItemRepository.findById(testItemId)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + testItemId));
    }
    private TestItemDTO toDTO(TestItem testItem) {
        return new TestItemDTO(
                testItem.getId(),
                testItem.getName(),
                testItem.getUnit(),
                testItem.getScoreWeight()
        );
    }

    public TestItemDTO getTestItemDTOById(Long testItemId) {
        TestItem testItem = getTestItemById(testItemId);
        return toDTO(testItem);
    }

    public List<TestItemDTO> getAllTestItem() {
        return testItemRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TestItemDTO addTestItem(String testItemName, String testItemUnit, Double testItemScoreWeight) {
        TestItem testItem = new TestItem(testItemName, testItemUnit);
        testItemRepository.save(testItem);
        if (testItemScoreWeight!= null) {
            testItem.setScoreWeight(testItemScoreWeight);
        }
        return toDTO(testItem);
    }

    @Transactional
    public void deleteTestItem(Long testItemId) {
        TestItem testItem = getTestItemById(testItemId);
        testItemRepository.delete(testItem);
    }

    @Transactional
    public TestItemDTO updateTestItem(TestItemDTO testItemDTO) {
        TestItem testItem = getTestItemById(testItemDTO.testItemId());

        if (testItemDTO.testItemName() != null && !testItemDTO.testItemName().isBlank()) {
            testItem.setName(testItemDTO.testItemName());
        }
        if (testItemDTO.testItemUnit() != null && !testItemDTO.testItemUnit().isBlank()) {
            testItem.setUnit(testItemDTO.testItemUnit());
        }
        if (testItemDTO.testItemScoreWeight() != null) {
            testItem.setScoreWeight(testItemDTO.testItemScoreWeight());
        }
        testItemRepository.save(testItem);
        return toDTO(testItem);
    }
}
