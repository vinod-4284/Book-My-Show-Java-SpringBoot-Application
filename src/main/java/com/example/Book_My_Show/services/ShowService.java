package com.example.Book_My_Show.services;


import com.example.Book_My_Show.dtos.SeatStatusResponse;
import com.example.Book_My_Show.dtos.ShowRequestDto;
import com.example.Book_My_Show.exceptions.UnAuthorizedException;
import com.example.Book_My_Show.moduls.BookedSeat;
import com.example.Book_My_Show.moduls.Hall;
import com.example.Book_My_Show.moduls.Show;
import com.example.Book_My_Show.moduls.User;
import com.example.Book_My_Show.repositories.BookedSeatRepository;
import com.example.Book_My_Show.repositories.ShowRepository;
import com.example.Book_My_Show.transformers.ApplicationTransformer;
import com.example.Book_My_Show.utilitis.SystemUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.directory.InvalidAttributesException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class ShowService {

    private UserService userService;
    private HallService hallService;
    private ShowRepository showRepository;
    private ApplicationTransformer applicationTransformer;
    private BookedSeatRepository bookedSeatRepository;

    @Autowired
    public ShowService(UserService userService,
                       HallService hallService,
                       BookedSeatRepository bookedSeatRepository,
                       ShowRepository showRepository,
                       ApplicationTransformer applicationTransformer){
        this.userService = userService;
        this.bookedSeatRepository = bookedSeatRepository;
        this.hallService = hallService;
        this.showRepository = showRepository;
        this.applicationTransformer = applicationTransformer;
    }

    public boolean isOverLappingShow(List<Show> shows, Long startTime, Long endTime){
        Collections.sort(shows);
        for(Show show : shows){
            if(show.getEndTimeInSeconds() >= startTime){
                return true;
            }
        }
        return false;
    }



    public Show createShow(ShowRequestDto showRequestDto,
                           UUID hallSysId,
                           UUID userSysId) throws InvalidAttributesException {
        User user = userService.verifyTheaterOwner(userSysId);
        Hall hall = hallService.verifyHallSysId(hallSysId);
        if(!hall.getTheater().getOwner().getSysId().equals(user.getSysId())){
            throw new UnAuthorizedException("User is not allowed to create show in hall");
        }
        LocalDateTime startTime = showRequestDto.getStartTime();
        Long startTimeInSeconds = SystemUtility.convertShowTimeInSeconds(startTime);
        LocalDateTime endTime = showRequestDto.getEndTime();
        Long endTimeInSeconds = SystemUtility.convertShowTimeInSeconds(endTime);
        // Now we need to identify is this show over lapping with other shows of the hall
        List<Show> shows = showRepository.findByHall(hall);
        boolean isOverLapping = this.isOverLappingShow(shows, startTimeInSeconds, endTimeInSeconds);
        if(isOverLapping){
            throw new IllegalArgumentException("Overlapping timings");
        }
        Show show = applicationTransformer.transformShowDtoToShow(showRequestDto,
                hall,
                user,
                startTimeInSeconds,
                endTimeInSeconds);

        showRepository.save(show);
        return show;
    }

    public List<Show> searchShows(
            String movieName,
            String city
    ){
        // I should call first repo layer to get all shows by movieName
        List<Show> shows = showRepository.findByMovieName(movieName);
        List<Show> showsFilteredByCity = new ArrayList<>();
        for(Show show : shows){
            if(show.getHall().getTheater().getCity().equals(city)){
                showsFilteredByCity.add(show);
            }
        }
        return showsFilteredByCity;
    }

    public List<SeatStatusResponse> fetchSeatStatusByShowId(UUID showId){
        // We require hall details -> In hall object we have seat mapping
        // Here we just have showId ? How we can get the hall object
        // In the show object we are having reference o hall objecgt
        Show show = showRepository.findById(showId).orElse(null);
        Hall hall = show.getHall();
        String rowRange = hall.getRowRange(); // A-H
        int rowCapacity = hall.getSeatCapacity();
        // A -> A1, A2, A3, A4, A5
        // B -> B1, B2, B3
        // C -> C1, C2, C3
        String [] rowArr = rowRange.split("-");
        char stRange = rowArr[0].charAt(0);
        char enRange = rowArr[1].charAt(0);

        List<SeatStatusResponse> seatStatusResponses = new ArrayList<>();

        for(char ch = stRange; ch <= enRange; ch++){
            for(int i = 1; i <= rowCapacity; i++){
                String seatId = ch + "" + i;
                // SeatId & ShowId -> For this show is this seatId is booked or unbooked ?
                BookedSeat bookedSeat = bookedSeatRepository.isSeatBooked(seatId, show.getSysId());
                SeatStatusResponse seatStatusResponse = new SeatStatusResponse();
                seatStatusResponse.setSeatId(seatId);
                seatStatusResponse.setShow(show);
                if(bookedSeat == null){
                    seatStatusResponse.setSeatStatus("UNBOOKED");
                    // this seat is unbooked for the show
                    // Need to generate a response
                }else{
                    seatStatusResponse.setSeatStatus("BOOKED");
                    // this seat is booked for the show
                }

                seatStatusResponses.add(seatStatusResponse);
            }
        }

        return seatStatusResponses;
        // If we have mapping of the hall -> We can create seat ids for the show
    }



}

