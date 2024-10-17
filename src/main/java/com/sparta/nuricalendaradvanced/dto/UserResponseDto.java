package com.sparta.nuricalendaradvanced.dto;

import com.sparta.nuricalendaradvanced.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String email;
    private String username;
    private LocalDateTime updatedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.updatedAt = user.getUpdatedAt();
    }

}
