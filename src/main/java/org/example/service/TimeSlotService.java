package org.example.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.model.DTO.TimeSlotDTO;
import org.example.model.DTO.TimeSlotRequest;
import org.example.model.Field;
import org.example.model.TimeSlot;
import org.example.repository.AppointmentRepository;
import org.example.repository.FieldRepository;
import org.example.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {
    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    /**
     * 新增一个时间段。
     * @param fieldId 关联场地的ID。
     * @param request 包含时间段详情的DTO。
     * @return 新建时间段的DTO。
     */
    @Transactional
    public TimeSlotDTO addTimeSlot(Long fieldId, TimeSlotRequest request) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + fieldId));

        TimeSlot timeSlot = new TimeSlot(
                request.date(),
                field,
                request.startTime(),
                request.endTime(),
                request.maxCapacity()
        );

        timeSlot = timeSlotRepository.save(timeSlot);
        return toDTO(timeSlot);
    }

    /**
     * 根据ID获取单个时间段的DTO。
     * @param timeSlotId 时间段的ID。
     * @return 时间段的DTO。
     */
    public TimeSlotDTO getTimeSlotDTOById(Long timeSlotId) {
        TimeSlot timeSlot = getTimeSlotById(timeSlotId);
        return toDTO(timeSlot);
    }

    /**
     * 获取某个场地的所有时间段。
     * @param fieldId 场地的ID。
     * @return 一个TimeSlotDTO列表。
     */
    public List<TimeSlotDTO> getTimeSlotsByFieldId(Long fieldId) {
        return timeSlotRepository.findByFieldId(fieldId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 更新一个已存在的时间段。
     * @param timeSlotId 需要更新的时间段ID。
     * @param request 包含更新信息的DTO。
     * @return 更新后的时间段DTO。
     */
    @Transactional
    public TimeSlotDTO updateTimeSlot(Long timeSlotId, TimeSlotRequest request) {
        TimeSlot timeSlot = getTimeSlotById(timeSlotId);

        // 仅在请求中字段不为空时才进行更新
        if (request.date() != null) {
            timeSlot.setDate(request.date());
        }
        if (request.startTime() != null) {
            timeSlot.setStartTime(request.startTime());
        }
        if (request.endTime() != null) {
            timeSlot.setEndTime(request.endTime());
        }
        if (request.maxCapacity() > 0) {
            timeSlot.setMaxCapacity(request.maxCapacity());
        }

        timeSlotRepository.save(timeSlot);
        return toDTO(timeSlot);
    }

    /**
     * 根据ID删除一个时间段。
     * @param timeSlotId 需要删除的时间段ID。
     */
    @Transactional
    public void deleteTimeSlot(Long timeSlotId) {
        TimeSlot timeSlot = getTimeSlotById(timeSlotId);
        timeSlotRepository.delete(timeSlot);
    }

    private TimeSlot getTimeSlotById(Long timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new EntityNotFoundException("TimeSlot not found with id: " + timeSlotId));
    }

    private TimeSlotDTO toDTO(TimeSlot timeSlot) {
        long bookedCount = appointmentRepository.countByTimeSlotId(timeSlot.getId());

        return new TimeSlotDTO(
                timeSlot.getId(),
                timeSlot.getDate(),
                timeSlot.getStartTime(),
                timeSlot.getEndTime(),
                timeSlot.getField().getName(),
                timeSlot.getStatus(),
                timeSlot.getMaxCapacity() - (int) bookedCount
        );
    }
}