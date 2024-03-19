package com.onionarchitecture.application.services.imp;

import com.onionarchitecture.application.dto.product.CreateProductDto;
import com.onionarchitecture.application.dto.product.ProductDto;
import com.onionarchitecture.application.dto.product.UpdateProductDto;
import com.onionarchitecture.application.exceptions.NotFoundException;
import com.onionarchitecture.application.mapper.ProductMapper;
import com.onionarchitecture.application.repositories.ProductRepository;
import com.onionarchitecture.application.services.ProductService;
import com.onionarchitecture.domain.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository _productRepository;
    private final ProductMapper _mapper;

    public ProductServiceImp(ProductRepository productRepository, ProductMapper mapper) {
        _productRepository = productRepository;
        _mapper = mapper;
    }

    @Override
    public List<ProductDto> GetAll() {
        waitSec();
        List<Product> listProducts = _productRepository.findAll();
        List<ProductDto> listProductDto = listProducts.stream().map(x -> _mapper.MapToProductDto(x)).collect(Collectors.toList());
        return listProductDto;
    }

    @Override
    public ProductDto GetById(String Id) {
        Product product = FindById(Id);
        ProductDto produtDto = _mapper.MapToProductDto(product);
        return produtDto;
    }

    @Override
    public ProductDto Update(UpdateProductDto updateProductDto) {
        Product product = FindById(updateProductDto.Id());

        product.setName(updateProductDto.name());
        product.setDescription(updateProductDto.description());
        product.setPrice(updateProductDto.price());
        product.setStockQuantity(updateProductDto.stockQuantity());

        _productRepository.save(product);

        ProductDto productDto = _mapper.MapToProductDto(product);
        return productDto;
    }

    @Override
    public ProductDto Add(CreateProductDto productDto) {
        Product product = _productRepository.save(_mapper.MapToProduct(productDto));
        return _mapper.MapToProductDto(product);

    }

    @Override
    public void Delete(String Id) {
        Product product = FindById(Id);
        _productRepository.delete(product);
    }

    private Product FindById(String Id) {
        waitSec();
        return _productRepository.findById(Id).orElseThrow(() -> new NotFoundException("Product (" + Id + ") not found."));
    }

    public void waitSec() {
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {

        }
    }


}
