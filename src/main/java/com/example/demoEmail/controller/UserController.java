package com.example.demoEmail.controller;

import com.example.demoEmail.dto.request.LoginRequest;
import com.example.demoEmail.dto.request.UserCreationRequest;
import com.example.demoEmail.dto.request.UserUpdateRequest;
import com.example.demoEmail.dto.sdi.ClientSdi;
import com.example.demoEmail.entity.User;
import com.example.demoEmail.service.ClientService;
import com.example.demoEmail.service.CloudinaryService;
import com.example.demoEmail.service.UserService;
import com.example.demoEmail.service.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private final CloudinaryService cloudinaryService;
    //tao user moi
    @PostMapping("/register")
    User createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
    }
    @PostMapping("/login")
    User checkLogin(@RequestBody LoginRequest loginRequest){
        User user = userService.getUserByEmailPass(loginRequest.getEmail(), loginRequest.getPassword());
        return user;
    }
    //get list user
    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    //get user theo id
    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") String userId){
        return userService.getUser(userId);
    }

    //cap nhat thong tin user
    @PutMapping("/update")
    User updateUser(@RequestBody UserUpdateRequest request) throws IOException {
        return userService.updateUser(request);
    }
//    @PutMapping("/upload")
//    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file){
//        Map data = this.cloudinaryService.upload(file);
//        return new ResponseEntity<>(data, HttpStatus.OK);
//    }

    //xoa user
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }


}
