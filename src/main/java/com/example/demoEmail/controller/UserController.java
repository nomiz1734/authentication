package com.example.demoEmail.controller;

import com.example.demoEmail.dto.request.UserCreationRequest;
import com.example.demoEmail.dto.request.UserUpdateRequest;
import com.example.demoEmail.dto.sdi.ClientSdi;
import com.example.demoEmail.entity.User;
import com.example.demoEmail.service.ClientService;
import com.example.demoEmail.service.UserService;
import com.example.demoEmail.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    //tao user moi
    @PostMapping
    User createUser(@RequestBody UserCreationRequest request){
        return userService.createUser(request);
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
    @PutMapping
    User updateUser(@RequestBody UserUpdateRequest request){
        return userService.updateUser(request);
    }

    //xoa user
    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "User has been deleted";
    }


}
