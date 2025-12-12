package com.example.uberreviewservice.controllers;

import com.example.uberreviewservice.adapters.CreateReviewDtoToReviewAdapter;
import com.example.uberreviewservice.adapters.CreateReviewDtoToReviewAdapterImpl;
import com.example.uberreviewservice.dtos.CreateReviewDto;
import com.example.uberreviewservice.dtos.ReviewDto;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    private CreateReviewDtoToReviewAdapter createReviewDtoToReviewAdapter;

    public ReviewController(ReviewService reviewService, CreateReviewDtoToReviewAdapter createReviewDtoToReviewAdapter) {
        this.reviewService = reviewService;
        this.createReviewDtoToReviewAdapter = createReviewDtoToReviewAdapter;
    }

    @PostMapping
    public ResponseEntity<?> createReview(@Validated @RequestBody CreateReviewDto review) {
        Review incomingReview = this.createReviewDtoToReviewAdapter.convertDtoToReview(review);
        if (incomingReview == null) {
            return new ResponseEntity<>("Invalid arguments", HttpStatus.BAD_REQUEST);
        }
        Review savedReview = reviewService.publishReview(incomingReview);
        ReviewDto response = ReviewDto.builder().
                id(savedReview.getId()).
                content(savedReview.getContent()).
                rating(savedReview.getRating()).
                createdAt(savedReview.getCreatedAt())
                .updatedAt(savedReview.getUpdatedAt())
                .booking(savedReview.getBooking().getId()).
                build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Optional<Review> review = reviewService.findReviewById(id);

        return review.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        boolean isDeleted = reviewService.deleteReviewById(id);

        if (isDeleted) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }
}