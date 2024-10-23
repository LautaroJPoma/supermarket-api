package com.lauty.supermarket_api.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.mapper.OrderDetailMapper;
import com.lauty.supermarket_api.api.model.OrderDetail;
import com.lauty.supermarket_api.api.repository.OrderDetailRepository;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    public OrderDetailServiceImpl(OrderDetailMapper orderDetailMapper, OrderDetailRepository orderDetailRepository) {
        this.orderDetailMapper = orderDetailMapper;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
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

        // Guarda y retorna el OrderDetail actualizado  
        OrderDetail updatedOrderDetail = orderDetailRepository.save(existingOrderDetail);
        return orderDetailMapper.toDTO(updatedOrderDetail);
    }


    @Override
    public void deleteOrderDetail(Long id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElse(null);
        if (orderDetail != null) {
            orderDetailRepository.delete(orderDetail); // Solo se elimina si existe
        }
    }
}
