package com.lesson.reminderation20.Services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lesson.reminderation20.MainActivity;
import com.lesson.reminderation20.Models.AuthorisedUser;

public class AuthService  {
    FirebaseAuth firebaseAuth;

    public AuthService(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void registerUser(String email, String password, Context context){
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = firebaseAuth.getCurrentUser();

                AuthorisedUser.Email = user.getEmail();
                AuthorisedUser.Id = user.getUid();

            }
            else{
                //error
                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void loginUser(String email, String password, Context context){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                AuthorisedUser.Email = user.getEmail();
                AuthorisedUser.Id = user.getUid();

            }
            else{
                //error
                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void logoutUser(){
        firebaseAuth.signOut();
    }
}
