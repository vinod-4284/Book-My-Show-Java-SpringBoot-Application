package com.example.Book_My_Show.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    private String fullName;
    private String email;
    private String password;
    private String userType;
    private String phoneNumber;
    private String address;
}
