package edu.miu.cs489.eshopper;

import edu.miu.cs489.eshopper.model.Review;
import edu.miu.cs489.eshopper.repository.ReviewRepository;
import edu.miu.cs489.eshopper.service.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class ReviewService_UT {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void testGetReviewByIdFails() {
        long reviewId = 1L;
        Review review = new Review();
        review.setId(reviewId);
        review.setRating(5);
        review.setComment("Great product");

        Mockito.when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Review review1 = reviewService.getReviewById(reviewId);
        assertEquals(2L, review1.getId());
    }

    @Test
    public void testGetReviewById() {
        long reviewId = 1L;
        Review review = new Review();
        review.setId(reviewId);
        review.setRating(5);
        review.setComment("Great product");

        Mockito.when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        Review review1 = reviewService.getReviewById(reviewId);
        assertEquals(1L, review1.getId());
    }

    @Test
    public void testGetAllReviews() {
        Review review1 = new Review();
        review1.setId(1L);
        review1.setRating(5);
        review1.setComment("Great product");

        Review review2 = new Review();
        review2.setId(2L);
        review2.setRating(4);
        review2.setComment("Good product");

        Mockito.when(reviewRepository.findAll()).thenReturn(java.util.List.of(review1, review2));

        assertEquals(2, reviewService.getAllReviews().size());
    }

    @Test
    public void testGetInvalidReviewById() {
        long reviewId = 1999L;
        Mockito.when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        Review review = reviewService.getReviewById(reviewId);
        assertNull(review);

    }

}
