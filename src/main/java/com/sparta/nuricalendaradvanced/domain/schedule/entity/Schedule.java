package com.sparta.nuricalendaradvanced.domain.schedule.entity;

import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
import com.sparta.nuricalendaradvanced.common.entity.Timestamped;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleResponseDto;
import com.sparta.nuricalendaradvanced.domain.userschedule.entity.UserSchedule;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Schedule extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String date;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "schedule")
    private List<UserSchedule> userScheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();
    

    public static Schedule from(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule();
        schedule.initData(requestDto);
        return schedule;
    }

    private void initData(ScheduleRequestDto requestDto) {
        this.date = requestDto.getDate();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }


    public ScheduleResponseDto to() {
        return new ScheduleResponseDto(
                id, date, title, contents, username, getUpdatedAt()
        );
    }
}