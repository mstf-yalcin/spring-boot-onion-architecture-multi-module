package com.onionarchitecture.persistence.repositories;

import com.onionarchitecture.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,String> {
}
