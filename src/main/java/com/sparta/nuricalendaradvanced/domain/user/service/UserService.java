package com.sparta.nuricalendaradvanced.domain.user.service;

import com.sparta.nuricalendaradvanced.domain.common.jwt.JwtUtil;
import com.sparta.nuricalendaradvanced.domain.user.dto.UserRequestDto;
import com.sparta.nuricalendaradvanced.domain.user.dto.UserResponseDto;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import com.sparta.nuricalendaradvanced.domain.user.entity.UserRoleEnum;
import com.sparta.nuricalendaradvanced.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public UserResponseDto signUp(UserRequestDto requestDto) {

        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = checkRole(requestDto);


        checkUsername(username);
        checkEmail(email);

        User user = userRepository.save(User.from(username, email, password, role));

        return UserResponseDto.of(user);
    }

    public String signIn(UserRequestDto requestDto, HttpServletResponse res) {

        String email = requestDto.getEmail();
        String inputPassword = requestDto.getPassword();
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("해당 회원은 존재하지 않습니다."));

        checkPassword(inputPassword, user.getPassword());

        String token = jwtUtil.createToken(String.valueOf(user.getId()), user.getRole());
        jwtUtil.addJwtCookie(token, res);

        return user.getEmail();

    }

    @Transactional
    public UserResponseDto updateUser(UserRequestDto requestDto, User user) {

        User userInfo = userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new);

        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        userInfo.update(email, username, password);

        return UserResponseDto.of(userInfo);

    }

    public void deleteUser(User user) {

        userRepository.delete(user);
    }


    public void checkUsername(String username) {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
    }

    public void checkEmail(String email) {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");

        }
    }

    @Value("${jwt.secret.key}")
    private String secretKey;

    public UserRoleEnum checkRole(UserRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(secretKey)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");

            }
            role = UserRoleEnum.ADMIN;
        }
        return role;
    }

    public void checkPassword(String inputPassword, String password) {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new IllegalArgumentException("암호가 일치하지 않습니다.");
        }
    }

}
