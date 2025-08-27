package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.TranscriptDTO;
import org.example.model.score.Transcript;
import org.example.model.user.Grade;
import org.example.model.user.Student;
import org.example.repository.TranscriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranscriptService {
    @Autowired
    private TranscriptRepository transcriptRepository;

    @Autowired
    private StudentService studentService;

    private Transcript getTranscriptById(Long transcriptId) {
        Transcript transcript = transcriptRepository.findById(transcriptId)
                .orElseThrow(() -> new EntityNotFoundException("Transcript not found with ID: " + transcriptId));
        return transcript;
    }

    public TranscriptDTO getTranscriptDTOById(Long transcriptId) {
        Transcript transcript = getTranscriptById(transcriptId);
        return toDTO(transcript);
    }

    private TranscriptDTO toDTO(Transcript transcript) {
        return new TranscriptDTO(
                transcript.getId(),
                transcript.getAcademicYear(),
                transcript.getTestGrade(),
                transcript.getTestScore(),
                transcript.getTestLevel(),
                transcript.getSemester()
        );
    }

    public List<TranscriptDTO> getAllTranscriptByStudentId(Long studentId) {
        return transcriptRepository.findByStudentId(studentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TranscriptDTO> getAllTranscripts() {
        return transcriptRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TranscriptDTO addTranscript(Long studentId, String transcriptAcademicYear, String transcriptSemester) {
        Student student = studentService.getStudentById(studentId);
        Transcript transcript = new Transcript(
                transcriptAcademicYear,
                student.getGrade(),
                transcriptSemester,
                student
        );
        transcript = transcriptRepository.save(transcript);
        return toDTO(transcript);
    }

    @Transactional
    public TranscriptDTO updateTranscript(Long transcriptId, Grade testGrade) {
        Transcript transcript = getTranscriptById(transcriptId);

        if (testGrade != null) {
            transcript.setTestGrade(testGrade);
        }

        transcriptRepository.save(transcript); // save方法会自动判断是新增还是更新
        return toDTO(transcript);
    }

    @Transactional
    public void deleteTranscript(Long transcriptId) {
        Transcript transcript = getTranscriptById(transcriptId);
        transcriptRepository.delete(transcript);
    }
}
