package com.sparta.nuricalendaradvanced.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    @Email
    @Size(max = 30)
    private String email;

    @Size(max = 10)
    private String username;

    @Size(max = 20)
    private String password;

}
