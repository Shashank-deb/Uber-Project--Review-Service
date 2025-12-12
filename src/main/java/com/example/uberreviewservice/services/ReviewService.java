package com.example.uberreviewservice.services;


import com.example.uberreviewservice.models.Review;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public interface ReviewService{

    public Optional<Review> findReviewById(Long id);

    public List<Review> findAllReviews();

    public boolean deleteReviewById(Long id);

    public Review publishReview(Review review);

    public Review updateReview(Long id, Review review);

}



