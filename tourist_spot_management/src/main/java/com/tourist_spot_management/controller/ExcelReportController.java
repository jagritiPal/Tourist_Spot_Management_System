package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.BookingDTO;
import com.tourist_spot_management.service.BookingService;
import com.tourist_spot_management.util.ExcelReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ExcelReportController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ExcelReportService excelReportService;

    // http://localhost:8080/api/reports/download
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadExcel() {
        try {
            // Retrieve all bookings
            List<BookingDTO> bookings = bookingService.getAllBookings();

            // Generate the Excel file
            excelReportService.generateExcel(bookings);

            // Define the path to the generated Excel file
            Path path = Paths.get("Booking_Report.xlsx");

            // Check if file exists
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // Load the file as a ByteArrayResource
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Booking_Report.xlsx");
            headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

            // Return the file as a downloadable response
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
