package com.sparta.nuricalendaradvanced.dto;

import com.sparta.nuricalendaradvanced.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private Long scheduleId;
    private String comment;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.comment = comment.getComment();
    }
}
