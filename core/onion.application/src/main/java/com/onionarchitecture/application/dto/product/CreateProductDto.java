package com.onionarchitecture.application.dto.product;

public record CreateProductDto(String name,
                               String description,
                               double price,
                               int stockQuantity) {
}
