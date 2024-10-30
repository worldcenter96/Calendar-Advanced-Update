package com.sparta.nuricalendaradvanced.common.filter;

import com.sparta.nuricalendaradvanced.common.exception.ResponseException;
import com.sparta.nuricalendaradvanced.common.exception.ResponseStatus;
import com.sparta.nuricalendaradvanced.common.jwt.JwtUtil;
import com.sparta.nuricalendaradvanced.domain.user.entity.User;
import com.sparta.nuricalendaradvanced.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthFilter(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ResponseException, ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/user/signup") || url.startsWith("/api/user/signin"))
        ) {
            chain.doFilter(request, response);
            return;
        }

        String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

        if (!StringUtils.hasText(tokenValue)) {
            throw new ResponseException(ResponseStatus.TOKEN_NOT_FOUND);
        }


        String token = jwtUtil.substringToken(tokenValue);


        if (!jwtUtil.validateToken(token)) {
            throw new ResponseException(ResponseStatus.TOKEN_INVALID);
        }


        Claims info = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findById(Long.valueOf(info.getSubject())).orElseThrow(() ->
                new ResponseException(ResponseStatus.USER_NOT_FOUND)
        );

        request.setAttribute("user", user);
        chain.doFilter(request, response);
    }


}
