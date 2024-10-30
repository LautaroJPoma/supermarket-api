package com.lauty.supermarket_api.api.mapper;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.model.OrderDetail;
import com.lauty.supermarket_api.api.model.PurchaseOrder;


import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseOrderMapper {

    private final OrderDetailMapper orderDetailMapper;

public PurchaseOrderMapper(OrderDetailMapper orderDetailMapper) {
    this.orderDetailMapper = orderDetailMapper;
}


    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setId(purchaseOrder.getId());
        purchaseOrderDTO.setTotal(purchaseOrder.getTotal());

        // Verificación para evitar NullPointerException si el cliente es nulo
        if (purchaseOrder.getClient() != null) {
            purchaseOrderDTO.setClientId(purchaseOrder.getClient().getId());
        }

        // Mapeo de la lista de OrderDetail a OrderDetailDTO
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
        for (OrderDetail orderDetail : purchaseOrder.getOrderDetails()) {
            orderDetailDTOs.add(orderDetailMapper.toDTO(orderDetail));
        }
        purchaseOrderDTO.setOrderDetails(orderDetailDTOs); // Asegúrate de que el DTO tenga este campo

        return purchaseOrderDTO;
    }

    public PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderDTO.getId());
        purchaseOrder.setTotal(purchaseOrderDTO.getTotal());

        // Mapeo de OrderDetailDTO a OrderDetail
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDTO orderDetailDTO : purchaseOrderDTO.getOrderDetails()) {
            orderDetails.add(orderDetailMapper.toEntity(orderDetailDTO));
        }
        purchaseOrder.setOrderDetails(orderDetails);

        return purchaseOrder;
    }
}


