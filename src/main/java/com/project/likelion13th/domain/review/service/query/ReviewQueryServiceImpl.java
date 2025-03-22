package com.project.likelion13th.domain.review.service.query;

import com.project.likelion13th.domain.order.converter.OrderConverter;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import com.project.likelion13th.domain.review.converter.ReviewConverter;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.entity.Review;
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
public class ReviewQueryServiceImpl implements ReviewQueryService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    @Override
    public ReviewResDTO.ReviewDetailDTO getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new IllegalArgumentException("reviewId에 해당하는 리뷰가 없습니다."));   // TODO CustomError 처리

        return ReviewConverter.from(review);
    }

    @Override
    public ReviewResDTO.ReviewListDTO getReviewPage(Long productId, Long cursor, int size) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 productId입니다."));  // TODO CustomError 처리

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Review> reviews;

        if(cursor == 0L){
            reviews = reviewRepository.findFirstPage(product, pageable);
        }
        else{
            reviews = reviewRepository.findByCursor(product, cursor, pageable);
        }

        return ReviewConverter.from(reviews);
    }

    @Override
    public ReviewResDTO.ReviewListDTO getMyReview(Long cursor, int size) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Long memberId = 1L;

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Review> reviews;

        if (cursor == 0L) {
            reviews = reviewRepository.findFirstPageByMemberId(memberId, pageable);
        } else {
            reviews = reviewRepository.findByMemberIdAndCursor(memberId, cursor, pageable);
        }

        return ReviewConverter.from(reviews);

    }


}
