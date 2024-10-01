package com.example.demoEmail.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
@Data
public class UserUpdateRequest {
    private String email;
    private String password;
    private MultipartFile imageData;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dob;


}
