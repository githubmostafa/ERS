package com.example.employee_app;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class apply extends AppCompatActivity {

   Button next,back;
   EditText fullname_,age_,location_,zip_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        ///////////////////////////////////////////////
        fullname_=findViewById(R.id.fullname);
        age_=findViewById(R.id.age);
        location_=findViewById(R.id.address_location);

        Intent getValues = getIntent();
        String full = getValues.getStringExtra("fullname");
        String age1 = getValues.getStringExtra("age");
        String add = getValues.getStringExtra("location");
        String jobname= getValues.getStringExtra("jobTitle");
        fullname_.setText(full);
        age_.setText(age1);
        location_.setText(add);
        ///////////////////////////////////////////////
        final String error = "Fill up this field please";

        Intent us = getIntent();
        String username = us.getStringExtra("username");
        next = findViewById(R.id.basicnext);
        back = findViewById(R.id.backToEmp);
        next.setOnClickListener(v -> {


            String name = fullname_.getText().toString();
            String age=age_.getText().toString();
            String location = location_.getText().toString();




             if(TextUtils.isEmpty(name)){
                fullname_.setError(error);

            }else if (TextUtils.isEmpty(location)){
                location_.setError(error);
            }
            else if(TextUtils.isEmpty(age)){
                age_.setError(error);
            }



            else if(name.length()<5){
                fullname_.setError("Enter a valid Name");
            }else if(location.length()<8){
                location_.setError("Enter a valid Location");
            }






            else if (!age.equals("") && (Integer.parseInt(age)< 18 || Integer.parseInt(age)>64)) {

                     age_.setError("Not allowed age");
                 }

            else{


                 Intent basic = new Intent(apply.this, education.class);
                 basic.putExtra("fullname", name);
                 basic.putExtra("age", age);
                 basic.putExtra("location", location);
                 basic.putExtra("username", username);
                 basic.putExtra("jobtitle", jobname);
                 startActivity(basic);

             }



        });
        back.setOnClickListener(v -> {
            String name = fullname_.getText().toString();
            String age=age_.getText().toString();
            String location = location_.getText().toString();


            if(!name.equals("") || !age.equals("") || !location.equals("")) {

                final Dialog dialog= new Dialog(apply.this);
                dialog.setContentView(R.layout.cancel);
                Button ok,no;
                ok  = dialog.findViewById(R.id.ok);
                no = dialog.findViewById(R.id.cancel);
                ok.setOnClickListener(v1 -> {
                    dialog.dismiss();
                    startActivity(new Intent(apply.this,emp_inter.class));
                });
                no.setOnClickListener(v12 -> dialog.dismiss());
                dialog.show();
            }


            else  {

                startActivity(new Intent(apply.this,emp_inter.class));
            }

        });
    }


    @Override
    public void onBackPressed() {

    }
}
