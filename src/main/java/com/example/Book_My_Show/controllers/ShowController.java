package com.example.Book_My_Show.controllers;


import com.example.Book_My_Show.dtos.ShowRequestDto;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.moduls.Show;
import com.example.Book_My_Show.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.InvalidAttributesException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/show")
public class ShowController {

    private ShowService showService;

    @Autowired
    public ShowController(ShowService showService){
        this.showService = showService;
    }

    @PostMapping("/create")
    public ResponseEntity createShow(
            @RequestParam UUID hallSysId,
            @RequestParam UUID userSysId,
            @RequestBody ShowRequestDto showRequestDto
            ){
        HashMap<String, String> exceptionMessage = new HashMap<>();
        try{
            return new ResponseEntity(showService.createShow(showRequestDto, hallSysId, userSysId), HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
        }catch (InvalidAttributesException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
        }catch (UnAuthorizedException e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            exceptionMessage.put("message", e.getMessage());
            return new ResponseEntity(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity searchShows(
            @RequestParam String city,
            @RequestParam String movie
    ){
        // Service layer
        List<Show> shows = showService.searchShows(movie, city);
        return new ResponseEntity(shows, HttpStatus.OK);
    }

    // http://localhost:8080/api/v1/show/seat-status?showId=bckwsbcbcbecb
    @GetMapping("/seat-status")
    public ResponseEntity getShowSeatStatus(@RequestParam UUID showId){
        // ShowService -> Fetch Seat Status of the show
        return new ResponseEntity(showService.fetchSeatStatusByShowId(showId), HttpStatus.OK);
    }


}
