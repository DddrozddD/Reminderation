package com.lesson.reminderation20;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lesson.reminderation20.Models.AuthorisedUser;
import com.lesson.reminderation20.Services.AuthService;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {
   /* private final ActivityResultLauncher<Intent> singInLauncher = registerForActivityResult(new FirebaseAuthUIActivityResultContract(), new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
        @Override
        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
            IdpResponse response = result.getIdpResponse();
            if(result.getResultCode() == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthorisedUser.Email = user.getEmail();
                AuthorisedUser.Id = user.getUid();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    });*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText email = (EditText) findViewById(R.id.LoginEmail);
        EditText pass = (EditText) findViewById(R.id.LoginPassword);

        AuthService as = new AuthService();

        Button logBtn = (Button) findViewById(R.id.login);
        logBtn.setOnClickListener(f->{

            if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {

                as.loginUser(email.getText().toString(), pass.getText().toString(), this);
                if (AuthorisedUser.Id != "") {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            else {
                Toast.makeText(this, "Email or password is empty", Toast.LENGTH_LONG).show();
            }
        });


        Button toRegBtn = (Button) findViewById(R.id.toReg);
        toRegBtn.setOnClickListener(f->{
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });

       /* FirebaseAuth.getInstance().signOut();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        Intent singInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();

        singInLauncher.launch(singInIntent);*/
    }
}