package org.example.repository;

import org.example.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public int countByTimeSlotId(Long timeSlotId);

    public boolean existsByStudentIdAndTimeSlotId(Long studentId, Long timeSlotId);
}
