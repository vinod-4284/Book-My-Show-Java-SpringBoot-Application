package com.example.Book_My_Show.repositories;


import com.example.Book_My_Show.moduls.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, UUID> {
}
