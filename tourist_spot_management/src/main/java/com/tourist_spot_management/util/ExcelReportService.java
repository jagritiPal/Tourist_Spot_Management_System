package com.tourist_spot_management.util;

import com.tourist_spot_management.payload.BookingDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ExcelReportService {

    private static final String FILE_PATH = "Booking_Report.xlsx";

    public void generateExcel(List<BookingDTO> bookings) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Booking Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Booking ID", "User ID", "Spot ID", "Email", "Booking Date", "Status", "Price"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Add booking data
            int rowNum = 1;
            for (BookingDTO booking : bookings) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(booking.getBookingId());
                row.createCell(1).setCellValue(booking.getUserId());
                row.createCell(2).setCellValue(booking.getSpotId());
                row.createCell(3).setCellValue(booking.getEmail());
                row.createCell(4).setCellValue(booking.getBookingDate());
                row.createCell(5).setCellValue(booking.getStatus());
                row.createCell(6).setCellValue(booking.getPrice());
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
                workbook.write(fileOut);
            }
        }
    }

    public void appendBookingToExcel(BookingDTO booking) throws IOException {
        Workbook workbook;
        Sheet sheet;

        // Load existing workbook or create a new one
        if (Files.exists(Paths.get(FILE_PATH))) {
            workbook = WorkbookFactory.create(Files.newInputStream(Paths.get(FILE_PATH)));
            sheet = workbook.getSheetAt(0);
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Booking Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Booking ID", "User ID", "Spot ID", "Email", "Booking Date", "Status", "Price"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
        }

        // Add new booking data
        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(booking.getBookingId());
        row.createCell(1).setCellValue(booking.getUserId());
        row.createCell(2).setCellValue(booking.getSpotId());
        row.createCell(3).setCellValue(booking.getEmail());
        row.createCell(4).setCellValue(booking.getBookingDate());
        row.createCell(5).setCellValue(booking.getStatus());
        row.createCell(6).setCellValue(booking.getPrice());

        // Write to file
        try (FileOutputStream fileOut = new FileOutputStream(FILE_PATH)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}
