package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.Review;
import edu.miu.cs489.eshopper.model.request.ReviewRequestDTO;

import java.util.List;

public interface IReviewService {

    Review addReview(Long userID, Long productId, ReviewRequestDTO review);

    List<Review> getReviewsByProductId(Long productId);

    void deleteReviewByProductIdAndId(Long productId, Long reviewId);

    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();
}