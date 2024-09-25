package com.example.demoEmail.dto.sdi;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
public class ClientSdi {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
//    private LocalDate dob;
}
