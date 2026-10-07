package com.example.Book_My_Show.moduls;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shows")
@Entity
public class Show implements Comparable<Show>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sysId;
    private String showId;
    private Double showPrice;
    private String movieName;
    @ManyToOne
    private Hall hall;
    private Long startTimeInSeconds;
    private Long endTimeInSeconds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    public int compareTo(Show b){
        return Math.toIntExact(this.startTimeInSeconds - b.getStartTimeInSeconds());
    }
}
