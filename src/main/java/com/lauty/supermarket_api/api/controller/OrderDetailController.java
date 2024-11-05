package com.lauty.supermarket_api.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.service.OrderDetailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Long id,
            @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetailDTO updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
        return new ResponseEntity<>(updatedOrderDetail, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
