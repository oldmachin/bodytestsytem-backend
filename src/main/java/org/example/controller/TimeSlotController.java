package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.DTO.TimeSlotDTO;
import org.example.model.DTO.TimeSlotRequest;
import org.example.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots")
public class TimeSlotController {
    @Autowired
    private TimeSlotService timeSlotService;

    /**
     * 新增一个时间段。
     * POST /api/time-slots/{fieldId}
     * @param fieldId 关联场地的ID
     * @param request 包含时间段详情的DTO
     * @return 新建时间段的DTO和201状态码
     */
    @PostMapping("/{fieldId}")
    public ResponseEntity<TimeSlotDTO> addTimeSlot(
            @PathVariable Long fieldId,
            @RequestBody TimeSlotRequest request) {
        try {
            TimeSlotDTO newTimeSlot = timeSlotService.addTimeSlot(fieldId, request);
            return new ResponseEntity<>(newTimeSlot, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据ID获取单个时间段详情。
     * GET /api/time-slots/{timeSlotId}
     * @param timeSlotId 时间段的ID
     * @return 时间段的DTO和200状态码
     */
    @GetMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable Long timeSlotId) {
        try {
            TimeSlotDTO timeSlot = timeSlotService.getTimeSlotDTOById(timeSlotId);
            return ResponseEntity.ok(timeSlot);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取某个场地的所有时间段。
     * GET /api/time-slots/field/{fieldId}
     * @param fieldId 场地的ID
     * @return 一个TimeSlotDTO列表和200状态码
     */
    @GetMapping("/field/{fieldId}")
    public ResponseEntity<List<TimeSlotDTO>> getTimeSlotsByFieldId(@PathVariable Long fieldId) {
        List<TimeSlotDTO> timeSlots = timeSlotService.getTimeSlotsByFieldId(fieldId);
        return ResponseEntity.ok(timeSlots);
    }

    /**
     * 更新一个已存在的时间段。
     * PUT /api/time-slots/{timeSlotId}
     * @param timeSlotId 需要更新的时间段ID
     * @param request 包含更新信息的DTO
     * @return 更新后的时间段DTO和200状态码
     */
    @PutMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotDTO> updateTimeSlot(
            @PathVariable Long timeSlotId,
            @RequestBody TimeSlotRequest request) {
        try {
            TimeSlotDTO updatedTimeSlot = timeSlotService.updateTimeSlot(timeSlotId, request);
            return ResponseEntity.ok(updatedTimeSlot);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 根据ID删除一个时间段。
     * DELETE /api/time-slots/{timeSlotId}
     * @param timeSlotId 需要删除的时间段ID
     * @return 无内容响应和204状态码
     */
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long timeSlotId) {
        try {
            timeSlotService.deleteTimeSlot(timeSlotId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}