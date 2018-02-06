package it.onetech.deadline;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
                // TODO login riuscito
                Toast.makeText(this, "LOGIN EFFETTUATO", Toast.LENGTH_SHORT).show();
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
}
