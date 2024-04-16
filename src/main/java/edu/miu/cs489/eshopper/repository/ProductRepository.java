package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Product;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductsByName(String name);

    List<ProductResponseDto> findProductsByRating(double rating);
}
