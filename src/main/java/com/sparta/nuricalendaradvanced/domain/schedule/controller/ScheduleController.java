package com.sparta.nuricalendaradvanced.domain.schedule.controller;

import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.domain.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PostMapping()
    public ResponseEntity<ScheduleResponseDto> submitSchedule(@RequestBody @Valid ScheduleRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.submitSchedule(requestDto));
    }

    @GetMapping("/list/{page}")
    public ResponseEntity<ScheduleResponsePage> findAllSchedule(@RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "10") int size,
                                                                @RequestParam(required = false, defaultValue = "updatedAt") String criteria) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.findAllSchedule(page, size, criteria));
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto findByIdSchedule(@PathVariable Long id) {
        return scheduleService.findByIdSchedule(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto modifySchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.modifySchedule(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        return scheduleService.deleteSchedule(id);
    }


}
