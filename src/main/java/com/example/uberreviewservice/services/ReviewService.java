package com.example.uberreviewservice.services;

import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.ReviewRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewService implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("***************************");
        Review review= Review.builder().content("Amazon ride quality").rating(5.0).build();
        reviewRepository.save(review);
        System.out.println(review.getId());
        List<Review> reviews=reviewRepository.findAll();
        for(Review r:reviews){
            System.out.println(r.getContent());
        }
    }
}
