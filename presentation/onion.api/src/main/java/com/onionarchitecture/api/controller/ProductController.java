package com.onionarchitecture.api.controller;

import com.onionarchitecture.application.dto.product.CreateProductDto;
import com.onionarchitecture.application.dto.product.ProductDto;
import com.onionarchitecture.application.dto.product.UpdateProductDto;
import com.onionarchitecture.application.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {
    private final ProductService _productService;

    public ProductController(ProductService productService) {
        _productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> GetAll() {
        return ResponseEntity.ok(_productService.GetAll());
    }

    @PutMapping
    public ResponseEntity<ProductDto> Update(@RequestBody UpdateProductDto updateProductDto) {
        return ResponseEntity.ok(_productService.Update(updateProductDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> GetById(@PathVariable String id) {
        return ResponseEntity.ok(_productService.GetById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> Create(@RequestBody CreateProductDto createProductDto) {
        return ResponseEntity.ok(_productService.Add(createProductDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> Delete(@PathVariable String id) {
        _productService.Delete(id);
        return ResponseEntity.noContent().build();
    }
}
