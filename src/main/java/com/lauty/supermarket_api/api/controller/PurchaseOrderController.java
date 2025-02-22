package com.lauty.supermarket_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.dto.PurchaseOrderDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.service.PurchaseOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Operation(summary = "Obtiene todas las órdenes de compra", description = "Obtiene todas las órdenes de compra existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ordenes de compra obtenidas correctamente", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<PurchaseOrderDTO>> getAllPurchaseOrders() {
        List<PurchaseOrderDTO> purchaseOrders = purchaseOrderService.getAllPurchaseOrders();
        return new ResponseEntity<>(purchaseOrders, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una orden de compra por su ID", description = "Obtiene una orden de compra utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden de compra encontrada", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
})
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseOrderDTO purchaseOrder = purchaseOrderService.getPurchaseOrderById(id);
        if (purchaseOrder == null) {
            throw new ResourceNotFoundException("Orden de compra con ID " + id + " no encontrada");
        }
        return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
    }

    @Operation(summary = "Crea una nueva orden de compra", description = "Crea una nueva orden de compra")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orden de compra creada exitosamente", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida ", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
})
    @PostMapping
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        // Validación básica de clientId (suponiendo que el clientId es obligatorio)
        if (purchaseOrderDTO.getClientId() == null) {
            throw new BadRequestException("El ID del cliente es obligatorio");
        }

        // Aquí, el total se calcularía automáticamente en el servicio, basado en los detalles de la orden
        PurchaseOrderDTO createdPurchaseOrder = purchaseOrderService.createPurchaseOrder(purchaseOrderDTO);
        return new ResponseEntity<>(createdPurchaseOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza una orden de compra existente", description = "Actualiza los datos de una orden de compra existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orden de compra actualizada correctamente", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(
            @PathVariable Long id, @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        // Validación de existencia de la orden de compra antes de intentar actualizarla
        if (!purchaseOrderService.existsById(id)) {
            throw new ResourceNotFoundException("Orden de compra con ID " + id + " no encontrada");
        }

        // Aquí, el total se actualizaría automáticamente en el servicio al recibir la nueva información
        PurchaseOrderDTO updatedPurchaseOrder = purchaseOrderService.updatePurchaseOrder(id, purchaseOrderDTO);
        return new ResponseEntity<>(updatedPurchaseOrder, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una orden de compra existente", description = "Elimina una orden de compra existente utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Orden de compra eliminada correctamente", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Orden de compra no encontrada", content = @Content()),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchaseOrder(@PathVariable Long id) {
        if (!purchaseOrderService.existsById(id)) {
            throw new ResourceNotFoundException("Orden de compra con ID " + id + " no encontrada");
        }
        purchaseOrderService.deletePurchaseOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
