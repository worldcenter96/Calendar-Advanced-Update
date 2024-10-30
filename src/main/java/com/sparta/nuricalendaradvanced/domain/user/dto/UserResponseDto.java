package com.sparta.nuricalendaradvanced.domain.user.dto;

import com.sparta.nuricalendaradvanced.domain.user.entity.User;
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

    public static UserResponseDto of(User user) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.init(user);
        return responseDto;
    }

    private void init(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.updatedAt = user.getUpdatedAt();
    }


}
