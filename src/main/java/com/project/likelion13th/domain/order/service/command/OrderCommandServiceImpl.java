package com.project.likelion13th.domain.order.service.command;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.member.exception.MemberErrorCode;
import com.project.likelion13th.domain.member.exception.MemberException;
import com.project.likelion13th.domain.member.repository.MemberRepository;
import com.project.likelion13th.domain.order.converter.OrderConverter;
import com.project.likelion13th.domain.order.dto.request.OrderReqDTO;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.order.entity.OrderProduct;
import com.project.likelion13th.domain.order.entity.OrderStatus;
import com.project.likelion13th.domain.order.exception.OrderErrorCode;
import com.project.likelion13th.domain.order.exception.OrderException;
import com.project.likelion13th.domain.order.repository.OrderProductRepository;
import com.project.likelion13th.domain.order.repository.OrderRepository;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.exception.ProductErrorCode;
import com.project.likelion13th.domain.product.exception.ProductException;
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
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // Product 조회
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        List<OrderReqDTO> orderProductDtos = new ArrayList<>();

        // 기존 주문 조회 (주문 상태가 PENDING인 주문)
        Optional<Order> newOrder = orderRepository.findByMemberAndOrderStatusAndNotDeleted(member, OrderStatus.PROCESS);
        Order order;
        order = newOrder.orElseGet(() -> OrderConverter.toOrder(member));

        OrderProduct orderProduct = OrderConverter.toOrderProduct(order, product, dto.getQuantity());

        // Order에 OrderProduct 추가
        order.addOrderProduct(orderProduct);

        orderProductRepository.save(orderProduct);
        orderRepository.save(order);

        return OrderConverter.toOrderDetailDTO(order, orderProduct);
    }

    @Override
    public OrderResDTO.OrderDetailDTO updateOrderStatus(Long orderId, OrderReqDTO.UpdateOrderStatusDTO dto) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 주문 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 본인의 주문만 수정 가능 (또는 관리자)
        if (!order.getMember().getId().equals(member.getId())) {
            throw new OrderException(OrderErrorCode.ORDER_UNAUTHORIZED);
        }

        // 주문 상태 업데이트
        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(dto.getOrderStatus());
        } catch (IllegalArgumentException e) {
            throw new OrderException(OrderErrorCode.ORDER_INVALID_STATUS);
        }

        // 이미 취소된 주문인 경우
        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new OrderException(OrderErrorCode.ORDER_ALREADY_CANCELED);
        }

        order.updateOrderStatus(newStatus);
        orderRepository.save(order);

        // 응답 생성 (첫 번째 주문 상품을 사용하여 응답 생성)
        if (!order.getOrderProducts().isEmpty()) {
            OrderProduct firstOrderProduct = order.getOrderProducts().get(0);
            return OrderConverter.toOrderDetailDTO(order, firstOrderProduct);
        } else {
            throw new OrderException(OrderErrorCode.ORDER_EMPTY_PRODUCT);
        }
    }

    @Override
    public void cancelOrder(Long orderId) {
        // 임시로 Member 설정 (실제로는 인증된 사용자 정보를 사용)
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 주문 조회
        Order order = orderRepository.findByIdAndNotDeleted(orderId)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        // 본인의 주문만 취소 가능 (또는 관리자)
        if (!order.getMember().getId().equals(member.getId())) {
            throw new OrderException(OrderErrorCode.ORDER_UNAUTHORIZED);
        }

        // 이미 배송 완료된 주문은 취소 불가
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new OrderException(OrderErrorCode.ORDER_ALREADY_DELIVERED);
        }

        // 이미 취소된 주문인 경우
        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new OrderException(OrderErrorCode.ORDER_ALREADY_CANCELED);
        }

        // 주문 상태를 취소로 변경
        order.updateOrderStatus(OrderStatus.CANCELED);

        // soft delete 처리
        order.delete();

        orderRepository.save(order);
    }
}
