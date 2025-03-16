package com.project.likelion13th.domain.review.converter;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewConverter {

    public static Review toEntity(Member member, Product product, ReviewReqDTO.CreateReviewDTO dto){
        return Review.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .product(product)
                //임의로 Member 추가
                .build();
    }

    public static ReviewResDTO.ReviewDetailDTO from(Review review){
        return ReviewResDTO.ReviewDetailDTO.builder()
                .reviewId(review.getId())
                .productId(review.getProduct().getId())
                .content(review.getContent())
                .author(review.getMember().getNickname())
                .createdAt(review.getCreatedAt().toString())
                .build();
    }

    public static ReviewResDTO.ReviewListDTO from(Slice<Review> reviews){
        return ReviewResDTO.ReviewListDTO.builder()
                .reviews(reviews.stream().map(ReviewConverter::from).toList())
                .hasNext(reviews.hasNext())
                .nextCursor(reviews.isEmpty() ?
                        0L :
                        reviews.getContent().get(reviews.getContent().size() -1 ).getId())
                .build();
    }
}
