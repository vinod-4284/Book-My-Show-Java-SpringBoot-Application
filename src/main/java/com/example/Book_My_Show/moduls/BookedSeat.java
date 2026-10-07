package com.example.Book_My_Show.moduls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booked-seats")
@Entity
// 1 A1
public class BookedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    @Column(unique = true, nullable = false)
    private String bookingId;
    private String seatId;
    @ManyToOne
    private Show show;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
