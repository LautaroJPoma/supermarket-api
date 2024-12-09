package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.mapper.OrderDetailMapper;
import com.lauty.supermarket_api.api.model.OrderDetail;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.model.PurchaseOrder;
import com.lauty.supermarket_api.api.repository.OrderDetailRepository;
import com.lauty.supermarket_api.api.repository.ProductRepository;
import com.lauty.supermarket_api.api.repository.PurchaseOrderRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final PurchaseOrderService purchaseOrderService;

    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper, OrderDetailRepository orderDetailRepository,
            PurchaseOrderRepository purchaseOrderRepository, ProductRepository productRepository,
            PurchaseOrderService purchaseOrderService) {
        this.orderDetailMapper = orderDetailMapper;
        this.orderDetailRepository = orderDetailRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public OrderDetailDTO createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);

        // Validación para evitar un valor null en quantity
        if (orderDetail.getQuantity() == null) {
            orderDetail.setQuantity(1); // Puedes cambiar 1 por el valor predeterminado que desees
        }

        PurchaseOrder purchaseOrder = null;
        // Asignar PurchaseOrder desde el ID en el DTO
        if (orderDetailDTO.getPurchaseOrderId() != null) {
            purchaseOrder = purchaseOrderRepository.findById(orderDetailDTO.getPurchaseOrderId())
                    .orElse(null);
            if (purchaseOrder != null) {
                orderDetail.setPurchaseOrder(purchaseOrder);
            }
        }

        // Asignar Product desde el ID en el DTO
        if (orderDetailDTO.getProductId() != null) {
            Product product = productRepository.findById(orderDetailDTO.getProductId())
                    .orElse(null);
            if (product != null) {
                orderDetail.setProduct(product);
            }
        }

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        // Actualiza el total de la PurchaseOrder
        if (purchaseOrder != null) {
            // Agrega el OrderDetail a la PurchaseOrder
            purchaseOrder.getOrderDetails().add(savedOrderDetail);
            purchaseOrderService.updateTotal(purchaseOrder); // Llama al método para actualizar el total
        }
        return orderDetailMapper.toDTO(savedOrderDetail);
    }

    @Override
    public OrderDetailDTO getOrderDetailById(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail == null) {
            return null;
        }
        return orderDetailMapper.toDTO(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() {
        Iterable<OrderDetail> orderDetailsIterable = orderDetailRepository.findAll();
        List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetailsIterable) {
            orderDetailDTOs.add(orderDetailMapper.toDTO(orderDetail));
        }
        return orderDetailDTOs;
    }

    @Override
    public OrderDetailDTO updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id).orElse(null);
        if (existingOrderDetail == null) {
            return null; // Retorna null si no se encuentra
        }

        // Actualiza los campos
        existingOrderDetail.setQuantity(orderDetailDTO.getQuantity());

        // Asignar PurchaseOrder desde el ID en el DTO
        if (orderDetailDTO.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(orderDetailDTO.getPurchaseOrderId())
                    .orElse(null);
            if (purchaseOrder != null) {
                existingOrderDetail.setPurchaseOrder(purchaseOrder);
            }
        }

        // Guarda y retorna el OrderDetail actualizado
        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);

        // Llama a updateTotal para actualizar el total en PurchaseOrder
        PurchaseOrder purchaseOrder = existingOrderDetail.getPurchaseOrder();
        if (purchaseOrder != null) {
            purchaseOrderService.updateTotal(purchaseOrder); // Actualiza el total de la orden de compra
        }

        return orderDetailMapper.toDTO(updatedOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            orderDetailRepository.delete(orderDetail); // Solo se elimina si existe
        }
    }

    @Override
    public boolean existsById(Long id) {
        return orderDetailRepository.existsById(id); // Delegar al repositorio
    }
}
