package com.example.demoEmail.dto.request;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String otp;
}
