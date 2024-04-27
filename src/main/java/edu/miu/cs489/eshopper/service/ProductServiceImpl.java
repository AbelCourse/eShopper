package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.config.Mapper;
import edu.miu.cs489.eshopper.exception.ResourceNotFoundException;
import edu.miu.cs489.eshopper.model.Product;
import edu.miu.cs489.eshopper.model.Review;
import edu.miu.cs489.eshopper.model.User;
import edu.miu.cs489.eshopper.model.request.ProductRequestDto;
import edu.miu.cs489.eshopper.model.request.ReviewRequestDTO;
import edu.miu.cs489.eshopper.model.response.ProductResponseDto;
import edu.miu.cs489.eshopper.model.response.ReviewDTO;
import edu.miu.cs489.eshopper.repository.ProductRepository;
import edu.miu.cs489.eshopper.repository.UserRepository;
import edu.miu.cs489.eshopper.service.interfaces.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, Mapper modelMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.mapper = modelMapper;
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .rating(0)
                .reviews(null)
                .quantity(productDto.getQuantity())
                .build();
        System.err.println("Product " + product);
        return mapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public String addReviewToProduct(Long userId, Long productId, ReviewRequestDTO reviewDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        double rating = reviewDTO.getRating();
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating should be between 0 and 5");
        }

        Review review = Review.builder()
                .comment(reviewDTO.getComment())
                .product(product)
                .user(user) // Associate the user with the review
                .rating(rating)
                .build();

        product.getReviews().add(review);

        double totalRating = product.getRating() * product.getNumberOfReviews();
        totalRating += rating;

        product.setReviews(product.getReviews());

        product.setRating(totalRating / product.getNumberOfReviews()); // Calculate new rating

        productRepository.save(product);

        return "Review added successfully";
    }

//    public String addReviewToProduct(Long userId, Long productId, ReviewRequestDTO reviewDTO) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
//        System.err.println("Add Review Product " + product);
//        double rating = reviewDTO.getRating();
//        if (rating < 0 || rating > 5) {
//            throw new IllegalArgumentException("Rating should be between 0 and 5");
//        }
//        Review review1 = Review.builder()
//                .comment(reviewDTO.getComment())
//                .product(mapper.map(getProduct(productId), Product.class))
//                .rating(reviewDTO.getRating())
//                .build();
//        product.getReviews().add(review1);
//        System.err.println("Current Rating " + product.getRating());
//        System.err.println("Current num of review" + product.getNumberOfReviews());
//        double totalRating = product.getRating() * product.getNumberOfReviews();
//        totalRating += rating;
//        System.err.println("Current total rating " + totalRating);
//        product.setRating(totalRating / ((product.getNumberOfReviews() + 1)));
//        productRepository.save(product);
//        return "Review added successfully";
//    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productDto, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        return mapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        var productResponseDto = new ProductResponseDto();
        productResponseDto.setId(product.getId());
        productResponseDto.setName(product.getName());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setRating(product.getRating());
        productResponseDto.setQuantity(product.getQuantity());
        var reviews = product.getReviews();
        if (reviews != null) {
            productResponseDto.setReviews(mapper.mapList(reviews, ReviewDTO.class));
        }
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return mapper.mapList(products, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getProductByName(String name) {
        List<Product> products = productRepository.findProductsByNameLike(name);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        return mapper.mapList(products, ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getProductByRating(double rating) {
        List<Product> products = productRepository.findProductsByRating(rating);
        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found");
        }
        return mapper.mapList(products, ProductResponseDto.class);
    }
}
