package com.project.likelion13th.domain.member.controller;

import com.project.likelion13th.domain.member.dto.request.MemberReqDTO;
import com.project.likelion13th.domain.member.dto.response.MemberResDTO;
import com.project.likelion13th.domain.member.service.command.MemberCommandService;
import com.project.likelion13th.domain.member.service.query.MemberQueryService;
import com.project.likelion13th.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "회원 관련 API")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    // ✅ 회원가입
    @PostMapping("/auth")
    @Operation(summary = "회원가입", description = "사용자가 이메일과 비밀번호를 입력하여 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public CustomResponse<MemberResDTO> register(@RequestBody MemberReqDTO.RegisterDTO dto) {
        MemberResDTO response = memberCommandService.createMember(dto);
        return CustomResponse.onSuccess(response);
    }

    // ✅ 일반 로그인
    @PostMapping("/login")
    @Operation(summary = "일반 로그인", description = "이메일과 비밀번호로 로그인을 수행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public CustomResponse<MemberResDTO> login(@RequestBody MemberReqDTO.LoginDTO loginDTO) {
        return CustomResponse.onSuccess(null);
    }

    // ✅ 카카오 로그인
    @PostMapping("/login/kakao")
    @Operation(summary = "카카오 로그인", description = "카카오 API를 통해 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "카카오 로그인 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public CustomResponse<MemberResDTO> kakaoLogin(@RequestBody MemberReqDTO.KakaoLoginDTO request) {
        return CustomResponse.onSuccess(null);
    }

    // ✅ 비밀번호 수정 (JWT 인증 필요)
    @PatchMapping("/members/password")
    @Operation(summary = "비밀번호 수정", description = "회원의 비밀번호를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공")
    })
    public CustomResponse<String> resetPassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MemberReqDTO.PasswordResetDTO request
    ) {
        memberCommandService.updatePassword(userDetails.getUsername(), request);
        return CustomResponse.onSuccess("비밀번호 변경 성공");
    }

    // ✅ 프로필 수정 (JWT 인증 필요)
    @PatchMapping("/members/{memberId}/profile")
    @Operation(summary = "프로필 수정", description = "회원의 프로필 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 수정 성공", content = @Content(schema = @Schema(implementation = MemberResDTO.class)))
    })
    public CustomResponse<MemberResDTO> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody MemberReqDTO.UpdateProfileDTO request
    ) {
        MemberResDTO response = memberCommandService.updateProfile(userDetails.getUsername(), request);
        return CustomResponse.onSuccess(response);
    }

    // ✅ 회원 탈퇴 (JWT 인증 필요)
    @DeleteMapping("/members/{memberId}")
    @Operation(summary = "회원 탈퇴", description = "회원 계정을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
    })
    public CustomResponse<String> deleteMember(@AuthenticationPrincipal UserDetails userDetails) {
        memberCommandService.deleteMember(userDetails.getUsername());
        return CustomResponse.onSuccess("회원 탈퇴 성공");
    }
}
