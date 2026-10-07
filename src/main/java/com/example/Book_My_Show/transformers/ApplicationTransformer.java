package com.example.Book_My_Show.transformers;


import com.example.Book_My_Show.dtos.HallRequestDto;
import com.example.Book_My_Show.dtos.ShowRequestDto;
import com.example.Book_My_Show.dtos.TheaterRequestDto;
import com.example.Book_My_Show.dtos.UserRequestDto;
import com.example.Book_My_Show.moduls.Hall;
import com.example.Book_My_Show.moduls.Show;
import com.example.Book_My_Show.moduls.Theater;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.utilitis.SystemUtility;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class ApplicationTransformer {

    public User transformUserRequestDtoToUserModel(UserRequestDto userRequestDto, String userType){
        return User.builder()
                .userId(SystemUtility.generate("USER"))
                .userType(userType)
                .fullName(userRequestDto.getFullName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .updatedBy("system")
                .phoneNumber(userRequestDto.getPhoneNumber())
                .address(userRequestDto.getAddress())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }

    public Theater transformTheaterRequestToTheaterModel(TheaterRequestDto theaterRequestDto,
                                                         User user){
        return Theater.builder()
                .halls(new ArrayList<>())
                .owner(user)
                .city(theaterRequestDto.getCity())
                .theaterName(theaterRequestDto.getTheaterName())
                .country(theaterRequestDto.getCountry())
                .state(theaterRequestDto.getState())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(user.getEmail())
                .updatedBy(user.getEmail())
                .build();
    }

    public Hall transformDtoToHallModel(
            HallRequestDto hallRequestDto,
            Theater theater
    ){
        return Hall.builder()
                .hallId(SystemUtility.generate("HALL"))
                .hallName(hallRequestDto.getHallName())
                .theater(theater)
                .seatCapacity(hallRequestDto.getSeatCapacityPerRow())
                .rowRange(hallRequestDto.getRowRange())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(theater.getOwner().getEmail())
                .updatedBy(theater.getOwner().getEmail())
                .build();
    }

    public Show transformShowDtoToShow(
            ShowRequestDto showRequestDto,
            Hall hall,
            User user,
            Long startTimeInSeconds,
            Long endTimeInSeconds
    ){
        return Show.builder()
                .hall(hall)
                .showId(SystemUtility.generate("SHOW"))
                .endTimeInSeconds(endTimeInSeconds)
                .movieName(showRequestDto.getMovieName())
                .startTimeInSeconds(startTimeInSeconds)
                .showPrice(showRequestDto.getShowPrice())
                .startTime(showRequestDto.getStartTime())
                .endTime(showRequestDto.getEndTime())
                .createdBy(user.getEmail())
                .updatedBy(user.getEmail())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
