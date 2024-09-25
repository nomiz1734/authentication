package com.example.demoEmail.controller;

import com.example.demoEmail.dto.sdi.ClientSdi;
import com.example.demoEmail.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    //gui mail den email duoc gui kem
    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> create(@RequestBody ClientSdi sdi) {
        return ResponseEntity.ok(clientService.create(sdi));
    }

    //get otp theo email
    @GetMapping("/{email}")
    public String getOtp(@PathVariable("email") String email){
        return clientService.layOtp(email);
    }

    //check otp
    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = clientService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired OTP.");
        }
    }
}
