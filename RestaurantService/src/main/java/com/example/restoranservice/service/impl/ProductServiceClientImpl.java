package com.example.restoranservice.service.impl;

import com.example.restoranservice.model.Product;
import com.example.restoranservice.service.ProductServiceClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceClientImpl{

    private final CacheManager cacheManager;
    private final ProductServiceClient productServiceClient;

    @CircuitBreaker(name = "warehouse-service", fallbackMethod = "getProductsFallback")
    public List<Product> getProducts() {
        return productServiceClient.getProducts();
    }

    @CircuitBreaker(name = "warehouse-service", fallbackMethod = "getProductByIdFallback")
    @CachePut(cacheNames = "products", key = "#id")
    public Product getProductById(@PathVariable String id){
        return productServiceClient.getProductById(id);
    }

    List<Product> getProductsFallback(Exception ex) {
        return new ArrayList<>();
    }

    //called when feign client failed when fetch products
    // 1.need to be in the same class, interface as getProducts method
    // 2. the same method name that specified as fallbackMethod
    // 3. the same return type as getProducts method and signature
    // 4. additional arg is error obj (it can be different fallback methods with diff error obj type. it will be called method with more closed error type)
    Product getProductByIdFallback(String id, Exception ex){
        System.out.println("FALLBACK METHOD");
        if (ex instanceof HttpClientErrorException || ex instanceof ResponseStatusException){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        Cache.ValueWrapper product = cacheManager.getCache("products").get(id);
        return Objects.nonNull(product) ? (Product) product.get() : null;
    }
}
