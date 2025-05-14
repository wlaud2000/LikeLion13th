package com.project.likelion13th.global.jwt.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.likelion13th.global.apiPayload.CustomResponse;
import com.project.likelion13th.global.jwt.dto.JwtDTO;
import com.project.likelion13th.global.jwt.repository.TokenRepository;
import com.project.likelion13th.global.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        try {
            String accessToken = jwtUtil.resolveAccessToken(request);
            log.info("access token parsing: {}", accessToken);

            if (accessToken == null) {
                log.warn("[ CustomLogoutHandler ] Access Token 이 없습니다.");
                return;
            }
            // 토큰 유효성 검증
            jwtUtil.validateToken(accessToken);

            // 토큰 삭제
            String email = jwtUtil.getEmail(accessToken);
            tokenRepository.deleteByEmail(email);

            // CustomResponse 사용하여 응답 통일
            CustomResponse<String> responseBody = CustomResponse.onSuccess("로그아웃이 완료되었습니다.");

            //JSON 변환
            ObjectMapper objectMapper = new ObjectMapper();
            response.setStatus(HttpStatus.OK.value()); //Response 의 Status 를 200으로 설정
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            //Body 에 토큰이 담긴 Response 쓰기
            response.getWriter().write(objectMapper.writeValueAsString(responseBody));


        } catch (ExpiredJwtException e) {
            log.warn("[ CustomLogoutHandler ] Access Token 이 만료되었습니다.");
            throw new RuntimeException(" Access Token 이 만료되었습니다.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.warn("[ CustomLogoutHandler ] 유효하지 않은 토큰입니다.");
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            log.error("[ CustomLogoutHandler ] 로그아웃 처리 중 오류 발생: {}", e.getMessage());
            log.error("Exception: {}", e.getClass().getName());
            log.error("Exception: {}", e.getClass().getSimpleName());
            log.error("Exception: {}", e.getClass());
            throw new RuntimeException("로그아웃 처리 중 오류 발생.");
        }
    }



}