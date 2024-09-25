package com.example.demoEmail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class DataOtp {
    private String otp;
    private LocalDateTime expiryTime;
}
