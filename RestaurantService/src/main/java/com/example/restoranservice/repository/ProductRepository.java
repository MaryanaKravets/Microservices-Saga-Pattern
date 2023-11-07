package com.example.restoranservice.repository;

import com.example.restoranservice.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductDTO, Long> {

    Optional<ProductDTO> findByProductId(String productId);
}
