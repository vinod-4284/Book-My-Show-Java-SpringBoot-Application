package com.example.Book_My_Show.repositories;


import com.example.Book_My_Show.moduls.User;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
/*@Repository  Manual Database creation as HashMap
public class UserRepository {
    private HashMap<String, User> userDB;
    public UserRepository() {
        this.userDB = new HashMap<>();
    }
    public User save(User user){
        this.userDB.put(user.getUserId(), user);
        return user;
    }

}*/


public interface UserRepository extends JpaRepository<User, UUID> {


}
