package it.onetech.deadline;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class CreateUserActivity extends AppCompatActivity {
    private AppDatabase database;
    private int sudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        database = AppDatabase.getDatabase(getApplicationContext());
    }

    public void onClickButton2(View view) {
        String username = extractStringFromEditText(R.id.editText1);
        String password1 = extractStringFromEditText(R.id.editText2);
        String password2 = extractStringFromEditText(R.id.editText3);

        if (checkCondition(username, password1, password2)) {
            User user = new User(username, password1);
            
            try {
                database.userDao().addUser(user);
                Toast.makeText(this, R.string.userCreated, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.errorCreationUser, Toast.LENGTH_LONG).show();
            }

            resetUsername();
            resetPassword();
        }
    }

    private String extractStringFromEditText(int id) {
        EditText editText = findViewById(id);
        return editText.getText().toString();
    }

    private boolean checkCondition(String username, String password1, String password2) {
        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, R.string.missingInformation, Toast.LENGTH_LONG).show();
            return false;
        } else if (!password1.equals(password2)) {
            Toast.makeText(this, R.string.differentPassword, Toast.LENGTH_LONG).show();
            resetPassword();
            return false;
        }
        return true;
    }

    private void resetPassword() {
        EditText editText1 = findViewById(R.id.editText2);
        EditText editText2 = findViewById(R.id.editText3);
        editText1.setText("");
        editText2.setText("");
    }

    private void resetUsername() {
        EditText editText = findViewById(R.id.editText1);
        editText.setText("");
    }

    public void onClickTextView2(View view) {
        sudo++;
        if (sudo == 3) {
            String username = extractStringFromEditText(R.id.editText1);
            String password = Hashing.sha256().hashString(extractStringFromEditText(R.id.editText2), StandardCharsets.UTF_8).toString();
            String command = extractStringFromEditText(R.id.editText3);

            if (username.equals("admin") && password.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8")) { // password
                switch (command) {
                    case "delete": {
                        database.userDao().cleanDatabase();
                        Toast.makeText(this, "Deleted all users", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case "all": {
                        Toast.makeText(this, "Users in database: " + database.userDao().getAllUsers().size(), Toast.LENGTH_LONG).show();
                        break;
                    }
                    default: {
                        Toast.makeText(this, "Unknown command", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(this, "ADMIN PRIVILIGE DENIED!", Toast.LENGTH_LONG).show();
            }

            sudo = 0;
        }
    }
}
