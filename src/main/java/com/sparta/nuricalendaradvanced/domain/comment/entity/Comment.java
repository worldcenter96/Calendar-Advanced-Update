package com.sparta.nuricalendaradvanced.domain.comment.entity;

import com.sparta.nuricalendaradvanced.common.entity.Timestamped;
import com.sparta.nuricalendaradvanced.domain.comment.dto.CommentRequestDto;
import com.sparta.nuricalendaradvanced.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;


    public static Comment from(CommentRequestDto requestDto, Schedule schedule) {
        Comment comment = new Comment();
        comment.init(requestDto, schedule);
        return comment;
    }

    private void init(CommentRequestDto requestDto, Schedule schedule) {
        this.comment = requestDto.getComment();
        this.schedule = schedule;
    }

    public Comment update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
        return this;

    }
}
