package com.sparta.nuricalendaradvanced.domain.schedule.controller;

import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponsePage;
import com.sparta.nuricalendaradvanced.domain.schedule.service.ScheduleService;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ScheduleResponseDto> submitSchedule(@RequestBody @Valid ScheduleRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(scheduleService.submitSchedule(requestDto, user));
    }

    @GetMapping("/list")
    public ResponseEntity<ScheduleResponsePage> findAllSchedule(@RequestParam(required = false, defaultValue = "1") int page,
                                                                @RequestParam(required = false, defaultValue = "10") int size,
                                                                @RequestParam(required = false, defaultValue = "updatedAt") String criteria) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.findAllSchedule(page, size, criteria));
    }

    @GetMapping()
    public ResponseEntity<ScheduleResponseDto> findByIdSchedule(@RequestParam Long id) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.findByIdSchedule(id));
    }

    @PutMapping()
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@RequestParam Long id,
                                                              @RequestBody ScheduleRequestDto requestDto) throws ResponseException{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(scheduleService.updateSchedule(id, requestDto));
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteSchedule(@RequestParam Long id) {

        scheduleService.deleteSchedule(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();

    }


}
