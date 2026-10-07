package com.example.Book_My_Show.services;


import com.example.Book_My_Show.dtos.TheaterRequestDto;
import com.example.Book_My_Show.enums.UserType;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.exceptions.UserNotFoundException;
import com.example.Book_My_Show.moduls.Theater;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.repositories.TheaterRepository;
import com.example.Book_My_Show.repositories.UserRepository;
import com.example.Book_My_Show.transformers.ApplicationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheaterService {

    private UserRepository userRepository;
    private ApplicationTransformer applicationTransformer;
    private TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(UserRepository userRepository,
                          ApplicationTransformer applicationTransformer,
                          TheaterRepository theaterRepository){
        this.userRepository = userRepository;
        this.theaterRepository = theaterRepository;
        this.applicationTransformer = applicationTransformer;
    }


    public Theater createTheater(TheaterRequestDto theaterRequestDto,
                                 UUID userSysId){
        // With the help of this sysId i need to get the user obejct
        User user = userRepository.findById(userSysId).orElse(null);
        if(user == null){
            throw new UserNotFoundException(String.format("user with id %s does not exist", userSysId.toString()));
        }
        if(!user.getUserType().equals(UserType.THEATER_OWNER.toString())){
            throw new UnAuthorizedException("User is not allowed create theater");
        }
        // Adpater to transform the request and return the Theater model
        Theater theater  = applicationTransformer.transformTheaterRequestToTheaterModel(theaterRequestDto,
                user);
        // save this theater inside the theater table
        theaterRepository.save(theater);
        return theater;
    }

    public Theater verifyTheaterSysId(UUID theaterSysId) throws InvalidAttributesException{
        Optional<Theater> theater = theaterRepository.findById(theaterSysId);
        if(theater.isEmpty()){
            throw new InvalidAttributesException("Invalid theaterId passed");
        }
        return theater.get();
    }

}
