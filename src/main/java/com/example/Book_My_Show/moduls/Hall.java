package com.example.Book_My_Show.moduls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "halls")
@Entity
@Builder
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    @Column(unique = true)
    private String hallId;
    private String hallName;
    private String rowRange; // A-G
    private Integer seatCapacity;
    @ManyToOne
    private Theater theater;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}

// A1 , B1, B2, C1, C2, C#, D1, D2,
// 10AM - 2:00PM, 3:00PM - 7:00 PM
// A1
