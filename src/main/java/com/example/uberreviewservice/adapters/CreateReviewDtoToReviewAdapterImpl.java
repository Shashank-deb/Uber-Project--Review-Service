package com.example.uberreviewservice.adapters;

import com.example.uberreviewservice.dtos.CreateReviewDto;
import com.example.uberreviewservice.exception.ResourceNotFoundException;
import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.BookingRepository;
import org.springframework.stereotype.Component;


@Component
public class CreateReviewDtoToReviewAdapterImpl implements CreateReviewDtoToReviewAdapter {
    private BookingRepository bookingRepository;

    public CreateReviewDtoToReviewAdapterImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review convertDtoToReview(CreateReviewDto createReviewDto) {

        Booking booking = bookingRepository.findById(createReviewDto.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with ID: " + createReviewDto.getBookingId()));


        Review review = Review.builder()
                .booking(booking) // Pass the extracted Booking object
                .content(createReviewDto.getContent())
                .rating(createReviewDto.getRating())
                .build();

        return review;
    }
}
