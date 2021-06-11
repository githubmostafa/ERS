package com.example.employee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class education extends AppCompatActivity {
String username,fullname,age,location,zip;
EditText highschool,highschoolDate,collage,collageDate,Projects;
    Button backToBasic,nextToContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_education);
        Intent i = getIntent();
        String error = "Fill up this field please";
        ////////////////////////////////////////////////////
        username = i.getStringExtra("username");
        fullname=i.getStringExtra("fullname");
        age = i.getStringExtra("age");
        location = i.getStringExtra("location");
        zip = i.getStringExtra("zip");
        String jobTitle = i.getStringExtra("jobtitle");
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Intent education = new Intent(com.example.employee_app.education.this,job.class);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        nextToContact=findViewById(R.id.educationnext);
        highschool=findViewById(R.id.highschool);
        highschoolDate=findViewById(R.id.schooldate);
        collage=findViewById(R.id.collage);
        collageDate=findViewById(R.id.college_date);
        Projects=findViewById(R.id.projects);
        backToBasic = findViewById(R.id.backTobasic);
        /////////////////////////////////////////////////////

        highschoolDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(education.this);
                dialog.show();
                dialog.setContentView(R.layout.yearspinner);
                Spinner year = dialog.findViewById(R.id.yourid);
                Spinner yearEnd = dialog.findViewById(R.id.yourid1);
                Button ok = dialog.findViewById(R.id.chooseYear);
                ok.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String text = year.getSelectedItem().toString();
                        String text1 = yearEnd.getSelectedItem().toString();
                        int yearStart=Integer.parseInt(text);
                        int yearEnd=Integer.parseInt(text1);



                        if(yearStart>yearEnd){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.warning_toast,
                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                            TextView text12 = layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            text12.setText("Invalid date");
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }else {
                            highschoolDate.setText(text+" till "+text1);
                            dialog.dismiss();
                        }


                    }
                });
            }
        });
        collageDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(education.this);
                dialog.show();
                dialog.setContentView(R.layout.yearspinner);
                Spinner year = dialog.findViewById(R.id.yourid);
                Spinner yearEnd = dialog.findViewById(R.id.yourid1);
                Button ok = dialog.findViewById(R.id.chooseYear);
                ok.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View v) {
                        String text = year.getSelectedItem().toString();
                        String text1 = yearEnd.getSelectedItem().toString();
                        int yearStart=Integer.parseInt(text);
                        int yearEnd=Integer.parseInt(text1);
                        if(yearStart>yearEnd){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.warning_toast,
                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                            TextView text12 = layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            text12.setText("Invalid date");
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }else {
                            collageDate.setText(text+" till "+text1);
                            dialog.dismiss();
                        }
                    }
                });

            }
        });



        nextToContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String school,schooldate,college,collegedate,project;
                school=highschool.getText().toString();
                schooldate=highschoolDate.getText().toString();
                college=collage.getText().toString();
                collegedate=collageDate.getText().toString();
                project=Projects.getText().toString();
                if(school.equals("")){
                    highschool.setError(error);

                }else if(schooldate.equals("")){
                    highschoolDate.setError(error);
                }else if (college.equals("")){
                    collage.setError(error);
                }else if (collegedate.equals("")){
                    collageDate.setError(error);
                }else if (project.equals("")){
                    Projects.setError(error);
                } else if(school.length()<6){
                    highschool.setError("School input requires more than 6 chars");


                }else if (college.length()<6){
                    collage.setError("College input requires more than 6 chars");
                }else if (project.length()<10){
                    Projects.setError("Projects input requires more than 10 chars");
                }

                else {

                    education.putExtra("username",username);
                    education.putExtra("fullname",fullname);
                    education.putExtra("age",age);
                    education.putExtra("location",location);
                    education.putExtra("postalcode",zip);
                    education.putExtra("school",school);
                    education.putExtra("schoolDate",schooldate);
                    education.putExtra("college",college);
                    education.putExtra("collegeDate",collegedate);
                    education.putExtra("projects",project);
                    education.putExtra("jobTitle",jobTitle);


                    startActivity(education);
                }
            }

        });
        backToBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(education.this);
                    dialog.setContentView(R.layout.cancel);
                    Button ok, no;
                    ok = dialog.findViewById(R.id.ok);
                    no = dialog.findViewById(R.id.cancel);
                    ok.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        startActivity(new Intent(education.this, apply.class));
                    });
                    no.setOnClickListener(v12 -> dialog.dismiss());
                    dialog.show();


            }
        });




        }

    @Override
    public void onBackPressed() {
    }
}