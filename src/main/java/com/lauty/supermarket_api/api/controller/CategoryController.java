package com.lauty.supermarket_api.api.controller;

import java.util.List;
import com.lauty.supermarket_api.api.dto.CategoryDTO;
import com.lauty.supermarket_api.api.exception.BadRequestException;
import com.lauty.supermarket_api.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lauty.supermarket_api.api.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Obtiene todas las categorias", description = "Obtiene todas las categorias existentes en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una categoría por su ID", description = "Obtiene una categoría existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        if (category == null) {
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada");
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Operation(summary = "Crea una nueva categoría", description = "Crea una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría creada correctamente", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre de la categoría no puede estar vacío");
        }
        if (categoryDTO.getDescription() == null || categoryDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripción de la categoría no puede estar vacía");
        }
        CategoryDTO newCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "Actualiza una categoría existente", description = "Actualiza los datos de una categoría existente utilizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getName().isEmpty()) {
            throw new BadRequestException("El nombre de la categoría no puede estar vacío");
        }
        if (categoryDTO.getDescription() == null || categoryDTO.getDescription().isEmpty()) {
            throw new BadRequestException("La descripción de la categoría no puede estar vacía");
        }

        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);

        if (updatedCategory == null) {
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada");
        }
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una categoría existente", description = "Elimina una categoría existente utlizando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada", content = @Content()),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            throw new ResourceNotFoundException("Categoría con ID " + id + " no encontrada");
        }
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
