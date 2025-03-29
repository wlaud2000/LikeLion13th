package com.project.likelion13th.domain.order.service.query;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.exception.MemberErrorCode;
import com.project.likelion13th.domain.member.exception.MemberException;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.order.converter.OrderConverter;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryServiceImpl implements OrderQueryService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;


    @Override
    public OrderResDTO.OrderListDTO getMyOrder(String email, Long cursor, int size) {
        // 로그인한 Member 조회
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Order> orders;

        if (cursor == 0L) {
            orders = orderRepository.findFirstPageByMemberId(member.getId(), pageable);
        } else {
            orders = orderRepository.findByMemberIdAndCursor(member.getId(), cursor, pageable);
        }

        return OrderConverter.toOrderListDTO(orders);
    }
}
