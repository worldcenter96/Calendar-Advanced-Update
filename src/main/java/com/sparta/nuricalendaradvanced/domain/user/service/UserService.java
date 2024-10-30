package com.sparta.nuricalendaradvanced.domain.user.service;

import com.sparta.nuricalendaradvanced.common.exception.UserException;
import com.sparta.nuricalendaradvanced.common.exception.UserResponseStatus;
import com.sparta.nuricalendaradvanced.common.jwt.JwtUtil;
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


    public UserResponseDto signUp(UserRequestDto requestDto) throws UserException {

        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = checkRole(requestDto);


        checkUsername(username);
        checkEmail(email);

        User user = userRepository.save(User.from(username, email, password, role));

        return UserResponseDto.of(user);
    }

    public String signIn(UserRequestDto requestDto, HttpServletResponse res) throws UserException {

        String email = requestDto.getEmail();
        String inputPassword = requestDto.getPassword();
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserException(UserResponseStatus.USER_NOT_FOUND));

        checkPassword(inputPassword, user.getPassword());

        String token = jwtUtil.createToken(String.valueOf(user.getId()), user.getRole());
        jwtUtil.addJwtCookie(token, res);

        return user.getEmail();

    }

    @Transactional
    public UserResponseDto updateUser(UserRequestDto requestDto, User user) throws UserException {

        User userInfo = userRepository.findById(user.getId()).orElseThrow(() ->
                new UserException(UserResponseStatus.USER_NOT_FOUND));

        String email = requestDto.getEmail();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        userInfo.update(email, username, password);

        return UserResponseDto.of(userInfo);

    }

    public void deleteUser(User user) {

        userRepository.delete(user);
    }


    public void checkUsername(String username) throws UserException {
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new UserException(UserResponseStatus.USER_NAME_DUPLICATED);
        }
    }

    public void checkEmail(String email) throws UserException {
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new UserException(UserResponseStatus.USER_EMAIL_DUPLICATED);

        }
    }

    @Value("${jwt.secret.key}")
    private String secretKey;

    public UserRoleEnum checkRole(UserRequestDto requestDto) throws UserException {
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(secretKey)) {
                throw new UserException(UserResponseStatus.ADMIN_PASSWORD_NOT_MATCH);

            }
            role = UserRoleEnum.ADMIN;
        }
        return role;
    }

    public void checkPassword(String inputPassword, String password) throws UserException {
        if (!passwordEncoder.matches(inputPassword, password)) {
            throw new UserException(UserResponseStatus.ADMIN_PASSWORD_NOT_MATCH);
        }
    }

}
