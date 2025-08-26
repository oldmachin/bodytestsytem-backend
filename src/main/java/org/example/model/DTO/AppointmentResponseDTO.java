package org.example.model.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long appointmentId,
        String studentName,
        String fieldName,
        LocalDate appointmentDate,
        LocalTime appointmentStartTime,
        LocalDateTime creationTime
) {
}
