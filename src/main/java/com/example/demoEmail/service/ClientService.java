package com.example.demoEmail.service;

import com.example.demoEmail.dto.sdi.ClientSdi;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {
    Boolean create(ClientSdi sdi);
    String layOtp(String email);
    boolean verifyOtp(String email, String inputOtp);
}
