package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;

public interface OrderDetailService {
    OrderDetailDTO getOrderDetailById(Long id);
    List<OrderDetailDTO> getAllOrderDetails();
    OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO);
    OrderDetailDTO updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO);
    void deleteOrderDetail(Long id);
}
