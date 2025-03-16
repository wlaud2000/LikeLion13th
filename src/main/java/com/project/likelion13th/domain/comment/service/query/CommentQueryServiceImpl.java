package com.project.likelion13th.domain.comment.service.query;

import com.project.likelion13th.domain.comment.converter.CommentConverter;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.repository.CommentLikeRepository;
import com.project.likelion13th.domain.comment.repository.CommentRepository;
import com.project.likelion13th.domain.review.entity.Review;
import com.project.likelion13th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Override
    public CommentResDTO.CommentListDTO getCommentList(Long reviewId, Long cursor, int size) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("reviewId에 해당하는 리뷰가 없습니다."));   // TODO CustomError 처리

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Object[]> comments;

        if(cursor == 0L){
            comments = commentRepository.findFirstPageWithLikeCount(review, pageable);
        }
        else{
            comments = commentRepository.findByCursorWithLikeCount(review, cursor, pageable);
        }


        return CommentConverter.from(comments);
    }
}
