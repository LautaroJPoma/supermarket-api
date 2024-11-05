package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.mapper.PurchaseOrderMapper;
import com.lauty.supermarket_api.api.model.PurchaseOrder;
import com.lauty.supermarket_api.api.repository.ClientRepository;
import com.lauty.supermarket_api.api.repository.ProductRepository;
import com.lauty.supermarket_api.api.repository.PurchaseOrderRepository;
import com.lauty.supermarket_api.api.model.Client;
import com.lauty.supermarket_api.api.model.OrderDetail;
import com.lauty.supermarket_api.api.model.Product;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderMapper purchaseOrderMapper,
            PurchaseOrderRepository purchaseOrderRepository, ClientRepository clientRepository,
            ProductRepository productRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    // Método para actualizar el total de la PurchaseOrder
    @Override
    public void updateTotal(PurchaseOrder purchaseOrder) {
        double total = 0.0;

        // Calcula el total directamente usando OrderDetails en la entidad PurchaseOrder
        for (OrderDetail orderDetail : purchaseOrder.getOrderDetails()) {
            Product product = productRepository.findById(orderDetail.getProduct().getId()).orElse(null);
            if (product != null) {
                total += orderDetail.getQuantity() * product.getPrice();
            }
        }

        purchaseOrder.setTotal(total);
        purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
        PurchaseOrder purchaseOrder = purchaseOrderMapper.toEntity(purchaseOrderDTO);

        // Recuperar el cliente y asignarlo a la orden de compra
        Client client = clientRepository.findById(purchaseOrderDTO.getClientId()).orElse(null);
        if (client != null) {
            purchaseOrder.setClient(client);
        }

        // Guardar la orden antes de calcular el total
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // Llamar a updateTotal para calcular el total y guardar nuevamente
        updateTotal(savedPurchaseOrder);
        savedPurchaseOrder = purchaseOrderRepository.save(savedPurchaseOrder);

        return purchaseOrderMapper.toDTO(savedPurchaseOrder);
    }

    @Override
    public PurchaseOrderDTO updatePurchaseOrder(Long id, PurchaseOrderDTO purchaseOrderDTO) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la orden de compra no puede ser null");
        }

        // Buscar la orden de compra existente
        PurchaseOrder existingPurchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        if (existingPurchaseOrder == null) {
            return null; // Retorna null si no se encuentra
        }

        // Actualizar los detalles de la compra usando el DTO
        existingPurchaseOrder.setTotal(purchaseOrderDTO.getTotal());

        // Asignar el cliente si el clientId está presente en el DTO
        if (purchaseOrderDTO.getClientId() != null) {
            Client client = clientRepository.findById(purchaseOrderDTO.getClientId()).orElse(null);
            if (client != null) {
                existingPurchaseOrder.setClient(client);
            } else {
                throw new IllegalArgumentException("Cliente con el ID proporcionado no existe");
            }
        }

        // Llamar a updateTotal para calcular el total y guardar nuevamente
        updateTotal(existingPurchaseOrder);
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.save(existingPurchaseOrder);

        return purchaseOrderMapper.toDTO(updatedPurchaseOrder);
    }

    @Override
    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder == null) {
            return null;
        }
        return purchaseOrderMapper.toDTO(purchaseOrder);
    }

    @Override
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        Iterable<PurchaseOrder> purchaseOrdersIterable = purchaseOrderRepository.findAll();
        List<PurchaseOrderDTO> purchaseOrderDTOs = new ArrayList<>();
        for (PurchaseOrder purchaseOrder : purchaseOrdersIterable) {
            purchaseOrderDTOs.add(purchaseOrderMapper.toDTO(purchaseOrder));
        }
        return purchaseOrderDTOs;
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElse(null);
        if (purchaseOrder != null) {
            purchaseOrderRepository.delete(purchaseOrder);
        }
    }
}
