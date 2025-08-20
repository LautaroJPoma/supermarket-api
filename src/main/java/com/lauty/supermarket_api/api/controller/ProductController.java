package com.lauty.supermarket_api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lauty.supermarket_api.api.dto.ProductDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;
import com.lauty.supermarket_api.api.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Obtiene todos los productos", description = "Obtiene todos los productos existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos obstenidos correctamente", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un producto existente", description = "Obtiene un producto existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        if (product == null) {
            throw new ResourceNotFoundException("Producto con ID " + id + " no encontrado");
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene productos por marca", description = "Obtiene todos los productos de una marca específica utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos obtenidos correctamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping("/brand/{brandId}")
    public ResponseEntity<List<ProductDTO>> getProductsByBrand(@PathVariable Long brandId) {
        List<ProductDTO> products = productService.getProductsByBrand(brandId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo producto", description = "Crea un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProduct = productService.createProduct(productDTO);
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }

        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripcion del producto es obligatoria");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0");
        }

        if (productDTO.getCategoryId() == null) {
            throw new BadRequestException("El ID de la categoria del producto es obligatorio");
        }

        if (productDTO.getQuantity() == null || productDTO.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza un producto existente", description = "Actualiza los datos de un producto existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        if (!productService.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de orden con ID " + id + " no encontrado");
        }

        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre del producto es obligatorio");
        }

        if (productDTO.getDescription() == null || productDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripcion del producto es obligatoria");
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new BadRequestException("El precio debe ser mayor a 0");
        }

        if (productDTO.getCategoryId() == null) {
            throw new BadRequestException("El ID de la categoria del producto es obligatorio");
        }

        if (productDTO.getQuantity() == null || productDTO.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un producto existente", description = "Elimina un producto existente utilizando su ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.existsById(id)) {
            throw new ResourceNotFoundException("Detalle de orden con ID " + id + " no encontrado");
        }
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
