package com.project.likelion13th.domain.comment.service.command;

import com.project.likelion13th.domain.comment.converter.CommentConverter;
import com.project.likelion13th.domain.comment.converter.CommentLikeConverter;
import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.entity.CommentLike;
import com.project.likelion13th.domain.comment.exception.CommentErrorCode;
import com.project.likelion13th.domain.comment.exception.CommentException;
import com.project.likelion13th.domain.comment.repository.CommentLikeRepository;
import com.project.likelion13th.domain.comment.repository.CommentRepository;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.exception.MemberErrorCode;
import com.project.likelion13th.domain.member.exception.MemberException;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.review.entity.Review;
import com.project.likelion13th.domain.review.exception.ReviewErrorCode;
import com.project.likelion13th.domain.review.exception.ReviewException;
import com.project.likelion13th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandServiceImpl implements CommentCommandService{

    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final MemberRepository memberRepository;

    @Override
    public CommentResDTO.CommentDetailDTO createComment(Long reviewId, CommentReqDTO.CreateCommentDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        Comment comment = CommentConverter.toComment(member, review, dto);
        commentRepository.save(comment);

        return CommentConverter.toCommentDetailDTO(comment, 0L);
    }

    @Override
    public CommentResDTO.CommentDetailDTO updateComment(Long commentId, CommentReqDTO.UpdateCommentDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 댓글이 삭제된 경우
        if (comment.isDeleted()) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        // 댓글 작성자와 현재 로그인한 사용자가 다른 경우 권한 오류
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new CommentException(CommentErrorCode.COMMENT_UNAUTHORIZED);
        }

        // 댓글 내용 업데이트
        comment.updateContent(dto.getContent());

        // 댓글 저장
        commentRepository.save(comment);

        // 댓글 좋아요 수 조회
        Long likeCount = commentLikeRepository.countByCommentId(commentId);

        return CommentConverter.toCommentDetailDTO(comment, likeCount);
    }

    @Override
    public void deleteComment(Long commentId) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 이미 삭제된 댓글인 경우
        if (comment.isDeleted()) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        // 댓글 작성자와 현재 로그인한 사용자가 다른 경우 권한 오류
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new CommentException(CommentErrorCode.COMMENT_UNAUTHORIZED);
        }

        // soft delete 처리
        comment.delete();
        commentRepository.save(comment);

        // CommentLike 엔티티도 함께 소프트 딜리트 처리
        List<CommentLike> commentLikes = commentLikeRepository.findAllByComment(comment);
        for (CommentLike like : commentLikes) {
            like.delete();
        }
        commentLikeRepository.saveAll(commentLikes);
    }

    @Override
    public Long commentLike(Long commentId) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));

        // 삭제된 댓글인지 확인
        if (comment.getDeletedAt() != null) {
            throw new CommentException(CommentErrorCode.COMMENT_NOT_FOUND);
        }

        // 이미 좋아요를 눌렀는지 확인
        Optional<CommentLike> existingLike = commentLikeRepository.findByMemberAndComment(member, comment);

        if (existingLike.isPresent()) {
            // 이미 좋아요가 있으면 좋아요 취소 (좋아요 삭제)
            commentLikeRepository.delete(existingLike.get());
        } else {
            // 좋아요가 없으면 새로 추가
            CommentLike commentLike = CommentLikeConverter.toEntity(member, comment);
            commentLikeRepository.save(commentLike);
        }

        // 좋아요 개수 반환
        return commentLikeRepository.countByCommentId(commentId);
    }
}
