package it.onetech.deadline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    public String username;
    public String password;
    public long games;
    public long bestScore;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        games = 0;
        bestScore = 0;
    }
}
