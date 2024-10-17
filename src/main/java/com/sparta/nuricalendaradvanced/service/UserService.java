package com.sparta.nuricalendaradvanced.service;

import com.sparta.nuricalendaradvanced.dto.UserRequestDto;
import com.sparta.nuricalendaradvanced.dto.UserResponseDto;
import com.sparta.nuricalendaradvanced.entity.User;
import com.sparta.nuricalendaradvanced.entity.UserRoleEnum;
import com.sparta.nuricalendaradvanced.jwt.JwtUtil;
import com.sparta.nuricalendaradvanced.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDto signUp(UserRequestDto requestDto) {

        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        // 사용자 ROLE 확인(해당 프로젝트에서는 전체 일반 사용자로 설정)
        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(email, username, password, role);
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    public String signIn(UserRequestDto requestDto, HttpServletResponse res) {

        try {

            String email = requestDto.getEmail();
            String password = requestDto.getPassword();

            User user = findByEmail(email);
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new IllegalArgumentException();
            }

            String token = jwtUtil.createToken(String.valueOf(user.getId()), user.getRole());
            jwtUtil.addJwtCookie(token, res);

            return user.getEmail();

        } catch (NullPointerException e) {

            return "해당 회원은 존재하지 않습니다.";

        } catch (IllegalArgumentException e) {

            return "비밀번호가 일치하지 않습니다.";
        }

    }

    public String duplicateCheck(String email) {

        try {

            User user = findByEmail(email);
            return user.getEmail();

        } catch (IllegalArgumentException e) {

            return "해당 회원은 존재하지 않습니다.";
        }

    }

    @Transactional
    public UserResponseDto modifyUser(UserRequestDto requestDto, HttpServletRequest req) {

        User userId = (User) req.getAttribute("user");
        User user = userRepository.findById(userId.getId()).orElseThrow(IllegalArgumentException::new);

        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        user.update(email, username, password);

        return new UserResponseDto(user);

    }

    public String deleteUser(HttpServletRequest req) {

        User user = (User) req.getAttribute("user");
        userRepository.delete(user);
        return user.getEmail();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }


}
