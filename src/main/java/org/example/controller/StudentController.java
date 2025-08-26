package org.example.controller;

import org.example.model.DTO.StudentDTO;
import org.example.model.DTO.StudentRegisterRequest;
import org.example.model.DTO.StudentUpdateRequest;
import org.example.model.user.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // 注册学生（新增学生及对应的用户）
    @PostMapping("/register")
    public ResponseEntity<Void> registerStudent(@RequestBody StudentRegisterRequest request) {
        studentService.registerStudent(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 删除学生
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 修改学生的相关信息
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody StudentUpdateRequest request
    ) {
        Student updatedStudent = studentService.updateStudent(id, request);
        return ResponseEntity.ok(updatedStudent);
    }

    // 获取所有学生信息
    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(@PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Student> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    // 查询学生的相关信息
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(new StudentDTO(
                student.getId(),
                student.getStudentId(),
                student.getName(),
                student.getIdCardNumber(),
                student.getGender(),
                student.getEthnicGroup(),
                student.getGrade(),
                student.getMajor()
        ));
    }

    // 重置登录密码
    @PutMapping("/{Id}/password-reset")
    public ResponseEntity<Void> resetStudentUserPassword(@PathVariable Long id, String oldPassword, String idCardNumber, String newPassword) {
        studentService.saveStudent(id, oldPassword, idCardNumber, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
