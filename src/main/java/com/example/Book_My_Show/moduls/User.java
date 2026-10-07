package com.example.Book_My_Show.moduls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
// {fullname, email, password, }
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private String userType;
    private String phoneNumber;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
