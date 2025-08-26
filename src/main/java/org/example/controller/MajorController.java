package org.example.controller;


import org.example.model.entity.department.School;
import org.example.service.MajorService;
import org.example.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MajorController {
    @RestController
    @RequestMapping("/api/schools")
    public class SchoolController {
        @Autowired
        private SchoolService schoolService;

        @Autowired
        private MajorService majorService;
    }
}
