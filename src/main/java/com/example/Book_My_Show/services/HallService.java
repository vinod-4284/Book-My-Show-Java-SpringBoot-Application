package com.example.Book_My_Show.services;


import com.example.Book_My_Show.dtos.HallRequestDto;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.moduls.Hall;
import com.example.Book_My_Show.moduls.Theater;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.repositories.HallRepository;
import com.example.Book_My_Show.transformers.ApplicationTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HallService {

    private UserService userService;
    private TheaterService theaterService;
    private ApplicationTransformer applicationTransformer;
    private HallRepository hallRepository;
    private HallRequestDto hallRequestDto;

    @Autowired
    public HallService(UserService userService,
                       TheaterService theaterService,
                       ApplicationTransformer applicationTransformer,
                       HallRepository hallRepository){
        this.userService = userService;
        this.applicationTransformer = applicationTransformer;
        this.theaterService = theaterService;
        this.hallRepository = hallRepository;
    }

    public Hall createHall(UUID theaterOwnerSysId, UUID theaterSysId , HallRequestDto hallRequestDto) throws  Exception{
        // User Service -> To verify that this owner sysId exists in the user table or not and then verify Is this user a theater owner or not
        User user = userService.verifyTheaterOwner(theaterOwnerSysId);
        // Verify the theater Id
        Theater theater = theaterService.verifyTheaterSysId(theaterSysId);
        // Verify the user owns this tehater or not
        if(!user.getSysId().equals(theater.getOwner().getSysId())){
            throw new UnAuthorizedException("User does not own the theater");
        }

        Hall hall = applicationTransformer.transformDtoToHallModel(hallRequestDto, theater);
        hallRepository.save(hall);
        return hall;
    }

    public Hall verifyHallSysId(UUID hallSysId) throws InvalidAttributesException {
        Optional<Hall> hall = hallRepository.findById(hallSysId);
        if(hall.isEmpty()){
            throw new InvalidAttributesException("Invalid hallId passed");
        }
        return hall.get();
    }

}
