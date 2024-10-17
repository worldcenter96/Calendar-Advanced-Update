package com.sparta.nuricalendaradvanced.entity;

import com.sparta.nuricalendaradvanced.dto.ScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "schedule")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "schedule", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    public Schedule(ScheduleRequestDto requestDto) {
        this.date = requestDto.getDate();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(ScheduleRequestDto requestDto) {
        this.date = requestDto.getDate();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
