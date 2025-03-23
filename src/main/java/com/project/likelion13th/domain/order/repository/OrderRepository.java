package com.project.likelion13th.domain.order.repository;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.order.entity.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 첫 페이지 조회 - 삭제되지 않은 주문만
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product " +
            "WHERE o.member.id = :memberId AND o.deletedAt IS NULL " +
            "ORDER BY o.id DESC")
    Slice<Order> findFirstPageByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 커서 기반 페이지 조회 - 삭제되지 않은 주문만
    @Query("SELECT o FROM Order o JOIN FETCH o.orderProducts op JOIN FETCH op.product " +
            "WHERE o.member.id = :memberId AND o.id < :cursor AND o.deletedAt IS NULL " +
            "ORDER BY o.id DESC")
    Slice<Order> findByMemberIdAndCursor(@Param("memberId") Long memberId, @Param("cursor") Long cursor, Pageable pageable);

    // 특정 회원의 특정 상태의 주문 조회 - 삭제되지 않은 주문만
    @Query("SELECT o FROM Order o WHERE o.member = :member AND o.orderStatus = :status AND o.deletedAt IS NULL")
    Optional<Order> findByMemberAndOrderStatusAndNotDeleted(@Param("member") Member member, @Param("status") OrderStatus status);

    // 삭제되지 않은 주문만 조회
    @Query("SELECT o FROM Order o WHERE o.id = :id AND o.deletedAt IS NULL")
    Optional<Order> findByIdAndNotDeleted(@Param("id") Long id);
}