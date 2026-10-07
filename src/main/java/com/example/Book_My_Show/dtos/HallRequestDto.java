package com.example.Book_My_Show.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HallRequestDto {
    private String hallName;
    private String rowRange; // A-G
    private int seatCapacityPerRow;
}
