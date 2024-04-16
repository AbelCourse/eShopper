package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;

import java.util.List;

public interface IProductService {

    void addProduct(ProductRequestDto productDto);

    void updateProduct(ProductRequestDto productDto, Long id);

    void deleteProductById(Long id);

    ProductResponseDto getProduct(Long id);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> getProductByName(String name);

    List<ProductResponseDto> getProductByRating(double rating);


}
