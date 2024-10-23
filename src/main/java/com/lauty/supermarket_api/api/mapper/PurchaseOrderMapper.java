package com.lauty.supermarket_api.api.mapper;

import org.springframework.stereotype.Component;

import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.model.PurchaseOrder;

@Component
public class PurchaseOrderMapper {

    public PurchaseOrderDTO toDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderDTO purchaseOrderDTO = new PurchaseOrderDTO();
        purchaseOrderDTO.setId(purchaseOrder.getId());
        purchaseOrderDTO.setTotal(purchaseOrder.getTotal());
        purchaseOrderDTO.setClientId(purchaseOrder.getClient().getId());
        return purchaseOrderDTO;
    }

    public PurchaseOrder toEntity(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(purchaseOrderDTO.getId());
        purchaseOrder.setTotal(purchaseOrderDTO.getTotal());
        // El cliente debe ser asignado por el servicio, no aquí
        return purchaseOrder;
    }
}
