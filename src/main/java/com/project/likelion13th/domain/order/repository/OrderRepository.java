package com.project.likelion13th.domain.order.repository;

import com.project.likelion13th.domain.order.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 첫 페이지 조회
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product " +
            "WHERE o.member.id = :memberId " +
            "ORDER BY o.id DESC")
    Slice<Order> findFirstPageByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 커서 기반 페이지 조회
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product " +
            "WHERE o.member.id = :memberId AND o.id < :cursor " +
            "ORDER BY o.id DESC")
    Slice<Order> findByMemberIdAndCursor(@Param("memberId") Long memberId, @Param("cursor") Long cursor, Pageable pageable);
}