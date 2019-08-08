package it.nave.deadline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

import it.nave.deadline.R;
import it.nave.deadline.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
  private static final int SIGN_IN_CODE = 10;

  private ActivityHomeBinding homeBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    checkUserPresence();
  }

  private void checkUserPresence() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      greetCurrentUser(user);
    } else {
      kickOffLogin();
    }
  }

  private void greetCurrentUser(FirebaseUser user) {
    homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
    homeBinding.setName(user.getDisplayName());
  }

  private void kickOffLogin() {
    List<AuthUI.IdpConfig> providers =
        Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build());
    startActivityForResult(
        AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
        SIGN_IN_CODE);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == SIGN_IN_CODE) {
      if (resultCode == RESULT_OK) {
        greetCurrentUser(FirebaseAuth.getInstance().getCurrentUser());
      }
    }
  }

  public void logout(View view) {
    AuthUI.getInstance()
        .signOut(this)
        .addOnCompleteListener(
            new OnCompleteListener<Void>() {
              public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomeActivity.this, "Disconnesso correttamente", Toast.LENGTH_SHORT)
                    .show();
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
              }
            });
  }
}
