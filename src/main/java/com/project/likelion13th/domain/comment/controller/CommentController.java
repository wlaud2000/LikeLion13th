package com.project.likelion13th.domain.comment.controller;

import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.service.command.CommentCommandService;
import com.project.likelion13th.domain.comment.service.query.CommentQueryService;
import com.project.likelion13th.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Comment API", description = "댓글 관련 API")
public class CommentController {
    private final CommentQueryService commentQueryService;
    private final CommentCommandService commentCommandService;

    @GetMapping("/reviews/{reviewId}/comments")
    @Operation(summary = "댓글 목록 조회", description = "특정 리뷰에 대한 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 목록 조회 성공", content = @Content(schema = @Schema(implementation = CommentResDTO.CommentListDTO.class)))
    })
    public CustomResponse<CommentResDTO.CommentListDTO> getComments(
            @PathVariable Long reviewId,
            @RequestParam Long cursor,
            @RequestParam Integer size
    ) {
        CommentResDTO.CommentListDTO response = commentQueryService.getCommentList(reviewId, cursor, size);
        return CustomResponse.onSuccess(response);
    }

    @PostMapping("/reviews/{reviewId}/comments")
    @Operation(summary = "댓글 작성", description = "특정 리뷰에 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 작성 성공", content = @Content(schema = @Schema(implementation = CommentResDTO.CommentDetailDTO.class)))
    })
    public ResponseEntity<CustomResponse<CommentResDTO.CommentDetailDTO>> createComment(@PathVariable Long reviewId, @RequestBody CommentReqDTO.CreateCommentDTO request) {

        CommentResDTO.CommentDetailDTO response = commentCommandService.createComment(reviewId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CustomResponse.onSuccess(HttpStatus.CREATED, response));
    }

    @PatchMapping("/comments/{commentId}")
    @Operation(summary = "댓글 수정", description = "작성한 댓글을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공", content = @Content(schema = @Schema(implementation = CommentResDTO.CommentDetailDTO.class)))
    })
    public CustomResponse<CommentResDTO.CommentDetailDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentReqDTO.UpdateCommentDTO request) {
        CommentResDTO.CommentDetailDTO response = commentCommandService.updateComment(commentId, request);
        return CustomResponse.onSuccess(response);
    }

    @DeleteMapping("/comments/{commentId}")
    @Operation(summary = "댓글 삭제", description = "작성한 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공")
    })
    public CustomResponse<String> deleteComment(@PathVariable Long commentId) {
        commentCommandService.deleteComment(commentId);
        return CustomResponse.onSuccess("댓글 삭제 성공");
    }

    @PostMapping("/comments/{commentId}/like")
    @Operation(summary = "댓글 좋아요", description = "특정 댓글에 좋아요를 추가/취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 좋아요 성공")
    })
    public CustomResponse<Long> likeComment(@PathVariable Long commentId) {
        Long likeCount = commentCommandService.commentLike(commentId);
        return CustomResponse.onSuccess(likeCount);
    }
}
