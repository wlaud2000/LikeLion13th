package com.project.likelion13th.domain.order.service.query;

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


    @Override
    public OrderResDTO.OrderListDTO getMyOrder(Long cursor, int size) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Long memberId = 1L;

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Order> orders;

        if (cursor == 0L) {
            orders = orderRepository.findFirstPageByMemberId(memberId, pageable);
        } else {
            orders = orderRepository.findByMemberIdAndCursor(memberId, cursor, pageable);
        }

        return OrderConverter.toOrderListDTO(orders);
    }
}
