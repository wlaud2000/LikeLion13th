package com.project.likelion13th.domain.member.repository;

import com.project.likelion13th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 삭제되지 않은 회원 중 이메일로 조회
    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.deletedAt IS NULL")
    Optional<Member> findByEmailAndNotDeleted(@Param("email") String email);

    // 삭제되지 않은 회원 ID로 조회
    @Query("SELECT m FROM Member m WHERE m.id = :id AND m.deletedAt IS NULL")
    Optional<Member> findByIdAndNotDeleted(@Param("id") Long id);

    // 특정 날짜 이전에 소프트 딜리트된 회원 목록 조회
    @Query("SELECT m FROM Member m WHERE m.deletedAt IS NOT NULL AND m.deletedAt <= :date")
    List<Member> findDeletedMembersBefore(@Param("date") LocalDateTime date);
}
