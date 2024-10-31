package com.sparta.nuricalendaradvanced.domain.schedule.entity;

import com.sparta.nuricalendaradvanced.common.entity.Timestamped;
import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
import com.sparta.nuricalendaradvanced.domain.schedule.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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
    private String weather;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();


    public static Schedule from(ScheduleRequestDto requestDto, User user, String weather) {
        Schedule schedule = new Schedule();
        schedule.initData(requestDto, user, weather);
        return schedule;
    }

    private void initData(ScheduleRequestDto requestDto, User user, String weather) {
        this.date = requestDto.getDate();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.weather = weather;
        this.user = user;
    }

    public Schedule update(ScheduleRequestDto requestDto, String weather) {
        this.date = requestDto.getDate();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.weather = weather;
        return this;
    }
}
