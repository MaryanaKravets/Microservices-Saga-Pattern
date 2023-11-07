package com.example.restoranservice.service;

import com.example.restoranservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "warehouse-service")
public interface ProductServiceClient {

    @GetMapping("/product")
    List<Product> getProducts();

    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable String id);
}
