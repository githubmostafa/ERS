package com.example.employee_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class contact_info extends AppCompatActivity {

    private EditText phone, email, skills, about;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Button back, apply;
        final String error = "Fill up this field please";
        back = findViewById(R.id.backToEducation);
        apply = findViewById(R.id.finalApply);
        phone = findViewById(R.id.phoneNumber_apply);
        email = findViewById(R.id.email_apply);
        skills = findViewById(R.id.skills);
        about = findViewById(R.id.about_emp);

        Intent i = getIntent();
        String username = i.getStringExtra("username");
        String fullname = i.getStringExtra("fullname");
        String age = i.getStringExtra("age");
        String location = i.getStringExtra("location");
        String zip = i.getStringExtra("postalcode");
        String school = i.getStringExtra("school");
        String schooldate = i.getStringExtra("schoolDate");
        String college = i.getStringExtra("college");
        String collegedate = i.getStringExtra("collegeDate");
        String pro = i.getStringExtra("projects");
        String job1 = i.getStringExtra("job1");
        String job2 = i.getStringExtra("job2");
        String job1Date = i.getStringExtra("jobDate1");
        String job2Date = i.getStringExtra("jobDate2");
        String jobTitle = i.getStringExtra("jobTitle");



        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Resumes").child(username);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum, EmailAdd, Skills, AboutEmp;
                phoneNum = phone.getText().toString();
                EmailAdd = email.getText().toString();
                Skills = skills.getText().toString();
                AboutEmp = about.getText().toString();
                Date now = new Date();
                long timestamp = now.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                String dateStr = sdf.format(timestamp);

                if (phoneNum.equals("")) {
                    phone.setError(error);
                } else if (EmailAdd.equals("")) {
                    email.setError(error);
                } else if (Skills.equals("")) {
                    skills.setError(error);
                } else if (AboutEmp.equals("")) {
                    about.setError(error);
                } else {
                    String resume_id = reference.push().getKey();

                    Resume resume = new Resume(jobTitle, fullname, age, location, zip, school, schooldate, college,
                            collegedate, pro, phoneNum, EmailAdd, Skills, AboutEmp, job1, job2, job1Date, job2Date, dateStr, resume_id);

                    reference.push().setValue(resume);

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_success,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Resume sent to "+username);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();

                    startActivity(new Intent(contact_info.this, emp_inter.class));

                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job1 = phone.getText().toString();
                String jobd1 = skills.getText().toString();
                String job2 = email.getText().toString();
                String jobd2 = about.getText().toString();


                    final Dialog dialog = new Dialog(contact_info.this);
                    dialog.setContentView(R.layout.cancel);
                    Button ok, no;
                    ok = dialog.findViewById(R.id.ok);
                    no = dialog.findViewById(R.id.cancel);
                    ok.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        startActivity(new Intent(contact_info.this, job.class));
                    });
                    no.setOnClickListener(v12 -> dialog.dismiss());
                    dialog.show();


            }
        });




    }


}