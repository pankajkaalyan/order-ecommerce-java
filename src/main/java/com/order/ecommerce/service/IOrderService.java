package com.order.ecommerce.service;

import com.order.ecommerce.dto.OrderResponseDto;
import com.order.ecommerce.dto.OrderDto;
import com.order.ecommerce.dto.ResponseDto;

public interface IOrderService {
    OrderResponseDto createOrder(OrderDto orderDto);

    OrderDto findOrderById(String id);

    ResponseDto updateOrderStatus(String id, String status);
}
