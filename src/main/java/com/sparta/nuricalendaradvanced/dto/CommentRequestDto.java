package com.sparta.nuricalendaradvanced.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @Size(max = 100)
    private String comment;
}
