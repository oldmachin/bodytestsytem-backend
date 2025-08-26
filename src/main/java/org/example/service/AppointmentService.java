package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.Appointment;
import org.example.model.DTO.AppointmentResponseDTO;
import org.example.model.TimeSlot;
import org.example.model.user.Student;
import org.example.repository.AppointmentRepository;
import org.example.repository.StudentRepository;
import org.example.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    /**
     * 创建一个新的体测预约。
     * @param studentId 学生的ID
     * @param timeSlotId 预约时间段的ID
     * @return 新创建的预约实体
     */
    @Transactional
    public Appointment createAppointment(Long studentId, Long timeSlotId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new EntityNotFoundException("TimeSlot not found with id: " + timeSlotId));

        long currentAppointments = appointmentRepository.countByTimeSlotId(timeSlotId);
        if (currentAppointments >= timeSlot.getMaxCapacity()) {
            throw new IllegalStateException("Time slot is already full.");
        }

        if (appointmentRepository.existsByStudentIdAndTimeSlotId(studentId, timeSlotId)) {
            throw new IllegalStateException("Student has already booked this time slot.");
        }

        Appointment newAppointment = new Appointment(student, timeSlot);

        return appointmentRepository.save(newAppointment);
    }

    /**
     * 取消一个已存在的预约。
     * @param appointmentId 预约的ID
     */
    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id: " + appointmentId));

        appointmentRepository.delete(appointment);
    }

    private AppointmentResponseDTO toResponseDTO(Appointment appointment) {
        // 实际应用中，这里会执行实体到 DTO 的映射逻辑
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getStudent().getName(), // 假设 Student 有 getName()
                appointment.getTimeSlot().getField().getName(), // 获取场地名称
                appointment.getTimeSlot().getDate(),
                appointment.getTimeSlot().getStartTime(),
                appointment.getCreationTime()
        );
    }
}