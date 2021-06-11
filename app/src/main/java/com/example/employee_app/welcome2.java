package com.example.employee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcome2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void emp_welcome(View view){

        Intent emp_wel = new Intent(welcome2.this, welcome3.class);
        startActivity(emp_wel);
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}