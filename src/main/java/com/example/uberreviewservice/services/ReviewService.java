package com.example.uberreviewservice.services;

import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.BookingRepository;
import com.example.uberreviewservice.repositories.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewService implements CommandLineRunner {

    private final ReviewRepository reviewRepository;

    private final BookingRepository bookingRepository; // This field is declared but was not injected

    // Constructor Injection for both ReviewRepository and BookingRepository
    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository; // Injecting and assigning BookingRepository
    }


    @Override
    public void run(String... args) throws Exception {
//        System.out.println("***************************");
//
//        // 1. Create and save a Review
//        Review r = Review.builder().content("Amazing ride quality").rating(5.0).build();
////        reviewRepository.save(r);
//
//        // 2. Create a Booking, associate it with the Review, and save it
//        Booking b = Booking.builder()
//                .endTime(new Date())
//                .startTime(new Date())
//                .totalDistance(18l)
//                .review(r) // Associating the Review with the Booking
//                .build();
//        bookingRepository.save(b); // Saving the Booking using the injected repository
//
//        System.out.println("Saved Review ID: " + r.getId());
//        System.out.println("Saved Booking ID: " + b.getId());
//
//        // 3. Find and print all Reviews
//        List<Review> reviews = reviewRepository.findAll();
//        for (Review r1 : reviews) {
//            System.out.println("Found Review Content: " + r1.getContent());
//        }
//
//        System.out.println("***************************");


        Optional<Booking> b=bookingRepository.findById(2l);
        if(b.isPresent()) {
            bookingRepository.delete(b.get());
        }

    }
}