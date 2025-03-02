package com.project.likelion13th.domain.member.controller;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "회원 관련 API")
public class MemberController {

    // ✅ 회원가입
    @PostMapping("/auth")
    @Operation(summary = "회원가입", description = "사용자가 이메일과 비밀번호를 입력하여 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public ResponseEntity<MemberResDTO> register() {
        return ResponseEntity.ok(null);
    }

    // ✅ 일반 로그인
    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public ResponseEntity<MemberResDTO> login(@RequestBody MemberReqDTO.LoginDTO loginDTO) {
        return ResponseEntity.ok(null);
    }

    // ✅ 카카오 로그인
    @PostMapping("/login/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 API를 통해 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public ResponseEntity<MemberResDTO> kakaoLogin(@RequestBody MemberReqDTO.KakaoLoginDTO request) {
        return ResponseEntity.ok(null);
    }

    // ✅ 비밀번호 수정 (JWT 인증 필요)
    @PostMapping("/password-reset")
    @Operation(summary = "비밀번호 수정", description = "JWT 인증을 기반으로 사용자의 비밀번호를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> resetPassword(
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        return ResponseEntity.ok(null);
    }
}
