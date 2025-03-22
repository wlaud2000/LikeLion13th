package com.project.likelion13th.domain.review.service.command;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import com.project.likelion13th.domain.review.converter.ReviewConverter;
import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.entity.Review;
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
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 productId입니다."));  // TODO CustomError 처리

        Review review = ReviewConverter.toEntity(member, product, dto);
        reviewRepository.save(review);

        return ReviewConverter.from(review);
    }
}
