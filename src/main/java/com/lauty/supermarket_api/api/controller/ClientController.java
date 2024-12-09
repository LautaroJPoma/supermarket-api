package com.lauty.supermarket_api.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.dto.ClientDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(summary = "Obtiene todos los clientes", description = "Obtiene todos los clientes existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Obtiene un cliente existente", description = "Obtiene un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.getClientById(id);
        if (client == null) {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo cliente", description = "Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        if (clientDTO.getName() == null || clientDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre del cliente es obligatorio");
        }

        if (clientDTO.getEmail() == null || clientDTO.getEmail().isEmpty()) {
            throw new BadRequestException("El email del cliente no puede estar vacío");
        }
        if (!clientDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new BadRequestException("El email del cliente no es válido");
        }

        ClientDTO createdClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza un cliente existente", description = "Actualiza un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        if (clientDTO.getName() == null || clientDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre del cliente es obligatorio");
        }

        if (clientDTO.getEmail() == null || clientDTO.getEmail().isEmpty()) {
            throw new BadRequestException("El email del cliente no puede estar vacío");
        }
        if (!clientDTO.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new BadRequestException("El email del cliente no es válido");
        }

        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        if (updatedClient == null) {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un cliente existente", description = "Elimina un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (!clientService.existsById(id)) {
            throw new ResourceNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
