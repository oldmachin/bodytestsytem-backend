package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.Appointment;
import org.example.model.DTO.AppointmentRequestDTO;
import org.example.model.DTO.AppointmentResponseDTO;
import org.example.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * 创建一个新的体测预约。
     * POST /api/appointments
     * @param requestDTO 包含学生ID和时间段ID的请求体
     * @return 新建预约的DTO和201状态码
     */
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @RequestBody AppointmentRequestDTO requestDTO) {
        try {
            Appointment appointment = appointmentService.createAppointment(
                    requestDTO.studentId(),
                    requestDTO.timeSlotId()
            );
            return new ResponseEntity<>(new AppointmentResponseDTO(
                    appointment.getId(),
                    appointment.getStudent().getName(),
                    appointment.getTimeSlot().getField().getName(),
                    appointment.getTimeSlot().getDate(),
                    appointment.getTimeSlot().getStartTime(),
                    appointment.getCreationTime()
            ), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * 取消一个已存在的预约。
     * DELETE /api/appointments/{appointmentId}
     * @param appointmentId 预约的ID
     * @return 无内容响应和204状态码
     */
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long appointmentId) {
        try {
            appointmentService.cancelAppointment(appointmentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}