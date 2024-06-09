package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.BookingDTO;

import java.util.List;

public interface BookingService {

    BookingDTO createBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAllBookings();
    BookingDTO getBookingById(Long bookingId);
    BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO);
    void deleteBooking(Long bookingId);
    List<BookingDTO> getBookingsBySpotId(Long spotId);
}
