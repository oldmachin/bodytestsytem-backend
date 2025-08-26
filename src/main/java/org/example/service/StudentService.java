package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.StudentRegisterRequest;
import org.example.model.DTO.StudentUpdateRequest;
import org.example.model.user.Role;
import org.example.model.user.Student;
import org.example.model.user.User;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
    }

    @Transactional
    public void registerStudent(StudentRegisterRequest request) {
        User user = new User(request.studentId(), passwordEncoder.encode("123456"));
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        Student student = new Student(
                request.studentId(),
                request.idCardNumber(),
                request.name(),
                request.gender(),
                request.ethnicCode(),
                request.grade(),
                request.major(),
                user
        );
        studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long studentId, StudentUpdateRequest request) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        if (request.name() != null) {
            student.setName(request.name());
        }
        if (request.major() != null) {
            student.setMajor(request.major());
        }

        return studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        Student student = getStudentById(studentId);

        studentRepository.delete(student);
    }

    @Transactional
    public void saveStudent(Long studentId, String oldPassword, String idCardNumber, String newPassword) {
        Student student = getStudentById(studentId);
        User user = student.getUser();

        if (newPassword.equals(oldPassword)) {
            throw new RuntimeException("新旧密码不能相同");
        }

        boolean isIdCardMatch = !idCardNumber.isBlank() && idCardNumber.equals(student.getIdCardNumber());
        boolean isPasswordMatch = !oldPassword.isBlank() && passwordEncoder.matches(oldPassword, user.getPassword());

        if (isIdCardMatch || isPasswordMatch) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new RuntimeException("提供的身份信息不正确，无法修改密码。");
        }
    }
}
