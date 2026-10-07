package com.example.Book_My_Show.controllers;


import com.example.Book_My_Show.dtos.UserRequestDto;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/customer/register")
    public ResponseEntity<User> createCustomer(@RequestBody UserRequestDto userRequestDto){
        User user = userService.registerCustomer(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/theater-owner/register")
    public ResponseEntity<User> createTheaterOwner(@RequestBody UserRequestDto userRequestDto){
        // We will be calling userService
        log.info(String.format("Recieved user registration request %s", userRequestDto.toString()));
        User user = userService.registerOwner(userRequestDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
