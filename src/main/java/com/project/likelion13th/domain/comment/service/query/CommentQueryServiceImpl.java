package com.project.likelion13th.domain.comment.service.query;

import com.project.likelion13th.domain.comment.converter.CommentConverter;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.repository.CommentLikeRepository;
import com.project.likelion13th.domain.comment.repository.CommentRepository;
import com.project.likelion13th.domain.review.entity.Review;
import com.project.likelion13th.domain.review.exception.ReviewErrorCode;
import com.project.likelion13th.domain.review.exception.ReviewException;
import com.project.likelion13th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentResDTO.CommentListDTO getCommentList(Long reviewId, Long cursor, int size) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Object[]> comments;

        if(cursor == 0L){
            comments = commentRepository.findFirstPageWithLikeCount(review, pageable);
        }
        else{
            comments = commentRepository.findByCursorWithLikeCount(review, cursor, pageable);
        }


        return CommentConverter.toCommentListDTO(comments);
    }
}
