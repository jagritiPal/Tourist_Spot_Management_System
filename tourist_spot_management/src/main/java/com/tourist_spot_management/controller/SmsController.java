package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.SmsRequest;
import com.tourist_spot_management.util.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    // http://localhost:8080/api/sms/send
    @PostMapping("/send")
    public String sendSms(@RequestBody SmsRequest smsRequest) {
        return smsService.sendSms(smsRequest);
    }
}
