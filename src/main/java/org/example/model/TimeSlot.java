package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "timeslots")
public class TimeSlot {
    public enum TimeSlotStatus {
        AVAILABLE,
        FULL,
        CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id")
    private Field field;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    private TimeSlotStatus status;

    public TimeSlot() {}

    public TimeSlot(LocalDate date, Field field, LocalTime startTime, LocalTime endTime, Integer maxCapacity) {
        this.date = date;
        this.field = field;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.status = TimeSlotStatus.AVAILABLE;
    }

    public TimeSlotStatus getStatus() {
        return status;
    }

    public Field getField() {
        return field;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setStatus(TimeSlotStatus status) {
        this.status = status;
    }
}
