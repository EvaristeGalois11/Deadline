package it.onetech.deadline;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class GamePlayedService extends IntentService {

    public GamePlayedService() {
        super("GamePlayedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String username = intent.getStringExtra("username");
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        User user = database.userDao().getUser(username);
        ++user.games;
        database.userDao().updateUser(user);
    }
}
