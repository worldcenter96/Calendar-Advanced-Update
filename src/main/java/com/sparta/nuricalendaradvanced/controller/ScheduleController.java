package com.sparta.nuricalendaradvanced.controller;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;


    @PostMapping("/submit")
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

    @GetMapping("/post/{id}")
    public ScheduleResponseDto findByIdSchedule(@PathVariable Long id) {
        return scheduleService.findByIdSchedule(id);
    }

    @PutMapping("/modify/{id}")
    public ScheduleResponseDto modifySchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.modifySchedule(id, requestDto);
    }

    @DeleteMapping("/delete/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        return scheduleService.deleteSchedule(id);
    }


}
