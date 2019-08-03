package it.nave.deadline;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(User user);

    @Query("select * from user where username = :username")
    User getUser(String username);

    @Update
    void updateUser(User user);

    @Query("delete from user")
    void cleanDatabase();

    @Query("select * from user")
    List<User> getAllUsers();

}
