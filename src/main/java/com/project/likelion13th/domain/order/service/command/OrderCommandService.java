package com.project.likelion13th.domain.order.service.command;

import com.project.likelion13th.domain.order.dto.request.OrderReqDTO;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;

public interface OrderCommandService {
    OrderResDTO.OrderDetailDTO createOrder(OrderReqDTO.CreateOrderDTO dto);
    OrderResDTO.OrderDetailDTO updateOrderStatus(Long orderId, OrderReqDTO.UpdateOrderStatusDTO dto);
    void cancelOrder(Long orderId);
}
