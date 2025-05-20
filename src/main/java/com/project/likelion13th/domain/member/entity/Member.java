package com.project.likelion13th.domain.member.entity;

import com.project.likelion13th.domain.common.entity.BaseEntity;
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
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    // soft delete를 위한 필드 추가
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    // 회원 정보 수정 메서드
    public void updateProfile(String nickname) {
        this.nickname = nickname;
    }

    // 비밀번호 변경 메서드
    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    // soft delete 메서드
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    // 삭제 여부 확인 메서드
    public boolean isDeleted() {
        return deletedAt != null;
    }
}
