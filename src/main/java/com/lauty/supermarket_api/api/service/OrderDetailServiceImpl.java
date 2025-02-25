package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.mapper.OrderDetailMapper;
import com.lauty.supermarket_api.api.model.OrderDetail;
import com.lauty.supermarket_api.api.model.Product;
import com.lauty.supermarket_api.api.model.PurchaseOrder;
import com.lauty.supermarket_api.api.repository.OrderDetailRepository;
import com.lauty.supermarket_api.api.repository.ProductRepository;
import com.lauty.supermarket_api.api.repository.PurchaseOrderRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    @Override
    public OrderDetailDTO createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);

        if (orderDetail.getQuantity() == null) {
            orderDetail.setQuantity(1);
        }

        PurchaseOrder purchaseOrder = null;

        if (orderDetailDTO.getPurchaseOrderId() != null) {
            purchaseOrder = purchaseOrderRepository.findById(orderDetailDTO.getPurchaseOrderId())
                    .orElse(null);
            if (purchaseOrder != null) {
                orderDetail.setPurchaseOrder(purchaseOrder);
            } else {
                throw new ResourceNotFoundException("PurchaseOrder no encontrada");
            }
        }

        if (orderDetailDTO.getProductId() != null) {
            Product product = productRepository.findById(orderDetailDTO.getProductId())
                    .orElse(null);
            if (product != null) {
                orderDetail.setProduct(product);
            } else {
                throw new ResourceNotFoundException("Producto no encontrado");
            }
        }

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        if (purchaseOrder != null) {

            purchaseOrder.getOrderDetails().add(savedOrderDetail);
            purchaseOrderService.updateTotal(purchaseOrder);
        }

        return orderDetailMapper.toDTO(savedOrderDetail);
    }

    @Override
    public OrderDetailDTO addOrderDetailToPurchaseOrder(Long purchaseOrderId, OrderDetailDTO orderDetailDTO) {

        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Orden de compra no encontrada"));

        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);

        orderDetail.setPurchaseOrder(purchaseOrder);

        if (orderDetailDTO.getProductId() != null) {
            Product product = productRepository.findById(orderDetailDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
            orderDetail.setProduct(product);
        }

        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        purchaseOrder.getOrderDetails().add(savedOrderDetail);

        purchaseOrderRepository.save(purchaseOrder);

        purchaseOrderService.updateTotal(purchaseOrder);

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
            return null;
        }

        existingOrderDetail.setQuantity(orderDetailDTO.getQuantity());

        if (orderDetailDTO.getPurchaseOrderId() != null) {
            PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(orderDetailDTO.getPurchaseOrderId())
                    .orElse(null);
            if (purchaseOrder != null) {
                existingOrderDetail.setPurchaseOrder(purchaseOrder);
            }
        }

        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);

        PurchaseOrder purchaseOrder = existingOrderDetail.getPurchaseOrder();
        if (purchaseOrder != null) {
            purchaseOrderService.updateTotal(purchaseOrder);
        }

        return orderDetailMapper.toDTO(updatedOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            orderDetailRepository.delete(orderDetail);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return orderDetailRepository.existsById(id);
    }
}
