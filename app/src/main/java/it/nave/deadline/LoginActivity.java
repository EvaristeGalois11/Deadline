package it.nave.deadline;

import android.content.SharedPreferences.Editor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class LoginActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = AppDatabase.getDatabase(getApplicationContext());
    }

    public void onClick(View view) {
        String username = extractStringFromEditText(R.id.editText4);
        String password = extractStringFromEditText(R.id.editText5);

        if (checkCondition(username, password)) {
            User user = database.userDao().getUser(username);
            if (user == null) {
                Toast.makeText(this, R.string.usernameNotFound, Toast.LENGTH_LONG).show();
            } else if (Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString().equals(user.password)) {
                checkRemember(username);
                login(username);
            } else {
                Toast.makeText(this, R.string.wrongPassword, Toast.LENGTH_LONG).show();
            }
        }
    }

    private String extractStringFromEditText(int id) {
        EditText editText = findViewById(id);
        return editText.getText().toString();
    }

    private boolean checkCondition(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.missingInformation, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void login(String username) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    private void checkRemember(String username) {
        CheckBox checkBox = findViewById(R.id.checkBox1);

        if (checkBox.isChecked()) {
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            Editor edit = settings.edit();
            edit.putString("username", username).apply();
        }
    }
}
