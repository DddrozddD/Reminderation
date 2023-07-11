package com.lesson.reminderation20;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.lesson.reminderation20.AddRemindActivity;
import com.lesson.reminderation20.Fragments.ListRemindsFragment;
import com.lesson.reminderation20.Models.AuthorisedUser;
import com.lesson.reminderation20.R;
import com.lesson.reminderation20.Services.AuthService;


public class MainActivity extends AppCompatActivity {


    public static final int POST_NOTIFICATIONS_KEY = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new ListRemindsFragment()).commit();

        ImageButton addBtn = (ImageButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRemindActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.singOutBtn).setOnClickListener(f->{
            AuthService as = new AuthService();
            as.logoutUser();
            AuthorisedUser.Id = "";
            AuthorisedUser.Email = "";
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!checkCameraPermission()){
            requestCameraPermission();
        }
    }

    private boolean checkCameraPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission(){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS},POST_NOTIFICATIONS_KEY );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == POST_NOTIFICATIONS_KEY){
            if(grantResults[0]>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
            }
        }
    }
}