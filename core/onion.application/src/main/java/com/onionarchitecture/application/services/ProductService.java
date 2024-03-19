package com.onionarchitecture.application.services;

import com.onionarchitecture.application.dto.product.CreateProductDto;
import com.onionarchitecture.application.dto.product.ProductDto;
import com.onionarchitecture.application.dto.product.UpdateProductDto;
import java.util.List;
public interface ProductService {

    List<ProductDto> GetAll();
    ProductDto GetById(String Id);

    ProductDto Update(UpdateProductDto updateProductDto);
    ProductDto Add(CreateProductDto productDto);
    void Delete(String Id);
}
