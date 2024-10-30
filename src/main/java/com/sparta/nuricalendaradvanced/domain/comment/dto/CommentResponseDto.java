package com.sparta.nuricalendaradvanced.domain.comment.dto;

import com.sparta.nuricalendaradvanced.domain.comment.entity.Comment;
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

    public static CommentResponseDto of(Comment comment) {
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.init(comment);
        return responseDto;

    }

    private void init(Comment comment) {
        this.id = comment.getId();
        this.scheduleId = comment.getSchedule().getId();
        this.comment = comment.getComment();
    }
}
