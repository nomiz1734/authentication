package com.example.demoEmail.controller;

import com.example.demoEmail.service.ImageService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@RestController
@RequestMapping("api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;
    @PostMapping("/{userId}")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file,@PathVariable("userId") String userId) throws IOException {
        String uploadImage = imageService.uploadImage(file,userId);
        return ResponseEntity.ok().body(uploadImage);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) throws IOException {
        byte[] imageData = imageService.downloadImage(fileName);
        return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(imageData);
    }
}
//