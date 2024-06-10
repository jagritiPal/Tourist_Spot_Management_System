package com.tourist_spot_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentCheckoutController {

    //http://localhost:8080/checkout
    @GetMapping("/checkout")
    public String showCheckoutPage() {
        return "payment-request";
    }

}
