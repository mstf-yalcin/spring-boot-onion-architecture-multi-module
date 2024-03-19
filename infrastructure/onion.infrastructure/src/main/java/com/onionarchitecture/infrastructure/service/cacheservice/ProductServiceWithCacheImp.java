package com.onionarchitecture.infrastructure.service.cacheservice;

import com.onionarchitecture.application.dto.product.CreateProductDto;
import com.onionarchitecture.application.dto.product.ProductDto;
import com.onionarchitecture.application.dto.product.UpdateProductDto;
import com.onionarchitecture.application.services.ProductService;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductServiceWithCacheImp implements ProductService {

    private final ProductService _productService;
    private final String cacheKey = "products";
    private final RedisTemplate<String, Object> _redisTemplate;
    private final HashOperations<String, String, ProductDto> _hashOperations;

    public ProductServiceWithCacheImp(ProductService productService, RedisTemplate<String, Object> redisTemplate) {
        _productService = productService;
        _redisTemplate = redisTemplate;
        _hashOperations = _redisTemplate.opsForHash();
    }

    @Override
    public ProductDto Add(CreateProductDto createProductdto) {
        ProductDto productDto = _productService.Add(createProductdto);
        _redisTemplate.opsForHash().put(cacheKey, String.valueOf(productDto.Id()), productDto);
        _redisTemplate.expire(cacheKey, Duration.ofMinutes(10));
        return productDto;
    }
    @Override
    public ProductDto GetById(String id) {
        ProductDto productDto;

        if (_redisTemplate.opsForHash().hasKey(cacheKey, String.valueOf(id))) {
            productDto = _hashOperations.get(cacheKey, String.valueOf(id));
            return productDto;
        }

        List<ProductDto> producDtotList = LoadCacheFromDb();
        productDto = producDtotList.stream().filter(x -> x.Id() == id).findFirst().orElseThrow();

        return productDto;
    }

    @Override
    public void Delete(String Id) {
        _productService.Delete(Id);

        if (_redisTemplate.opsForHash().hasKey(cacheKey, String.valueOf(Id)))
            _hashOperations.delete(cacheKey, String.valueOf(Id));
    }

    @Override
    public ProductDto Update(UpdateProductDto updateProductDto) {
        ProductDto productDto = _productService.Update(updateProductDto);

        if (_redisTemplate.opsForHash().hasKey(cacheKey, String.valueOf(productDto.Id()))) {
            _redisTemplate.opsForHash().put(cacheKey, String.valueOf(productDto.Id()), productDto);
        }

        return productDto;
    }

    @Override
    public List<ProductDto> GetAll() {

        if (_redisTemplate.keys(cacheKey).isEmpty())
        {
            return LoadCacheFromDb();
        }

        List<ProductDto> productDtoList = _hashOperations.values(cacheKey);
        return productDtoList;
    }

    private List<ProductDto> LoadCacheFromDb() {
        List<ProductDto> productDtoCacheList = _productService.GetAll();
        _hashOperations.putAll(cacheKey, convertProductListToMap(productDtoCacheList));
        return productDtoCacheList;
    }

    private Map<String, ProductDto> convertProductListToMap(List<ProductDto> productList) {
        Map<String, ProductDto> productMap = new HashMap<>();
        productList.forEach(productDto -> productMap.put(String.valueOf(productDto.Id()), productDto));
        return productMap;
    }

}
