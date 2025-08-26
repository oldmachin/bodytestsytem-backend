package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.MajorSplitRequestDTO;
import org.example.model.entity.department.Class;
import org.example.model.entity.department.Major;
import org.example.repository.ClassRepository;
import org.example.repository.MajorRepository;
import org.example.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public List<Major> findAllMajors() {
        return majorRepository.findAll();
    }

    public Major findMajorById(Long id) {
        return majorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Major id doesn't exist."));
    }

    @Transactional
    public Major saveMajor(Major major) {
        return majorRepository.save(major);
    }

    @Transactional
    public void deleteMajorById(Long id) {
        majorRepository.deleteById(id);
    }

//    @Transactional
//    public Major mergeMajor(Major newMajor, List<Long> classIdToMerge) {
//        Major newMajor = new Major(newMajorName);
//        newSchool = schoolRepository.save(newSchool);
//
//        List<School> schoolToMerge = schoolRepository.findAllById(schoolIdToMerge);
//        for (School school : schoolToMerge) {
//            List<Major> majorsToUpdate = majorRepository.findBySchoolId(school.getId());
//            for (Major major : majorsToUpdate) {
//                major.setSchool(newSchool);
//                majorRepository.save(major);
//            }
//        }
//
//        schoolRepository.deleteAllById(schoolIdToMerge);
//        return newSchool;
//    }

    @Transactional
    public void splitMajor(Long originalMajorId, List<MajorSplitRequestDTO.newMajorsInfo> newMajorsInfo) {
        Major originalMajor = majorRepository.findById(originalMajorId)
                .orElseThrow(() -> new EntityNotFoundException("Original major id doesn't exists."));

        List<Class> allOriginalClasses = classRepository.findByMajorId(originalMajorId);
        Set<Long> processedClassIds = new HashSet<>();

        for (MajorSplitRequestDTO.newMajorsInfo newMajorInfo : newMajorsInfo) {
            Major newMajor = majorRepository.save(new Major(newMajorInfo.newMajorCode(), newMajorInfo.newMajorName(), newMajorInfo.newMajorCategory(), newMajorInfo.newMajorAcademicField(), newMajorInfo.newMajorDegreeCategory(), newMajorInfo.newMajorApproeYear(), newMajorInfo.newMajorEnrollmentStatus(), schoolRepository.getReferenceById(newMajorInfo.newMajorSchoolId())));

            List<Class> classesToAssign = classRepository.findAllById(newMajorInfo.classIds());
            for (Class clazz : classesToAssign) {
                clazz.setMajor(newMajor);
                classRepository.save(clazz);
                processedClassIds.add(clazz.getId());
            }
        }

        List<Class> unassignedClasses = allOriginalClasses.stream()
                .filter(clazz -> !processedClassIds.contains(clazz.getId()))
                .toList();

        for (Class clazz : unassignedClasses) {
            classRepository.delete(clazz);
        }

        originalMajor.getClassList().clear();
        majorRepository.save(originalMajor);
        majorRepository.delete(originalMajor);
    }

    @Transactional
    public Major updateMajor(Long id, Major updatedMajor) {
        Major existingMajor = majorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Major not found with ID: " + id));

        existingMajor.setMajorCode(updatedMajor.getMajorCode());
        existingMajor.setMajorName(updatedMajor.getMajorName());
        existingMajor.setAcademicField(updatedMajor.getAcademicField());
        existingMajor.setDegreeCategory(updatedMajor.getDegreeCategory());
        existingMajor.setEnrollmentStatus(updatedMajor.isEnrollmentStatus());
        existingMajor.setApproveYear(updatedMajor.getApproveYear());
        existingMajor.setSchool(updatedMajor.getSchool());

        return majorRepository.save(existingMajor);
    }
}
