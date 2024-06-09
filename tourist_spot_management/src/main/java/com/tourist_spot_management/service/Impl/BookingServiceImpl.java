package com.tourist_spot_management.service.Impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tourist_spot_management.controller.OrderController;
import com.tourist_spot_management.entity.Booking;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.BookingDTO;
import com.tourist_spot_management.payload.OrderDetails;
import com.tourist_spot_management.repository.BookingRepository;
import com.tourist_spot_management.service.BookingService;
import com.tourist_spot_management.util.ExcelReportService;
import com.tourist_spot_management.util.PdfTicketService;
import com.tourist_spot_management.util.SmsNotificationService;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PdfTicketService pdfTicketService;

    @Autowired
    private SmsNotificationService smsNotificationService;

    @Autowired
    private ExcelReportService excelReportService;

    @Autowired
    private OrderController orderController;


    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = mapToEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);
        BookingDTO savedBookingDTO = mapToDto(savedBooking);

        // Generate and send PDF ticket via email
        pdfTicketService.generateAndSendTicket(savedBookingDTO);

        // Sms Notification
        smsNotificationService.sendSms(
                savedBookingDTO.getPhoneNumber(),
                "Your booking is confirmed. Booking ID: " + savedBookingDTO.getBookingId()
        );

        // Append new booking to the Excel file
        try {
            excelReportService.appendBookingToExcel(savedBookingDTO);
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }


        return savedBookingDTO;
    }



    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        return mapToDto(booking);
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        existingBooking.setUserId(bookingDTO.getUserId());
        existingBooking.setSpotId(bookingDTO.getSpotId());
        existingBooking.setEmail(bookingDTO.getEmail());
        existingBooking.setBookingDate(bookingDTO.getBookingDate());
        existingBooking.setStatus(bookingDTO.getStatus());
        existingBooking.setPrice(bookingDTO.getPrice());
        existingBooking.setPhoneNumber(bookingDTO.getPhoneNumber());

        Booking updatedBooking = bookingRepository.save(existingBooking);
        return mapToDto(updatedBooking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException("Booking not found with id: " + bookingId);
        }
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<BookingDTO> getBookingsBySpotId(Long spotId) {
        List<Booking> bookings = bookingRepository.findBySpotId(spotId);
        return bookings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private BookingDTO mapToDto(Booking booking) {
        return modelMapper.map(booking, BookingDTO.class);
    }

    private Booking mapToEntity(BookingDTO bookingDTO) {
        return modelMapper.map(bookingDTO, Booking.class);
    }
}
