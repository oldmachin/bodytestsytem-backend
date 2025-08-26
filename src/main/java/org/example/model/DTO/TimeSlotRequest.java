package org.example.model.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeSlotRequest (
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int maxCapacity
) {
}
