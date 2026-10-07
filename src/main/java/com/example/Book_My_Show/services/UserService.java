package com.example.Book_My_Show.services;


import com.example.Book_My_Show.dtos.UserRequestDto;
import com.example.Book_My_Show.enums.UserType;
import com.example.Book_My_Show.exceptions.UserNotFoundException;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.repositories.UserRepository;
import com.example.Book_My_Show.transformers.ApplicationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private ApplicationTransformer applicationTransformer;
    private UserRepository userRepository;

    @Autowired
    public UserService(ApplicationTransformer applicationTransformer,
                       UserRepository userRepository){
        this.applicationTransformer = applicationTransformer;
        this.userRepository = userRepository;
    }

    public User registerOwner(UserRequestDto userRequestDto){
        User user = applicationTransformer.transformUserRequestDtoToUserModel(userRequestDto, UserType.THEATER_OWNER.toString());
        this.userRepository.save(user);
        return user;

    }

    public User registerCustomer(UserRequestDto userRequestDto){
        User user = applicationTransformer.transformUserRequestDtoToUserModel(userRequestDto, UserType.CUSTOMER.toString());
        this.userRepository.save(user);
        return user;
    }

    public User verifyTheaterOwner(UUID userSysId) throws InvalidAttributesException{
        Optional<User> user =  userRepository.findById(userSysId);
        if(user.isEmpty()){
            throw new UserNotFoundException("User does not exist");
        }
        if(!user.get().getUserType().equals(UserType.THEATER_OWNER.toString())){
            throw new InvalidAttributesException("Invalid Id passed");
        }
        return user.get();
    }


}
