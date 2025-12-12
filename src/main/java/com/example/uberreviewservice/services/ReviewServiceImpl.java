package com.example.uberreviewservice.services;

import com.example.uberreviewservice.exception.ReviewNotFoundException;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.ReviewRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        // Optional handles "not found" naturally, so no exception needed here.
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    @Transactional
    public boolean deleteReviewById(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        // Instead of throwing an exception, we return false because
        // the interface specifically asks for a boolean return type.
        return false;
    }

    @Override
    @Transactional
    public Review publishReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review updateReview(Long id, Review review) {
        // 1. Check if the review exists
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID " + id + " not found"));

        // 2. Update the existing review's details
        // Assuming your Review model has these setters. Update fields as necessary.
        if (review.getContent() != null) {
            existingReview.setContent(review.getContent());
        }
        if (review.getRating() != null) {
            existingReview.setRating(review.getRating());
        }

        // 3. Save the updated entity
        // We save 'existingReview' to ensure we update the row, not create a new one.
        return reviewRepository.save(existingReview);
    }
}


//
//@Service
//public class ReviewService implements CommandLineRunner {
//
//    private final ReviewRepository reviewRepository;
//
//    private final BookingRepository bookingRepository; // This field is declared but was not injected
//    private final DriverRepository driverRepository;
//
//    // Constructor Injection for both ReviewRepository and BookingRepository
//    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository, DriverRepository driverRepository) {
//        this.reviewRepository = reviewRepository;
//        this.bookingRepository = bookingRepository;// Injecting and assigning BookingRepository
//        this.driverRepository = driverRepository;
//    }
//
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
////        System.out.println("***************************");
////
////        // 1. Create and save a Review
////        Review r = Review.builder().content("Amazing ride quality").rating(5.0).build();
//////        reviewRepository.save(r);
////
////        // 2. Create a Booking, associate it with the Review, and save it
////        Booking b = Booking.builder()
////                .endTime(new Date())
////                .startTime(new Date())
////                .totalDistance(18l)
////                .review(r) // Associating the Review with the Booking
////                .build();
////        bookingRepository.save(b); // Saving the Booking using the injected repository
////
////        System.out.println("Saved Review ID: " + r.getId());
////        System.out.println("Saved Booking ID: " + b.getId());
////
////        // 3. Find and print all Reviews
////        List<Review> reviews = reviewRepository.findAll();
////        for (Review r1 : reviews) {
////            System.out.println("Found Review Content: " + r1.getContent());
////        }
////
////        System.out.println("***************************");
//
//
////        Optional<Booking> b=bookingRepository.findById(2l);
////        if(b.isPresent()) {
////            bookingRepository.delete(b.get());
////        }
//
////        Optional<Driver> driver = driverRepository.findById(1l);
////        if(driver.isPresent()) {
////            System.out.println(driver.get().getName());
////            List<Booking> bookings=driver.get().getBookings();
////
////            for(Booking booking:bookings) {
////                System.out.println(booking.getId());
////            }
////         }
//
////        Optional<Booking> b = bookingRepository.findById(1l);
////        if(b.isPresent()) {
////            System.out.println(b.get().getDriver().getName());
////        }
//
//
////        Driver d = driverRepository.hqlFindByIdAndLicense(1l, "ABC123456");
////        System.out.println(d.getName());
//
//
//        List<Long> driverIds = new ArrayList<>(Arrays.asList(1L, 2L,3L,5L,6L,7L,8L));
//        List<Driver> drivers=driverRepository.findAllByIdIn(driverIds);
////        List<Booking> bookings=bookingRepository.findAllByDriverIn(drivers);
//
//
//        for (Driver driver : drivers) {
//            final List<Booking> bookings=driver.getBookings();
//            bookings.forEach(booking-> System.out.println(booking.getId()));
//        }
//
//
//    }
//}

