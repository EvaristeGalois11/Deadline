package it.onetech.deadline;

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
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButton1) {
            //è stata selezionata la creazione di un nuovo utente
            Toast.makeText(this, "CREAZIONE", Toast.LENGTH_SHORT).show();
        } else {
            //procedere verso il login
            Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
        }
    }
}
