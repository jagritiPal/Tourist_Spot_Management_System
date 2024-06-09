package com.tourist_spot_management.util;

import com.tourist_spot_management.payload.SmsRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public String sendSms(SmsRequest smsRequest) {
        Twilio.init(accountSid, authToken);

        Message message = Message.creator(
                        new PhoneNumber(smsRequest.getTo()),
                        new PhoneNumber("+12183216906"), // Use configured Twilio phone number
                        smsRequest.getMessage())
                .create();

        return message.getSid();
    }
}
