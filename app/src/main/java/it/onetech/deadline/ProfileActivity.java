package it.onetech.deadline;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = AppDatabase.getDatabase(getApplicationContext());

        String username = getIntent().getExtras().getString("username");
        User user = database.userDao().getUser(username);

        TextView textView4 = findViewById(R.id.textView4);
        TextView textView5 = findViewById(R.id.textView5);
        TextView textView6 = findViewById(R.id.textView6);

        setUserInformation(textView4, "USERNAME", user.username);
        setUserInformation(textView5, "XX", Long.toUnsignedString(user.games));
        setUserInformation(textView6, "XX", Long.toUnsignedString(user.bestScore));
    }

    private void setUserInformation(TextView textView, String target, String replacement) {
        String oldString = textView.getText().toString();
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
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
    }
}