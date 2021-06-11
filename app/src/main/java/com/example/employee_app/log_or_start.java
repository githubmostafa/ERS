package com.example.employee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class log_or_start extends AppCompatActivity {
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_or_start);
        t1=findViewById(R.id.textView2);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void login(View V){
        Intent Login = new Intent(log_or_start.this,login.class);
        startActivity(Login);
    }
    public void EmpStart(View V){
        Intent EMPStart = new Intent(log_or_start.this,emp_inter.class);
        startActivity(EMPStart);
    }
    public void signup(View view){
        Intent i = new Intent(this,register.class);
        startActivity(i);

    }



    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}