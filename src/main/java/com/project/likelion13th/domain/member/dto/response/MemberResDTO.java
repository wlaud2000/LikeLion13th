package com.project.likelion13th.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResDTO {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "이름", example = "jamey")
    private String name;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "JWT access 토큰", example = "Bearer eyJhbGciOiJIUzI1NiIsInR...")
    private String accessToken;

    @Schema(description = "JWT Refresh 토큰", example = "Bearer eyJhbGciOiJIUzI1NiIsInR...")
    private String refreshToken;

    @Schema(description = "생성일", example = "2222-11-11")
    private LocalDateTime createAt;
}
