package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.DTO.TranscriptDTO;
import org.example.model.DTO.TranscriptRequestDTO;
import org.example.service.TranscriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transcripts")
public class TranscriptController {
    @Autowired
    private TranscriptService transcriptService;

    /**
     * 根据 ID 获取单个成绩单详情。
     * GET /api/transcripts/{transcriptId}
     */
    @GetMapping("/{transcriptId}")
    public ResponseEntity<TranscriptDTO> getTranscriptById(@PathVariable Long transcriptId) {
        try {
            TranscriptDTO transcriptDTO = transcriptService.getTranscriptDTOById(transcriptId);
            return ResponseEntity.ok(transcriptDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取所有成绩单。
     * GET /api/transcripts
     */
    @GetMapping
    public ResponseEntity<List<TranscriptDTO>> getAllTranscripts() {
        List<TranscriptDTO> transcripts = transcriptService.getAllTranscripts();
        return ResponseEntity.ok(transcripts);
    }

    /**
     * 根据学生 ID 获取所有成绩单。
     * GET /api/transcripts/student/{studentId}
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<TranscriptDTO>> getAllTranscriptsByStudentId(@PathVariable Long studentId) {
        List<TranscriptDTO> transcripts = transcriptService.getAllTranscriptByStudentId(studentId);
        return ResponseEntity.ok(transcripts);
    }

    /**
     * 新增一个成绩单。
     * POST /api/transcripts
     * 请求体格式: { "studentId": ..., "academicYear": "...", "semester": "..." }
     */
    @PostMapping
    public ResponseEntity<TranscriptDTO> addTranscript(@RequestBody TranscriptRequestDTO requestDTO) {
        TranscriptDTO newTranscript = transcriptService.addTranscript(
                requestDTO.studentId(),
                requestDTO.testAcademicYear(),
                requestDTO.testSemester()
        );
        return new ResponseEntity<>(newTranscript, HttpStatus.CREATED);
    }

    /**
     * 更新一个已存在的成绩单。
     * PUT /api/transcripts/{transcriptId}
     * 请求体格式: { "testGrade": "..." }
     */
    @PutMapping("/{transcriptId}")
    public ResponseEntity<TranscriptDTO> updateTranscript(
            @PathVariable Long transcriptId,
            @RequestBody TranscriptRequestDTO request) {
        try {
            TranscriptDTO updatedTranscript = transcriptService.updateTranscript(
                    transcriptId,
                    request.testGrade()
            );
            return ResponseEntity.ok(updatedTranscript);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据 ID 删除一个成绩单。
     * DELETE /api/transcripts/{transcriptId}
     */
    @DeleteMapping("/{transcriptId}")
    public ResponseEntity<Void> deleteTranscript(@PathVariable Long transcriptId) {
        try {
            transcriptService.deleteTranscript(transcriptId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}