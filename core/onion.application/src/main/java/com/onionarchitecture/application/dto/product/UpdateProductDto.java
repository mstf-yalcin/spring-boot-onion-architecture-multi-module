package com.onionarchitecture.application.dto.product;
public record UpdateProductDto(String Id,
                               String name,
                               String description,
                               double price,
                               int stockQuantity) {
}
