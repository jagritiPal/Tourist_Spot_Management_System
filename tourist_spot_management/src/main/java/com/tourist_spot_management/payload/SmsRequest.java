package com.tourist_spot_management.payload;

public class SmsRequest {
    private String to;
    private String message;

    public SmsRequest(String phoneNumber, String s) {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
