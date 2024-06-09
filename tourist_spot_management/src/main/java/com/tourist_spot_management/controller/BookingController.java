package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.BookingDTO;
import com.tourist_spot_management.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // http://localhost:8080/api/bookings/add
    @PostMapping("/add")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO) {
        BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/bookings
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // http://localhost:8080/api/bookings/1
    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) {
        BookingDTO booking = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    // http://localhost:8080/api/bookings/1
    @PutMapping("/{bookingId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(bookingId, bookingDTO);
        return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
    }

    // http://localhost:8080/api/bookings/1
    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>("Booking is deleted successfully.", HttpStatus.OK);
    }

    // http://localhost:8080/api/bookings/spot/1
    @GetMapping("/spot/{spotId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<BookingDTO>> getBookingsBySpotId(@PathVariable Long spotId) {
        List<BookingDTO> bookings = bookingService.getBookingsBySpotId(spotId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

}
