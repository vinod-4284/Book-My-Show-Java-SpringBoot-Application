package com.example.Book_My_Show.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterRequestDto {
    private String theaterName;
    private String city;
    private String state;
    private String country;
    private String address;
}
