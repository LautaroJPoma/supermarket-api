package com.lauty.supermarket_api.api.mapper;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.model.OrderDetail;

@Component
public class OrderDetailMapper {

    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetail.getId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setPurchaseOrderId(orderDetail.getPurchaseOrder().getId());
        return orderDetailDTO;
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDTO.getId());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        // La PurchaseOrder debe ser asignada por el servicio, no aquí
        return orderDetail;
    }
}
