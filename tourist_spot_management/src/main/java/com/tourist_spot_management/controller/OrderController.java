package com.tourist_spot_management.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.tourist_spot_management.entity.OrderEntity;
import com.tourist_spot_management.payload.OrderDetails;
import com.tourist_spot_management.service.Impl.OrderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    //http://localhost:8080/api/order
    @PostMapping
    public String createOrder(@RequestBody OrderDetails orderDetails) throws RazorpayException {

        RazorpayClient razorpay = new RazorpayClient("rzp_test_iqhfGZLeltoN7B", "W8KWi2qZ04Ehh9zchvuVUGX1");

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", orderDetails.getAmount() * 100); // Amount should be in paise
        orderRequest.put("currency", orderDetails.getCurrency());
        orderRequest.put("receipt", UUID.randomUUID().toString());

        JSONObject notes = new JSONObject();
        notes.put(orderDetails.getNoteSubject(), orderDetails.getNoteContent());
        orderRequest.put("notes", notes);

        Order order = razorpay.orders.create(orderRequest);

        // Save the order details including bookingId
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setBookingId(orderDetails.getBookingId());
        orderEntity.setAmount(orderDetails.getAmount());
        orderEntity.setCurrency(orderDetails.getCurrency());
        orderEntity.setNoteSubject(orderDetails.getNoteSubject());
        orderEntity.setNoteContent(orderDetails.getNoteContent());

        orderService.saveOrder(orderEntity);

        return order.get("id").toString();
    }
}
