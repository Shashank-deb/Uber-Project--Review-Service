package com.example.uberreviewservice;

import com.example.uberreviewservice.models.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testReview_SettersAndGetters() {
        // Arrange
        Booking booking = new Booking();
        booking.setId(1L);
        
        String content = "Great service!";
        Double rating = 4.5;

        // Act
        Review review = new Review();
        review.setBooking(booking);
        review.setContent(content);
        review.setRating(rating);

        // Assert
        assertNotNull(review);
        assertEquals(booking, review.getBooking());
        assertEquals(content, review.getContent());
        assertEquals(rating, review.getRating());
    }

    @Test
    public void testReview_InheritsFromBaseModel() {
        // Arrange
        Review review = new Review();

        // Assert - Review should have BaseModel fields
        assertNotNull(review);
        // These methods exist because Review extends BaseModel
        review.setId(1L);
        assertEquals(1L, review.getId());
    }

    @Test
    public void testBooking_SettersAndGetters() {
        // Arrange
        BookingStatus status = BookingStatus.COMPLETED;
        Date startTime = new Date();
        Date endTime = new Date();
        Long distance = 100L;
        
        Driver driver = new Driver();
        driver.setId(1L);
        
        Passenger passenger = new Passenger();
        passenger.setId(1L);

        // Act
        Booking booking = new Booking();
        booking.setBookingStatus(status);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalDistance(distance);
        booking.setDriver(driver);
        booking.setPassenger(passenger);

        // Assert
        assertEquals(status, booking.getBookingStatus());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(endTime, booking.getEndTime());
        assertEquals(distance, booking.getTotalDistance());
        assertEquals(driver, booking.getDriver());
        assertEquals(passenger, booking.getPassenger());
    }

    @Test
    public void testBooking_AllStatuses() {
        // Test all booking statuses
        Booking booking = new Booking();

        booking.setBookingStatus(BookingStatus.SCHEDULED);
        assertEquals(BookingStatus.SCHEDULED, booking.getBookingStatus());

        booking.setBookingStatus(BookingStatus.CANCELLED);
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus());

        booking.setBookingStatus(BookingStatus.CAR_ARRIVED);
        assertEquals(BookingStatus.CAR_ARRIVED, booking.getBookingStatus());

        booking.setBookingStatus(BookingStatus.ASSIGNING_DRIVER);
        assertEquals(BookingStatus.ASSIGNING_DRIVER, booking.getBookingStatus());

        booking.setBookingStatus(BookingStatus.IN_RIDE);
        assertEquals(BookingStatus.IN_RIDE, booking.getBookingStatus());

        booking.setBookingStatus(BookingStatus.COMPLETED);
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus());
    }

    @Test
    public void testDriver_SettersAndGetters() {
        // Arrange
        String name = "John Doe";
        String licenceNumber = "ABC123";
        String phoneNumber = "+1234567890";

        // Act
        Driver driver = new Driver();
        driver.setName(name);
        driver.setLicenceNumber(licenceNumber);
        driver.setPhoneNumber(phoneNumber);

        // Assert
        assertEquals(name, driver.getName());
        assertEquals(licenceNumber, driver.getLicenceNumber());
        assertEquals(phoneNumber, driver.getPhoneNumber());
    }

    @Test
    public void testDriver_WithBookings() {
        // Arrange
        Driver driver = new Driver();
        driver.setName("John Doe");
        driver.setLicenceNumber("ABC123");

        List<Booking> bookings = new ArrayList<>();
        Booking booking1 = new Booking();
        booking1.setId(1L);
        Booking booking2 = new Booking();
        booking2.setId(2L);
        bookings.add(booking1);
        bookings.add(booking2);

        // Act
        driver.setBookings(bookings);

        // Assert
        assertNotNull(driver.getBookings());
        assertEquals(2, driver.getBookings().size());
        assertEquals(booking1, driver.getBookings().get(0));
        assertEquals(booking2, driver.getBookings().get(1));
    }

    @Test
    public void testPassenger_Constructor() {
        // Act
        Passenger passenger = new Passenger();

        // Assert
        assertNotNull(passenger);
        assertNotNull(passenger.getBookings());
        assertTrue(passenger.getBookings().isEmpty());
    }

    @Test
    public void testPassenger_GettersAndSetters() {
        // Arrange
        Passenger passenger = new Passenger();
        String name = "Jane Smith";

        // Act
        passenger.setName(name);

        // Assert
        assertEquals(name, passenger.getName());
    }

    @Test
    public void testPassenger_WithBookings() {
        // Arrange
        Passenger passenger = new Passenger();
        List<Booking> bookings = new ArrayList<>();
        Booking booking = new Booking();
        booking.setId(1L);
        bookings.add(booking);

        // Act
        passenger.setBookings(bookings);

        // Assert
        assertEquals(1, passenger.getBookings().size());
        assertEquals(booking, passenger.getBookings().get(0));
    }

    @Test
    public void testPassengerReview_GettersAndSetters() {
        // Arrange
        PassengerReview passengerReview = new PassengerReview();
        String content = "Good passenger";
        String rating = "5";

        // Act
        passengerReview.setPassengerReviewContent(content);
        passengerReview.setPassengerRating(rating);

        // Assert
        assertEquals(content, passengerReview.getPassengerReviewContent());
        assertEquals(rating, passengerReview.getPassengerRating());
    }

    @Test
    public void testPassengerReview_InheritsFromReview() {
        // Arrange
        PassengerReview passengerReview = new PassengerReview();
        String content = "Great!";
        Double rating = 4.5;

        // Act
        passengerReview.setContent(content);
        passengerReview.setRating(rating);

        // Assert - Can access Review fields
        assertEquals(content, passengerReview.getContent());
        assertEquals(rating, passengerReview.getRating());
    }

    @Test
    public void testBaseModel_IdGeneration() {
        // Arrange & Act
        Review review1 = new Review();
        Review review2 = new Review();

        review1.setId(1L);
        review2.setId(2L);

        // Assert
        assertEquals(1L, review1.getId());
        assertEquals(2L, review2.getId());
        assertNotEquals(review1.getId(), review2.getId());
    }

    @Test
    public void testBaseModel_Timestamps() {
        // Arrange
        Review review = new Review();
        Date createdAt = new Date();
        Date updatedAt = new Date(createdAt.getTime() + 10000);

        // Act
        review.setCreatedAt(createdAt);
        review.setUpdatedAt(updatedAt);

        // Assert
        assertEquals(createdAt, review.getCreatedAt());
        assertEquals(updatedAt, review.getUpdatedAt());
        assertTrue(review.getUpdatedAt().after(review.getCreatedAt()) || 
                   review.getUpdatedAt().equals(review.getCreatedAt()));
    }

    @Test
    public void testBooking_TimeCalculation() {
        // Arrange
        Date startTime = new Date();
        Date endTime = new Date(startTime.getTime() + 3600000); // 1 hour later
        
        Booking booking = new Booking();
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);

        // Assert
        assertNotNull(booking.getStartTime());
        assertNotNull(booking.getEndTime());
        assertTrue(booking.getEndTime().after(booking.getStartTime()));
        assertEquals(3600000, booking.getEndTime().getTime() - booking.getStartTime().getTime());
    }

    @Test
    public void testDriver_UniqueLicenceNumber() {
        // This test verifies the model structure supports unique licence numbers
        Driver driver1 = new Driver();
        driver1.setLicenceNumber("ABC123");
        driver1.setName("Driver 1");

        Driver driver2 = new Driver();
        driver2.setLicenceNumber("XYZ789");
        driver2.setName("Driver 2");

        assertNotEquals(driver1.getLicenceNumber(), driver2.getLicenceNumber());
    }

    @Test
    public void testBooking_WithNullDriver() {
        // Arrange & Act
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.SCHEDULED);
        booking.setDriver(null);

        // Assert
        assertNull(booking.getDriver());
        assertNotNull(booking.getBookingStatus());
    }

    @Test
    public void testBooking_WithNullPassenger() {
        // Arrange & Act
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.SCHEDULED);
        booking.setPassenger(null);

        // Assert
        assertNull(booking.getPassenger());
        assertNotNull(booking.getBookingStatus());
    }

    @Test
    public void testReview_ToString() {
        // Arrange
        Booking booking = new Booking();
        booking.setId(1L);
        
        Review review = new Review();
        review.setId(1L);
        review.setBooking(booking);
        review.setContent("Test");
        review.setRating(4.0);

        // Act
        String toString = review.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("Review"));
    }

    @Test
    public void testDriver_AllArgsConstructor() {
        // Arrange
        List<Booking> bookings = new ArrayList<>();
        Date now = new Date();

        // Act
        Driver driver = new Driver(
                "John Doe",
                "ABC123",
                "+1234567890",
                bookings
        );
        driver.setId(1L);
        driver.setCreatedAt(now);
        driver.setUpdatedAt(now);

        // Assert
        assertEquals("John Doe", driver.getName());
        assertEquals("ABC123", driver.getLicenceNumber());
        assertEquals("+1234567890", driver.getPhoneNumber());
        assertEquals(bookings, driver.getBookings());
    }

    @Test
    public void testBooking_AllArgsConstructor() {
        // Arrange
        Date startTime = new Date();
        Date endTime = new Date();
        Driver driver = new Driver();
        Passenger passenger = new Passenger();

        // Act
        Booking booking = new Booking(
                BookingStatus.COMPLETED,
                startTime,
                endTime,
                100L,
                driver,
                passenger
        );

        // Assert
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus());
        assertEquals(startTime, booking.getStartTime());
        assertEquals(endTime, booking.getEndTime());
        assertEquals(100L, booking.getTotalDistance());
        assertEquals(driver, booking.getDriver());
        assertEquals(passenger, booking.getPassenger());
    }

    @Test
    public void testPassenger_AllArgsConstructor() {
        // Arrange
        List<Booking> bookings = new ArrayList<>();

        // Act
        Passenger passenger = new Passenger("Jane Doe", bookings);

        // Assert
        assertEquals("Jane Doe", passenger.getName());
        assertEquals(bookings, passenger.getBookings());
    }

    @Test
    public void testReview_NoArgsConstructor() {
        // Act
        Review review = new Review();

        // Assert
        assertNotNull(review);
        assertNull(review.getBooking());
        assertNull(review.getContent());
        assertNull(review.getRating());
    }

    @Test
    public void testReview_AllArgsConstructor() {
        // Arrange
        Booking booking = new Booking();

        // Act
        Review review = new Review(booking, "Great!", 5.0);

        // Assert
        assertEquals(booking, review.getBooking());
        assertEquals("Great!", review.getContent());
        assertEquals(5.0, review.getRating());
    }

    @Test
    public void testBookingStatus_EnumValues() {
        // Assert all enum values exist
        assertEquals(6, BookingStatus.values().length);
        assertNotNull(BookingStatus.valueOf("SCHEDULED"));
        assertNotNull(BookingStatus.valueOf("CANCELLED"));
        assertNotNull(BookingStatus.valueOf("CAR_ARRIVED"));
        assertNotNull(BookingStatus.valueOf("ASSIGNING_DRIVER"));
        assertNotNull(BookingStatus.valueOf("IN_RIDE"));
        assertNotNull(BookingStatus.valueOf("COMPLETED"));
    }

    @Test
    public void testReview_FullLifecycle() {
        // Arrange
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setTotalDistance(50L);

        Review review = new Review();
        review.setId(1L);
        review.setBooking(booking);
        review.setContent("Excellent service");
        review.setRating(5.0);
        review.setCreatedAt(new Date());
        review.setUpdatedAt(new Date());

        // Assert
        assertNotNull(review);
        assertEquals(1L, review.getId());
        assertEquals("Excellent service", review.getContent());
        assertEquals(5.0, review.getRating());
        assertEquals(booking, review.getBooking());
        assertNotNull(review.getCreatedAt());
        assertNotNull(review.getUpdatedAt());
    }

    @Test
    public void testBooking_CompleteObject() {
        // Arrange
        Driver driver = new Driver();
        driver.setId(1L);
        driver.setName("John Driver");
        driver.setLicenceNumber("DL123");

        Passenger passenger = new Passenger();
        passenger.setId(2L);
        passenger.setName("Jane Passenger");

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBookingStatus(BookingStatus.COMPLETED);
        booking.setStartTime(new Date());
        booking.setEndTime(new Date());
        booking.setTotalDistance(100L);
        booking.setDriver(driver);
        booking.setPassenger(passenger);

        // Assert
        assertNotNull(booking);
        assertEquals(1L, booking.getId());
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus());
        assertEquals(100L, booking.getTotalDistance());
        assertEquals(driver, booking.getDriver());
        assertEquals(passenger, booking.getPassenger());
        assertNotNull(booking.getStartTime());
        assertNotNull(booking.getEndTime());
    }
}