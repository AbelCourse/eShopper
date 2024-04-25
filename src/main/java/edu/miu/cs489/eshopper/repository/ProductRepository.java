package edu.miu.cs489.eshopper.repository;

import edu.miu.cs489.eshopper.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findProductsByNameLike(String name);

    @Query("SELECT p FROM Product p WHERE p.rating = ?1")
    List<Product> findProductsByRating(double rating);
}
