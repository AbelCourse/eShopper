package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.model.Product;
import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import edu.miu.cs489.eshopper.repository.ProductRepository;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    //TODO: Implement the methods
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductRequestDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .build();
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductRequestDto productDto, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepository.save(product);

    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return mapList(products, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getProductByName(String name) {
        List<Product> products = productRepository.findProductsByName(name);
        return mapList(products, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getProductByRating(double rating) {
        List<ProductResponseDto> products = productRepository.findProductsByRating(rating);
        return mapList(products, ProductResponseDto.class);
    }
}
