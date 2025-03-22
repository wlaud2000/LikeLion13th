package com.project.likelion13th.domain.comment.service.command;

import com.project.likelion13th.domain.comment.converter.CommentConverter;
import com.project.likelion13th.domain.comment.converter.CommentLikeConverter;
import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.entity.CommentLike;
import com.project.likelion13th.domain.comment.repository.CommentLikeRepository;
import com.project.likelion13th.domain.comment.repository.CommentRepository;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.review.entity.Review;
import com.project.likelion13th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("reviewId에 해당하는 리뷰가 없습니다."));   // TODO CustomError 처리

        Comment comment = CommentConverter.toEntity(member, review, dto);
        commentRepository.save(comment);

        return CommentConverter.from(comment, 0L);
    }

    @Override
    public void commentLike(Long commentId) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("commentId에 해당하는 댓글이 없습니다."));    // TODO CustomError 처리

        CommentLike commentLike = CommentLikeConverter.toEntity(member, comment);
        commentLikeRepository.save(commentLike);
    }
}
