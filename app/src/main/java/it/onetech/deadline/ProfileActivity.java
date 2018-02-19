package it.onetech.deadline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private AppDatabase database;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = AppDatabase.getDatabase(getApplicationContext());

        if (savedInstanceState == null) {
            username = getIntent().getStringExtra("username");
        } else {
            username = savedInstanceState.getString("username");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = database.userDao().getUser(username);

        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);

        setUserInformation(textView4, R.string.textView4,"USERNAME", user.username);
        setUserInformation(textView5, R.string.textView5,"XX", Long.toUnsignedString(user.games));
        setUserInformation(textView6, R.string.textView6, "XX", Long.toUnsignedString(user.bestScore));
    }

    private void setUserInformation(TextView textView, int strId, String target, String replacement) {
        String oldString = getResources().getString(strId);
        String newString = oldString.replace(target, replacement);
        textView.setText(newString);
    }

    public void onClickButton4(View view) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        Editor edit = settings.edit();
        edit.remove("username").apply();
        Intent i = new Intent(this, WelcomeActivity.class);
        startActivity(i);
    }

    public void onClickButton5(View view) {
        Intent service = new Intent(this, GamePlayedService.class);
        service.putExtra("username", username);
        startService(service);

        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance) {
        savedInstance.putString("username", username);
        super.onSaveInstanceState(savedInstance);
    }


}
