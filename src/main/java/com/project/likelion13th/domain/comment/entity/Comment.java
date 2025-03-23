package com.project.likelion13th.domain.comment.entity;

import com.project.likelion13th.domain.common.entity.BaseEntity;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    // soft delete를 위한 필드 추가
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // content 수정을 위한 메서드
    public void updateContent(String content) {
        this.content = content;
    }

    // soft delete를 위한 메서드
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    // 삭제 여부 확인 메서드
    public boolean isDeleted() {
        return deletedAt != null;
    }
}
