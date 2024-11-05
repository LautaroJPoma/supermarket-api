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

        // Verificación de nulidad para evitar NullPointerException
        if (orderDetail.getPurchaseOrder() != null) {
            orderDetailDTO.setPurchaseOrderId(orderDetail.getPurchaseOrder().getId());
        } else {
            orderDetailDTO.setPurchaseOrderId(null);
        }

        if (orderDetail.getProduct() != null) {
            orderDetailDTO.setProductId(orderDetail.getProduct().getId());
        } else {
            orderDetailDTO.setProductId(null);
        }

        // Asignar el precio del producto
        if (orderDetail.getProduct() != null) {
            orderDetailDTO.setProductPrice(orderDetail.getProduct().getPrice());
        }

        return orderDetailDTO;
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(orderDetailDTO.getId());
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        // La asignación de PurchaseOrder debe realizarse en el servicio
        return orderDetail;
    }
}
