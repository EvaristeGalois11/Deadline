package it.onetech.deadline;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert()
    void addUser(User user);

    @Query("select * from user where username = :username")
    User getUser(String username);

    @Query("delete from user")
    void cleanDatabase();

    @Query("select * from user")
    List<User> getAllUsers();

}
