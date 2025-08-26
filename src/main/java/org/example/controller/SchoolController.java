package org.example.controller;

import org.example.model.DTO.SchoolMergeRequestDTO;
import org.example.model.DTO.SchoolSplitRequestDTO;
import org.example.model.entity.department.School;
import org.example.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/api/schools")
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @PostMapping("/merge")
    public ResponseEntity<School> mergeSchools(@RequestBody SchoolMergeRequestDTO mergeRequest) {
        School mergedSchool = schoolService.mergeSchool(mergeRequest.newSchoolName(), mergeRequest.schoolIdsToMerge());
        return ResponseEntity.ok(mergedSchool);
    }

    @PostMapping("/split")
    public ResponseEntity<Void> splitSchool(@RequestBody SchoolSplitRequestDTO splitRequest) {
        schoolService.splitSchool(splitRequest.originalScoolId(), splitRequest.newSchools());
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<School> createSchool(@RequestBody School school) {
        School newSchool = schoolService.saveSchool(school);
        return ResponseEntity.ok(newSchool);
    }

    @GetMapping
    public ResponseEntity<List<School>> getAllSchools() {
        List<School> schools = schoolService.findAllSchools();
        return ResponseEntity.ok(schools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
        School school = schoolService.findSchoolById(id);
        return ResponseEntity.ok(school);
    }

    @PutMapping("/{id}")
    public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody School updatedSchool) {
        School school = schoolService.updateSchool(id, updatedSchool);
        return ResponseEntity.ok(school);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        School school = schoolService.findSchoolById(id);
        schoolService.deleteSchool(school);
        return ResponseEntity.ok().build();
    }
}
