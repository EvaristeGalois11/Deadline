package it.onetech.deadline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void onClick(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup1);

        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.radioButton1: {
                Intent i = new Intent(this, CreateUserActivity.class);
                startActivity(i);
                break;
            }
            case R.id.radioButton2: {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
            }
            case R.id.radioButton3: {
                // TODO implementare ABOUT
                break;
            }
        }

    }
}
