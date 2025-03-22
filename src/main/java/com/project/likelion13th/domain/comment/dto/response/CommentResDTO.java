package com.project.likelion13th.domain.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentResDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentListDTO {
        @Schema(description = "커서 ID (다음 페이지 조회를 위한 커서)", example = "50")
        private Long nextCursor;

        @Schema(description = "다음 여부", example = "true")
        private Boolean hasNext;

        @Schema(description = "댓글 목록")
        private List<CommentDetailDTO> comments;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentDetailDTO {
        @Schema(description = "댓글 ID", example = "789")
        private Long commentId;

        @Schema(description = "댓글 내용", example = "좋은 리뷰 감사합니다!")
        private String content;

        @Schema(description = "작성자", example = "Jane Doe")
        private String author;

        @Schema(description = "작성일", example = "2025-03-02")
        private String createdAt;

        @Schema(description = "좋아요 수", example = "12")
        private Long likeCount;
    }
}
