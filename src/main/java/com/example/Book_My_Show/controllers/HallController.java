package com.example.Book_My_Show.controllers;

import com.example.Book_My_Show.dtos.HallRequestDto;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.moduls.Hall;
import com.example.Book_My_Show.services.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hall")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createHall(
            @RequestParam UUID theaterOwnerSysId,
            @RequestParam UUID theaterSysId,
            @RequestBody HallRequestDto hallRequestDto
    ) {
        Map<String, String> exceptionMessage = new HashMap<>();
        try {
            Hall hall = hallService.createHall(theaterOwnerSysId, theaterSysId, hallRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(hall);
        } catch (UnAuthorizedException e) {
            exceptionMessage.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionMessage);
        } catch (InvalidAttributesException e) {
            exceptionMessage.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
        } catch (Exception e) {
            exceptionMessage.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionMessage);
        }
    }
}
