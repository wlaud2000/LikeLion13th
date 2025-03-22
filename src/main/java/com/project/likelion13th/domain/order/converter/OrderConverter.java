package com.project.likelion13th.domain.order.converter;

import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.order.dto.request.OrderReqDTO;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.order.entity.OrderProduct;
import com.project.likelion13th.domain.order.entity.OrderStatus;
import com.project.likelion13th.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static OrderResDTO.OrderDetailDTO from(Order order, OrderProduct orderProduct) {
        Product product = orderProduct.getProduct();
        Double totalPrice = product.getPrice() * orderProduct.getQuantity();

        return OrderResDTO.OrderDetailDTO.builder()
                .orderId(order.getId())
                .productId(product.getId())
                .quantity(orderProduct.getQuantity())
                .totalPrice(totalPrice)
                .status(order.getOrderStatus().toString())
                .orderDate(order.getCreatedAt().toString())
                .build();
    }

    public static OrderResDTO.OrderListDTO from(Slice<Order> orders) {
        List<OrderResDTO.OrderDetailDTO> orderDetailDTOs = orders.getContent().stream()
                .flatMap(order -> order.getOrderProducts().stream()
                        .map(orderProduct -> from(order, orderProduct)))
                .toList();

        return new OrderResDTO.OrderListDTO(
                orders.isEmpty() ? 0L : orders.getContent().get(orders.getContent().size() - 1).getId(),
                orders.hasNext(),
                orderDetailDTOs
        );
    }

    public static Order toEntity(Member member){
        return Order.builder()
                .member(member)
                .orderStatus(OrderStatus.PROCESS)
                .orderProducts(new ArrayList<>())
                .build();
    }

    public static OrderProduct toEntity(Order order, Product product, int quantity){
        return OrderProduct.builder()
                .order(order)
                .product(product)
                .quantity(quantity)
                .build();
    }
}