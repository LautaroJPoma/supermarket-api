package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.mapper.PurchaseOrderMapper;
import com.lauty.supermarket_api.api.model.PurchaseOrder;
import com.lauty.supermarket_api.api.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purcharseOrderRepository;
    private final PurchaseOrderMapper purcharseOrderMapper;


    public PurchaseOrderServiceImpl(PurchaseOrderMapper purcharseOrderMapper, PurchaseOrderRepository purcharseOrderRepository) {
        this.purcharseOrderRepository = purcharseOrderRepository;
        this.purcharseOrderMapper = purcharseOrderMapper;
    }

    @Override
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = purcharseOrderMapper.toEntity(purchaseOrderDTO);
        PurchaseOrder savedPurchaseOrder = purcharseOrderRepository.save(purchaseOrder);
        return purcharseOrderMapper.toDTO(savedPurchaseOrder);
    }


    @Override
    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purcharseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder == null) {
            return null;
        }
        return purcharseOrderMapper.toDTO(purchaseOrder);
    }


    @Override
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        Iterable<PurchaseOrder> purchaseOrdersIterable = purcharseOrderRepository.findAll();
        List<PurchaseOrderDTO> purchaseOrderDTOs = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrdersIterable) {
            purchaseOrderDTOs.add(purcharseOrderMapper.toDTO(purchaseOrder));
        }
        return purchaseOrderDTOs;
    }


    @Override
    public PurchaseOrderDTO updatePurchaseOrder(Long id, PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder existingPurchaseOrder = purcharseOrderRepository.findById(id).orElse(null);
        if (existingPurchaseOrder == null) {
            return null; // Retorna null si no se encuentra
        }

        // Actualiza los campos
        existingPurchaseOrder.setTotal(purchaseOrderDTO.getTotal());

        // Guarda y retorna el PurchaseOrder actualizado
        PurchaseOrder updatedPurchaseOrder = purcharseOrderRepository.save(existingPurchaseOrder);
        return purcharseOrderMapper.toDTO(updatedPurchaseOrder);
    }


    @Override
    public void deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purcharseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder != null) {
            purcharseOrderRepository.delete(purchaseOrder);
        }
    }

}
