package edu.miu.cs489.eshopper.service.interfaces;

import edu.miu.cs489.eshopper.model.Review;

import java.util.List;

public interface IReviewService {

//    Review addReview(Long userID, Long productId, ReviewRequestDTO review);

    List<Review> getReviewsByProductId(Long productId);

    void deleteReviewByProductIdAndId(Long productId, Long reviewId);

}