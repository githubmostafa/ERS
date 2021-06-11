package com.example.employee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcome3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    public void main_Start(View view){

        Intent log_or_start = new Intent(welcome3.this, log_or_start.class);
        startActivity(log_or_start);
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}