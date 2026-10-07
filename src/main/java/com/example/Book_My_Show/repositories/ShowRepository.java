package com.example.Book_My_Show.repositories;


import com.example.Book_My_Show.moduls.Hall;
import com.example.Book_My_Show.moduls.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {

    @Query(value = "select * from shows where hall_sys_id =:hallSysId", nativeQuery = true)
    public List<Show> getAllShowsByHall(String hallSysId);

    public List<Show> findByHall(Hall hall);

    public List<Show> findByMovieName(String name);
}
