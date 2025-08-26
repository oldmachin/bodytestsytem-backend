package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.SchoolSplitRequestDTO;
import org.example.model.entity.department.Major;
import org.example.model.entity.department.School;
import org.example.repository.MajorRepository;
import org.example.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SchoolService {
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    public School findSchoolById(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new RuntimeException("School id doesn't exist."));
    }

    public School saveSchool(School school) {
        return schoolRepository.save(school);
    }

    public void deleteSchool(School school) {
        schoolRepository.deleteById(school.getId());
    }

    @Transactional
    public School updateSchool(Long schoolId, School updatedSchool) {
        School existingSchool = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new IllegalArgumentException("School not found with ID: " + schoolId));

        existingSchool.setName(updatedSchool.getName());

        return schoolRepository.save(existingSchool);
    }

    @Transactional
    public School mergeSchool(String newSchoolName, List<Long> schoolIdToMerge) {
        School newSchool = new School(newSchoolName);
        newSchool = schoolRepository.save(newSchool);

        List<School> schoolToMerge = schoolRepository.findAllById(schoolIdToMerge);
        for (School school : schoolToMerge) {
            List<Major> majorsToUpdate = majorRepository.findBySchoolId(school.getId());
            for (Major major : majorsToUpdate) {
                major.setSchool(newSchool);
                majorRepository.save(major);
            }
        }

        schoolRepository.deleteAllById(schoolIdToMerge);
        return newSchool;
    }

    @Transactional
    public void splitSchool(Long originalSchoolId, List<SchoolSplitRequestDTO.NewSchoolInfo> newSchoolsInfo) {
        School originalSchool = schoolRepository.findById(originalSchoolId)
                .orElseThrow(() -> new EntityNotFoundException("Original school not found"));

        List<Major> allOriginalMajors = majorRepository.findBySchoolId(originalSchool.getId());
        Set<Long> processedMajorIds = new HashSet<>();

        for (SchoolSplitRequestDTO.NewSchoolInfo newSchoolInfo : newSchoolsInfo) {
            School newSchool = schoolRepository.save(new School(newSchoolInfo.newSchoolName()));

            List<Major> majorsToAssign = majorRepository.findAllById(newSchoolInfo.majorIds());
            for (Major major : majorsToAssign) {
                major.setSchool(newSchool);
                majorRepository.save(major);
                processedMajorIds.add(major.getId());
            }
        }

        List<Major> unassignedMajors = allOriginalMajors.stream()
                .filter(major -> !processedMajorIds.contains(major.getId()))
                .toList();

        for (Major major : unassignedMajors) {
            major.setEnrollmentStatus(false);
            majorRepository.save(major);
        }

        schoolRepository.delete(originalSchool);
    }
}
