package com.onionarchitecture.application.dto.product;


import java.io.Serializable;

public record ProductDto(String Id,
                         String name,
                         String description,
                         double price,
                         int stockQuantity
) implements Serializable {
}
