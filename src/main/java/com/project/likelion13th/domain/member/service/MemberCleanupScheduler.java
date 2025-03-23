package com.project.likelion13th.domain.member.service;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCleanupScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 3 * * *")  // 매일 새벽 3시에 실행
    @Transactional
    public void cleanupDeletedMembers() {
        log.info("Starting scheduled cleanup of deleted members...");

        // 한 달 전 날짜 계산
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        // 한 달 전 이전에 소프트 딜리트된 회원 조회
        List<Member> membersToDelete = memberRepository.findDeletedMembersBefore(oneMonthAgo);

        if (membersToDelete.isEmpty()) {
            log.info("No members to delete.");
            return;
        }

        for (Member member : membersToDelete) {
            // 회원 관련 데이터 삭제 전 로깅
            log.info("Deleting member with ID: {}", member.getId());

            // 이 회원과 관련된 다른 엔티티들을 먼저 삭제 (외래 키 제약 조건 고려)
            // 예: 댓글, 리뷰, 주문 등
            // commentRepository.deleteByMemberId(member.getId());
            // reviewRepository.deleteByMemberId(member.getId());
            // orderRepository.deleteByMemberId(member.getId());
        }

        // 회원 삭제
        memberRepository.deleteAll(membersToDelete);

        log.info("Successfully deleted {} members.", membersToDelete.size());
    }
}
