package com.example.employee_app;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;

public class MainActivity extends     Activity {
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    ProgressBar bar;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();

        bar = (ProgressBar)findViewById(R.id.progressBar1);
        bar.setVisibility(View.VISIBLE);

        Intent i = new Intent(MainActivity.this,log_or_start.class);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
          }
        }, 2500);



                }


}