package com.sparta.nuricalendaradvanced.domain.schedule.dto;

import com.sparta.nuricalendaradvanced.domain.userschedule.entity.UserSchedule;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String date;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.updatedAt = schedule.getUpdatedAt();
    }

    public ScheduleResponseDto(UserSchedule userSchedule) {
        this.id = userSchedule.getSchedule().getId();
        this.date = userSchedule.getSchedule().getDate();
        this.title = userSchedule.getSchedule().getTitle();
        this.contents = userSchedule.getSchedule().getContents();
        this.username = userSchedule.getUser().getUsername();
        this.updatedAt = userSchedule.getSchedule().getUpdatedAt();
    }

}
