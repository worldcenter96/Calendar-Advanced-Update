package com.sparta.nuricalendaradvanced.dto;

import com.sparta.nuricalendaradvanced.entity.Schedule;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ScheduleResponsePage {

    private List<ScheduleResponseDto> schedules;
    private int totalPages;
    private long totalElements;

    public ScheduleResponsePage(Page<Schedule> schedules) {

        this.schedules =  schedules.stream()
                .map(Schedule::to)
                .toList();

        this.totalPages = schedules.getTotalPages();
        this.totalElements = schedules.getTotalElements();

    }
}
