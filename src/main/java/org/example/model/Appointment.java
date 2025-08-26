package org.example.model;

import jakarta.persistence.*;
import org.example.model.user.Student;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_slot_id", nullable = false)
    private TimeSlot timeSlot;

    private LocalDateTime creationTime;

    public Appointment() {}

    public Appointment(Student student, TimeSlot timeSlot) {
        this.student = student;
        this.timeSlot = timeSlot;
        this.creationTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
