package com.sparta.nuricalendaradvanced.dto;

import com.sparta.nuricalendaradvanced.entity.Post;
import com.sparta.nuricalendaradvanced.entity.Schedule;
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

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.date = schedule.getDate();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.updatedAt = schedule.getUpdatedAt();
    }

    public ScheduleResponseDto(Post post) {
        this.id = post.getSchedule().getId();
        this.date = post.getSchedule().getDate();
        this.title = post.getSchedule().getTitle();
        this.contents = post.getSchedule().getContents();
        this.username = post.getUser().getUsername();
        this.updatedAt = post.getSchedule().getUpdatedAt();
    }
}
