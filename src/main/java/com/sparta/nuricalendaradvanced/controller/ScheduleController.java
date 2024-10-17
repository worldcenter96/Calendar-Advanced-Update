package com.sparta.nuricalendaradvanced.controller;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/submit")
    public ScheduleResponseDto submitSchedule(@RequestBody @Valid ScheduleRequestDto requestDto, HttpServletRequest req) {
        return scheduleService.submitSchedule(requestDto, req);
    }

    @GetMapping("/list/{page}")
    public List<ScheduleResponseDto> findAllSchedule(@PathVariable int page) {
        return scheduleService.findAllSchedule(page);
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
