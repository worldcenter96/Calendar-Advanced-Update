package com.sparta.nuricalendaradvanced.entity;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import com.sparta.nuricalendaradvanced.dto.ScheduleResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Post> postList = new ArrayList<>();

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
