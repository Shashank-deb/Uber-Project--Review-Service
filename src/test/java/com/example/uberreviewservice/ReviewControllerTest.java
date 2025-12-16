package com.example.uberreviewservice;

import com.example.uberreviewservice.adapters.CreateReviewDtoToReviewAdapter;
import com.example.uberreviewservice.controllers.ReviewController;
import com.example.uberreviewservice.dtos.CreateReviewDto;
import com.example.uberreviewservice.dtos.ReviewDto;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @Mock
    private CreateReviewDtoToReviewAdapter createReviewDtoToReviewAdapter;

    private Review testReview;

    private Booking testBooking;

    private CreateReviewDto createReviewDto;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        //Setup Booking
        testBooking=new Booking();
        testBooking.setId(1L);
        testBooking.setTotalDistance(100l);

        //Setup Review
        testReview=new Review();
        testReview.setId(2L);
        testReview.setContent("Great service");
        testReview.setRating(4.5);
        testReview.setBooking(testBooking);
        testReview.setCreatedAt(new Date());
        testReview.setUpdatedAt(new Date());


        //Setup DTO
        createReviewDto=new CreateReviewDto();
        createReviewDto.setContent("Great service");
        createReviewDto.setRating(4.5);
        createReviewDto.setBookingId(1l);

    }


    @Test
    public void testCreateReview_Success() {
        // Arrange
        when(createReviewDtoToReviewAdapter.convertDtoToReview(any(CreateReviewDto.class)))
                .thenReturn(testReview);
        when(reviewService.publishReview(any(Review.class)))
                .thenReturn(testReview);

        // Act
        ResponseEntity<?> response = reviewController.createReview(createReviewDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ReviewDto);

        ReviewDto reviewDto = (ReviewDto) response.getBody();
        assertEquals(testReview.getId(), reviewDto.getId());
        assertEquals(testReview.getContent(), reviewDto.getContent());
        assertEquals(testReview.getRating(), reviewDto.getRating());
        assertEquals(testReview.getBooking().getId(), reviewDto.getBooking());

        verify(createReviewDtoToReviewAdapter, times(1)).convertDtoToReview(any(CreateReviewDto.class));
        verify(reviewService, times(1)).publishReview(any(Review.class));
    }

    @Test
    public void testCreateReview_NullReview() {
        // Arrange
        when(createReviewDtoToReviewAdapter.convertDtoToReview(any(CreateReviewDto.class)))
                .thenReturn(null);

        // Act
        ResponseEntity<?> response = reviewController.createReview(createReviewDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid arguments", response.getBody());
        verify(reviewService, never()).publishReview(any(Review.class));
    }

    @Test
    public void testFindReviewById_Success() {
        // Arrange
        when(reviewService.findReviewById(1L))
                .thenReturn(Optional.of(testReview));

        // Act
        ResponseEntity<Review> response = reviewController.getReviewById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testReview.getId(), response.getBody().getId());
        assertEquals(testReview.getContent(), response.getBody().getContent());

        verify(reviewService, times(1)).findReviewById(1L);
    }

    @Test
    public void testFindReviewById_NotFound() {
        // Arrange
        when(reviewService.findReviewById(999L))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<Review> response = reviewController.getReviewById(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(reviewService, times(1)).findReviewById(999L);
    }

    @Test
    public void testGetAllReviews_Success() {
        // Arrange
        Review review2 = new Review();
        review2.setId(2L);
        review2.setContent("Excellent!");
        review2.setRating(5.0);
        review2.setBooking(testBooking);

        List<Review> reviews = Arrays.asList(testReview, review2);
        when(reviewService.findAllReviews()).thenReturn(reviews);

        // Act
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(reviewService, times(1)).findAllReviews();
    }

    @Test
    public void testGetAllReviews_EmptyList() {
        // Arrange
        when(reviewService.findAllReviews()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<Review>> response = reviewController.getAllReviews();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());

        verify(reviewService, times(1)).findAllReviews();
    }

    @Test
    public void testUpdateReview_Success() {
        // Arrange
        Review updatedReview = new Review();
        updatedReview.setId(1L);
        updatedReview.setContent("Updated content");
        updatedReview.setRating(4.0);
        updatedReview.setBooking(testBooking);

        when(reviewService.updateReview(eq(1L), any(Review.class)))
                .thenReturn(updatedReview);

        // Act
        ResponseEntity<Review> response = reviewController.updateReview(1L, updatedReview);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedReview.getContent(), response.getBody().getContent());
        assertEquals(updatedReview.getRating(), response.getBody().getRating());

        verify(reviewService, times(1)).updateReview(eq(1L), any(Review.class));
    }

    @Test
    public void testDeleteReview_Success() {
        // Arrange
        when(reviewService.deleteReviewById(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = reviewController.deleteReview(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review deleted successfully", response.getBody());

        verify(reviewService, times(1)).deleteReviewById(1L);
    }

    @Test
    public void testDeleteReview_NotFound() {
        // Arrange
        when(reviewService.deleteReviewById(999L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = reviewController.deleteReview(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Review not found", response.getBody());

        verify(reviewService, times(1)).deleteReviewById(999L);
    }
}





