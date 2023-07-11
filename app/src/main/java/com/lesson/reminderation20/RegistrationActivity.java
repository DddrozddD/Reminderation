package com.lesson.reminderation20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lesson.reminderation20.Models.AuthorisedUser;
import com.lesson.reminderation20.Services.AuthService;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        EditText email = (EditText) findViewById(R.id.registerEmail);
        EditText pass = (EditText) findViewById(R.id.registerPassword);

        AuthService as = new AuthService();

        Button regBtn = (Button) findViewById(R.id.register);
        regBtn.setOnClickListener(f->{
            if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                as.registerUser(email.getText().toString(), pass.getText().toString(), this);
                if (AuthorisedUser.Id != "") {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
            else{
                Toast.makeText(this, "Email or password is empty", Toast.LENGTH_LONG).show();
            }
        });

        Button toLogBtn = (Button) findViewById(R.id.toLogin);
        toLogBtn.setOnClickListener(f->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}