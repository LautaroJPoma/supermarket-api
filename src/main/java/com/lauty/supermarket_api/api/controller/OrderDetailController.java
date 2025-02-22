package com.lauty.supermarket_api.api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.dto.OrderDetailDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.service.OrderDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Obtiene todos los detalles de órdenes", description = "Obtiene todos los detalles de órdenes existentes en la base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalles de orden encontrados", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetails() {
        List<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un detalle de orden existente", description = "Obtiene un detalle de orden existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de orden encontrado", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Detalle de orden no encontrado", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        OrderDetailDTO orderDetail = orderDetailService.getOrderDetailById(id);
        if (orderDetail == null) {
            throw new ResourceNotFoundException("Detalle de orden con ID " + id + " no encontrado");
        }
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo detalle de orden", description = "Crea un nuevo detalle de orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Detalle de orden creado exitosamente", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PostMapping
    public ResponseEntity<OrderDetailDTO> createOrderDetail(@RequestBody OrderDetailDTO orderDetailDTO) {
        // Validaciones
        if (orderDetailDTO.getQuantity() == null || orderDetailDTO.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        if (orderDetailDTO.getPurchaseOrderId() == null) {
            throw new BadRequestException("El ID de la orden de compra es obligatorio");
        }

        if (orderDetailDTO.getProductPrice() == null || orderDetailDTO.getProductPrice() <= 0) {
            throw new BadRequestException("El precio del producto debe ser mayor a 0");
        }

        if (orderDetailDTO.getProductId() == null) {
            throw new BadRequestException("El ID del producto es obligatorio");
        }

        OrderDetailDTO createdOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza un detalle de orden existente", description = "Actualiza un detalle de orden existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de orden actualizado exitosamente", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Detalle de orden no encontrado", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailDTO orderDetailDTO) {
        if (!orderDetailService.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de orden con ID " + id + " no encontrado");
        }

        // Validaciones
        if (orderDetailDTO.getQuantity() == null || orderDetailDTO.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        if (orderDetailDTO.getProductPrice() == null || orderDetailDTO.getProductPrice() <= 0) {
            throw new BadRequestException("El precio del producto debe ser mayor a 0");
        }

        OrderDetailDTO updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
        return new ResponseEntity<>(updatedOrderDetail, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un detalle de orden existente", description = "Elimina un detalle de orden existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Detalle de orden eliminado exitosamente", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Detalle de orden no encontrado", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        if (!orderDetailService.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de orden con ID " + id + " no encontrado");
        }
        orderDetailService.deleteOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
