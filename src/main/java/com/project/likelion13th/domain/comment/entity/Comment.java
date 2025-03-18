package com.project.likelion13th.domain.comment.entity;

import com.project.likelion13th.domain.common.entity.BaseEntity;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    @OneToOne
    private Member member;
}
