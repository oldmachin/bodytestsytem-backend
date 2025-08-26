package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.DTO.StudentRegisterRequest;
import org.example.model.user.Student;
import org.example.model.user.User;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerStudent(StudentRegisterRequest request) {
        User user = new User();
        userRepository.save(user);

        Student student = new Student(
                request.studentId(),
                request.idCardNumber(),
                request.name(),
                request.gender(),
                request.ethnicCode(),
                request.grade(),
                request.major(),
        );
    }
}
