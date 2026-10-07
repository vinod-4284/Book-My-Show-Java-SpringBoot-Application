package com.example.Book_My_Show.repositories;


import com.example.Book_My_Show.moduls.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookedSeatRepository extends JpaRepository<BookedSeat, UUID> {

    @Query(value = "SELECT * FROM public.\"booked-seats\" where seat_id =:seatId and show_sys_id =:showSysId", nativeQuery = true)
    public BookedSeat isSeatBooked(String seatId, UUID showSysId);

}
