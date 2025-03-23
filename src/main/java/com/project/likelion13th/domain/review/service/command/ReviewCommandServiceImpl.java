package com.project.likelion13th.domain.review.service.command;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.exception.MemberErrorCode;
import com.project.likelion13th.domain.member.exception.MemberException;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.exception.ProductErrorCode;
import com.project.likelion13th.domain.product.exception.ProductException;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import com.project.likelion13th.domain.review.converter.ReviewConverter;
import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.entity.Review;
import com.project.likelion13th.domain.review.exception.ReviewErrorCode;
import com.project.likelion13th.domain.review.exception.ReviewException;
import com.project.likelion13th.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewCommandServiceImpl implements ReviewCommandService{

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    @Override
    public ReviewResDTO.ReviewDetailDTO createReview(Long productId, ReviewReqDTO.CreateReviewDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Product product = productRepository.findByIdAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Review review = ReviewConverter.toReview(member, product, dto);
        reviewRepository.save(review);

        return ReviewConverter.toReviewDetailDTO(review);
    }

    @Override
    public ReviewResDTO.ReviewDetailDTO updateReview(Long reviewId, ReviewReqDTO.UpdateReviewDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 리뷰 조회
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 본인 리뷰만 수정 가능
        if (!review.getMember().getId().equals(member.getId())) {
            throw new ReviewException(ReviewErrorCode.REVIEW_UNAUTHORIZED);
        }

        // 리뷰 내용 및 평점 업데이트
        review.updateReview(dto.getContent(), dto.getRating());
        reviewRepository.save(review);

        return ReviewConverter.toReviewDetailDTO(review);
    }

    @Override
    public void deleteReview(Long reviewId) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 리뷰 조회
        Review review = reviewRepository.findByIdAndNotDeleted(reviewId)
                .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));

        // 본인 리뷰만 삭제 가능
        if (!review.getMember().getId().equals(member.getId())) {
            throw new ReviewException(ReviewErrorCode.REVIEW_UNAUTHORIZED);
        }

        // soft delete 처리
        review.delete();
        reviewRepository.save(review);
    }
}
