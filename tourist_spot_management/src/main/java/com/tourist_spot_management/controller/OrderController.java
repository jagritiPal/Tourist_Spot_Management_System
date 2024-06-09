package com.tourist_spot_management.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tourist_spot_management.payload.OrderDetails;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final String razorpayKeyId = "rzp_test_iqhfGZLeltoN7B";
    private final String razorpayKeySecret = "W8KWi2qZ04Ehh9zchvuVUGX1";

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDetails orderDetails) {
        try {
            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", orderDetails.getAmount());
            orderRequest.put("currency", orderDetails.getCurrency());
            orderRequest.put("receipt", UUID.randomUUID().toString());
            JSONObject notes = new JSONObject();
            notes.put(orderDetails.getNoteSubject(), orderDetails.getNoteContent());
            orderRequest.put("notes", notes);

            Order order = razorpay.orders.create(orderRequest);

            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
