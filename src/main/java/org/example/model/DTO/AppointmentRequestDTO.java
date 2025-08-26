package org.example.model.DTO;


public record AppointmentRequestDTO(
        Long studentId,
        Long timeSlotId
) {
}
