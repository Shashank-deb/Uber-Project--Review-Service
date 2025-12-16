package com.example.uberreviewservice;

import com.example.uberreviewservice.adapters.CreateReviewDtoToReviewAdapter;
import com.example.uberreviewservice.controllers.ReviewController;
import com.example.uberreviewservice.dtos.CreateReviewDto;
import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @Mock
    private CreateReviewDtoToReviewAdapter createReviewDtoToReviewAdapter;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFindReviewById_Success() {
        Review review = new Review();
        review.setId(1L);

        when(reviewService.findReviewById(1L))
                .thenReturn(Optional.of(review));

        ResponseEntity<Review> response =
                reviewController.getReviewById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    public void createReview_Success() {
        CreateReviewDto requestDTO=new CreateReviewDto();

        Booking booking=new Booking();
        booking.setId(1L);
        requestDTO.setBookingId(booking.getId());

        Review incomingReview = Review.builder().content("Test review content").rating(4.5).booking(booking).build();
        when(createReviewDtoToReviewAdapter.convertDtoToReview(requestDTO)).thenReturn(incomingReview);
    }

}
