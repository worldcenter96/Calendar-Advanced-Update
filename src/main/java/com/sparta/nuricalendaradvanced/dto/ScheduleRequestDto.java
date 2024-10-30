package com.sparta.nuricalendaradvanced.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDto {

    @Pattern(regexp = "^(\\d{4})-(\\d{2})-(\\d{2})$")
    @Size(max = 10)
    private String date;

    @Size(max = 50)
    private String title;

    @Size(max = 255)
    private String contents;

    @Size(max = 10)
    private String username;

}
