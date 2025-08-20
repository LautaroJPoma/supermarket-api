package com.lauty.supermarket_api.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.dto.BrandDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.service.BrandService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "Obtiene todas las marcas", description = "Obtiene todas las marcas existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marcas obtenidas correctamente", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping()
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandDTO> brands = brandService.getAllBrands();
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una marca existente", description = "Obtiene una marca existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(@PathVariable Long id) {
        BrandDTO brand = brandService.getBrandById(id);
        if (brand == null) {
            throw new ResourceNotFoundException("Marca con ID " + id + " no encontrada");
        }
        return new ResponseEntity<>(brand, HttpStatus.OK);
    }

    @Operation(summary = "Crea una nueva marca", description = "Crea una nueva marca en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca creada correctamente", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PostMapping()
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getName() == null || brandDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre de la marca no puede estar vacío");
        }
        BrandDTO createdBrand = brandService.createBrand(brandDTO);
        return new ResponseEntity<>(createdBrand, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza una marca existente", description = "Actualiza una marca existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca actualizada correctamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        if (brandDTO.getName() == null || brandDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre de la marca no puede estar vacío");
        }
        BrandDTO updatedBrand = brandService.updateBrand(id, brandDTO);
        if (updatedBrand == null) {
            throw new ResourceNotFoundException("Marca con ID " + id + " no encontrada");
        }
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una marca existente", description = "Elimina una marca existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marca eliminada correctamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
       if(!brandService.existsById(id)) {
            throw new ResourceNotFoundException("Marca con ID " + id + " no encontrada");
        }
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
