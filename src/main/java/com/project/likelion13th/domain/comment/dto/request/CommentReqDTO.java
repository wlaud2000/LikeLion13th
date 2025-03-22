package com.project.likelion13th.domain.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentReqDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateCommentDTO {
        @Schema(description = "댓글 내용", example = "좋은 리뷰 감사합니다!")
        @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
        private String content;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateCommentDTO {
        @Schema(description = "댓글 내용", example = "좋은 리뷰 감사합니다!")
        @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
        private String content;
    }
}
