package com.lauty.supermarket_api.api.service;

import java.util.List;

import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.model.PurchaseOrder;

public interface PurchaseOrderService {
    PurchaseOrderDTO getPurchaseOrderById(Long id);

    List<PurchaseOrderDTO> getAllPurchaseOrders();

    PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    PurchaseOrderDTO updatePurchaseOrder(Long id, PurchaseOrderDTO purchaseOrderDTO);

    void deletePurchaseOrder(Long id);

    void updateTotal(PurchaseOrder purchaseOrder);

    boolean existsById(Long id);
}
