package org.example.model.DTO;

import org.example.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeSlotDTO(
        Long timeSlotId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String fieldName,
        TimeSlot.TimeSlotStatus status,
        int remainingCapacity
        ) {
}
