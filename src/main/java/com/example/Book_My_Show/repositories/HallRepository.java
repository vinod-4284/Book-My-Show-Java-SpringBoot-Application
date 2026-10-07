package com.example.Book_My_Show.repositories;


import com.example.Book_My_Show.moduls.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HallRepository extends JpaRepository<Hall, UUID> {
}
