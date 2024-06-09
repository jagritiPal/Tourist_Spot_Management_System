package com.tourist_spot_management.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.tourist_spot_management.payload.BookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

@Service
public class PdfTicketService {

    @Autowired
    private JavaMailSender mailSender;

    public void generateAndSendTicket(BookingDTO bookingDTO) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Add content to the PDF
            document.add(new Paragraph("Booking Details"));
            document.add(new Paragraph("Booking ID: " + bookingDTO.getBookingId()));
            document.add(new Paragraph("User ID: " + bookingDTO.getUserId()));
            document.add(new Paragraph("Spot ID: " + bookingDTO.getSpotId()));
            document.add(new Paragraph("Booking Date: " + bookingDTO.getBookingDate()));
            document.add(new Paragraph("Status: " + bookingDTO.getStatus()));
            document.add(new Paragraph("Price: " + bookingDTO.getPrice()));
            // Add more details as needed

            document.close();

            // Save the PDF to a byte array
            byte[] pdfBytes = outputStream.toByteArray();

            // Send the PDF as an email attachment
            sendTicketByEmail(pdfBytes, bookingDTO.getEmail());
        } catch (DocumentException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void sendTicketByEmail(byte[] pdfBytes, String userEmail) {
        // Create a MimeMessage
        MimeMessage message = mailSender.createMimeMessage();

        try {
            // Enable multipart mode
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set recipient, subject, and body
            helper.setTo(userEmail);
            helper.setSubject("Your Booking Ticket");
            helper.setText("Please find attached your booking ticket.");

            // Attach the PDF
            helper.addAttachment("Booking_Ticket.pdf", new ByteArrayResource(pdfBytes));

            // Send the email
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }
}
