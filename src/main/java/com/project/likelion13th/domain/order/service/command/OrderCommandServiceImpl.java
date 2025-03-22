package com.project.likelion13th.domain.order.service.command;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.order.converter.OrderConverter;
import com.project.likelion13th.domain.order.dto.request.OrderReqDTO;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.order.entity.OrderProduct;
import com.project.likelion13th.domain.order.entity.OrderStatus;
import com.project.likelion13th.domain.order.repository.OrderProductRepository;
import com.project.likelion13th.domain.order.repository.OrderRepository;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandServiceImpl implements OrderCommandService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    @Override
    public OrderResDTO.OrderDetailDTO createOrder(OrderReqDTO.CreateOrderDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // Product 조회
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));

        List<OrderReqDTO> orderProductDtos = new ArrayList<>();

        // 기존 주문 조회 (주문 상태가 PENDING인 주문)
        Optional<Order> newOrder = orderRepository.findByMemberAndOrderStatus(member, OrderStatus.PROCESS);
        Order order;
        order = newOrder.orElseGet(() -> OrderConverter.toEntity(member));

        OrderProduct orderProduct = OrderConverter.toEntity(order, product, dto.getQuantity());

        // Order에 OrderProduct 추가
        order.addOrderProduct(orderProduct);

        orderProductRepository.save(orderProduct);
        orderRepository.save(order);

        return OrderConverter.from(order, orderProduct);
    }
}
