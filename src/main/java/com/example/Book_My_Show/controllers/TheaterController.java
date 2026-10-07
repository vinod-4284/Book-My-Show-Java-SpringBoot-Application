package com.example.Book_My_Show.controllers;


import com.example.Book_My_Show.dtos.TheaterRequestDto;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.exceptions.UserNotFoundException;
import com.example.Book_My_Show.moduls.Theater;
import com.example.Book_My_Show.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RequestMapping("/api/v1/theater")
@RestController
public class TheaterController {

    private TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService){
        this.theaterService = theaterService;
    }

    @PostMapping("/create-theater")
    public ResponseEntity createTheater(
            @RequestBody TheaterRequestDto theaterRequestDto,
            @RequestParam UUID userSysId
            ){
        // Service layer
        try{
            Theater theater = theaterService.createTheater(theaterRequestDto, userSysId);
            return new ResponseEntity(theater, HttpStatus.CREATED);
        }catch (UserNotFoundException e){
            HashMap<String, String> notFoundMessage = new HashMap<>();
            notFoundMessage.put("message", e.getMessage());
            return new ResponseEntity(notFoundMessage, HttpStatus.BAD_REQUEST);
        }catch (UnAuthorizedException e){
            HashMap<String, String> notFoundMessage = new HashMap<>();
            notFoundMessage.put("message", e.getMessage());
            return new ResponseEntity(notFoundMessage, HttpStatus.UNAUTHORIZED);
        }catch(Exception e){
            HashMap<String, String> notFoundMessage = new HashMap<>();
            notFoundMessage.put("message", e.getMessage());
            return new ResponseEntity(notFoundMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
