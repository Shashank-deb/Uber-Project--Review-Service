package com.example.uberreviewservice;

import com.example.uberreviewservice.adapters.CreateReviewDtoToReviewAdapterImpl;
import com.example.uberreviewservice.dtos.CreateReviewDto;
import com.example.uberreviewservice.exception.ResourceNotFoundException;
import com.example.uberreviewservice.models.Booking;
import com.example.uberreviewservice.models.BookingStatus;
import com.example.uberreviewservice.models.Review;
import com.example.uberreviewservice.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateReviewDtoToReviewAdapterImplTest {

    @InjectMocks
    private CreateReviewDtoToReviewAdapterImpl adapter;

    @Mock
    private BookingRepository bookingRepository;

    private CreateReviewDto createReviewDto;
    private Booking testBooking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Create booking using setters
        testBooking = new Booking();
        testBooking.setId(1L);
        testBooking.setBookingStatus(BookingStatus.COMPLETED);
        testBooking.setStartTime(new Date());
        testBooking.setEndTime(new Date());
        testBooking.setTotalDistance(100L);

        createReviewDto = new CreateReviewDto();
        createReviewDto.setBookingId(1L);
        createReviewDto.setContent("Great service!");
        createReviewDto.setRating(4.5);
    }

    @Test
    public void testConvertDtoToReview_Success() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // Act
        Review result = adapter.convertDtoToReview(createReviewDto);

        // Assert
        assertNotNull(result);
        assertEquals(createReviewDto.getContent(), result.getContent());
        assertEquals(createReviewDto.getRating(), result.getRating());
        assertNotNull(result.getBooking());
        assertEquals(testBooking.getId(), result.getBooking().getId());
        
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_BookingNotFound() {
        // Arrange
        when(bookingRepository.findById(999L)).thenReturn(Optional.empty());
        createReviewDto.setBookingId(999L);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> adapter.convertDtoToReview(createReviewDto)
        );
        
        assertEquals("Booking not found with ID: 999", exception.getMessage());
        verify(bookingRepository, times(1)).findById(999L);
    }

    @Test
    public void testConvertDtoToReview_WithDifferentRatings() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // Test with rating 5.0
        createReviewDto.setRating(5.0);
        Review result1 = adapter.convertDtoToReview(createReviewDto);
        assertEquals(5.0, result1.getRating());

        // Test with rating 1.0
        createReviewDto.setRating(1.0);
        Review result2 = adapter.convertDtoToReview(createReviewDto);
        assertEquals(1.0, result2.getRating());

        // Test with rating 0.0
        createReviewDto.setRating(0.0);
        Review result3 = adapter.convertDtoToReview(createReviewDto);
        assertEquals(0.0, result3.getRating());

        verify(bookingRepository, times(3)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_WithEmptyContent() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        createReviewDto.setContent("");

        // Act
        Review result = adapter.convertDtoToReview(createReviewDto);

        // Assert
        assertNotNull(result);
        assertEquals("", result.getContent());
        assertEquals(4.5, result.getRating());
        
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_WithLongContent() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        String longContent = "A".repeat(1000);
        createReviewDto.setContent(longContent);

        // Act
        Review result = adapter.convertDtoToReview(createReviewDto);

        // Assert
        assertNotNull(result);
        assertEquals(longContent, result.getContent());
        assertEquals(1000, result.getContent().length());
        
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_WithSpecialCharacters() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));
        createReviewDto.setContent("Great! @#$%^&*() 特殊字符 😊");

        // Act
        Review result = adapter.convertDtoToReview(createReviewDto);

        // Assert
        assertNotNull(result);
        assertEquals("Great! @#$%^&*() 特殊字符 😊", result.getContent());
        
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_MultipleCallsSameBooking() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // Act - Create multiple reviews for same booking
        Review result1 = adapter.convertDtoToReview(createReviewDto);
        
        createReviewDto.setContent("Different content");
        createReviewDto.setRating(3.0);
        Review result2 = adapter.convertDtoToReview(createReviewDto);

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals("Great service!", result1.getContent());
        assertEquals("Different content", result2.getContent());
        assertEquals(4.5, result1.getRating());
        assertEquals(3.0, result2.getRating());
        
        // Both should reference the same booking
        assertEquals(result1.getBooking().getId(), result2.getBooking().getId());
        
        verify(bookingRepository, times(2)).findById(1L);
    }

    @Test
    public void testConvertDtoToReview_NullBookingId() {
        // Arrange
        createReviewDto.setBookingId(null);

        // Act & Assert
        assertThrows(
                Exception.class,
                () -> adapter.convertDtoToReview(createReviewDto)
        );
    }

    @Test
    public void testConvertDtoToReview_VerifyBuilderUsage() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(testBooking));

        // Act
        Review result = adapter.convertDtoToReview(createReviewDto);

        // Assert - Verify all builder fields are properly set
        assertNotNull(result);
        assertNotNull(result.getBooking());
        assertNotNull(result.getContent());
        assertNotNull(result.getRating());
        
        // Verify the booking reference is the exact same object
        assertSame(testBooking, result.getBooking());
        
        verify(bookingRepository, times(1)).findById(1L);
    }
}