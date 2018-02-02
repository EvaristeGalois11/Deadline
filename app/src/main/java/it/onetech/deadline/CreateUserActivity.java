package it.onetech.deadline;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        database = AppDatabase.getDatabase(getApplicationContext());
    }

    public void onClick(View view) {
        String username = extractStringFromEditText(R.id.editText1);
        String password1 = extractStringFromEditText(R.id.editText2);
        String password2 = extractStringFromEditText(R.id.editText3);

        if (username.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, R.string.missingInformation, Toast.LENGTH_LONG).show();
        } else if (!password1.equals(password2)) {
            Toast.makeText(this, R.string.differentPassword, Toast.LENGTH_LONG).show();
            resetPassword();
        } else {
            User user = new User(username, password1);
            database.userDao().addUser(user); // TODO in caso l'utente sia già presente l'app crasha
            Toast.makeText(this, R.string.userCreated, Toast.LENGTH_LONG).show();
            resetUsername();
            resetPassword();
        }
    }

    private String extractStringFromEditText(int id) {
        EditText editText = findViewById(id);
        return editText.getText().toString();
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
}
