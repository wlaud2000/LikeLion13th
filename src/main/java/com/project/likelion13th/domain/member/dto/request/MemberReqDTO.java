package com.project.likelion13th.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;


public class MemberReqDTO {

    @Getter
    public static class RegisterDTO {
        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @Schema(description = "비밀번호", example = "securepassword123")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        private String password;

        @Schema(description = "이름", example = "오랑이")
        @NotBlank(message = "이름은 필수 입력값입니다.")
        private String name;
    }

    @Getter
    public static class LoginDTO {
        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이어야 합니다.")
        private String email;

        @Schema(description = "비밀번호", example = "securepassword123")
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        private String password;
    }

    @Getter
    public static class KakaoLoginDTO {
        @Schema(description = "카카오 액세스 토큰", example = "kakao-access-token")
        @NotBlank(message = "카카오 액세스 토큰은 필수입니다.")
        private String accessToken;
    }

    @Getter
    public static class PasswordResetDTO {
        @Schema(description = "새로운 비밀번호", example = "newpassword123")
        @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
        private String password;
    }

    @Getter
    public static class UpdateProfileDTO {
        @Schema(description = "닉네임", example = "새로운닉네임")
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        private String nickname;
    }
}
