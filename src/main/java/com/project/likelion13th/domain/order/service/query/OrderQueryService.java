package com.project.likelion13th.domain.order.service.query;

import com.project.likelion13th.domain.order.dto.response.OrderResDTO;

public interface OrderQueryService {
    OrderResDTO.OrderListDTO getMyOrder(String email, Long cursor, int size);
}
