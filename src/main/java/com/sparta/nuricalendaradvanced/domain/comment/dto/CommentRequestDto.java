package com.sparta.nuricalendaradvanced.domain.comment.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @Size(max = 100)
    private String comment;
}
