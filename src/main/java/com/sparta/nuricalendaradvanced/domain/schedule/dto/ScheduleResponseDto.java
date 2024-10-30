package com.sparta.nuricalendaradvanced.domain.schedule.dto;

import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String date;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime updatedAt;

    public static ScheduleResponseDto of(Schedule schedule) {
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        responseDto.init(schedule);
        return responseDto;
    }

    public void init(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.username = schedule.getUser().getUsername();
        this.updatedAt = schedule.getUpdatedAt();

    }


    public static ScheduleResponseDto to(Schedule schedule) {
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        responseDto.init(schedule);
        return responseDto;
    }
}
