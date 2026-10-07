package com.example.Book_My_Show.dtos;

import com.example.Book_My_Show.moduls.Show;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatusResponse {
    private Show show;
    private String seatId;
    private String seatStatus;
}
