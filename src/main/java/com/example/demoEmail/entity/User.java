package com.example.demoEmail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;
    private String email;
    private String username;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private LocalDate dob;

}
