package edu.miu.cs489.eshopper.service;

import edu.miu.cs489.eshopper.config.Mapper;
import edu.miu.cs489.eshopper.model.Product;
import edu.miu.cs489.eshopper.model.Review;
import edu.miu.cs489.eshopper.model.request.ReviewRequestDTO;
import edu.miu.cs489.eshopper.repository.ReviewRepository;
import edu.miu.cs489.eshopper.service.interfaces.IReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductServiceImpl productServiceImpl;
    private final Mapper mapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductServiceImpl productServiceImpl, Mapper mapper) {
        this.reviewRepository = reviewRepository;
        this.productServiceImpl = productServiceImpl;
        this.mapper = mapper;
    }

    @Override
    public Review addReview(Long userID, Long productId, ReviewRequestDTO review) {
        Review review1 = Review.builder()
                .comment(review.getComment())
                .product(mapper.map(productServiceImpl.getProduct(productId), Product.class))
                .rating(review.getRating())
                .build();
        reviewRepository.save(review1);
        return review1;
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public void deleteReviewByProductIdAndId(Long productId, Long reviewId) {
        reviewRepository.deleteReviewByProductIdAndId(productId, reviewId);
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }
}