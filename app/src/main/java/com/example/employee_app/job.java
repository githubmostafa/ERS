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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;
import java.util.List;

public class job extends AppCompatActivity {
FirebaseDatabase database;
DatabaseReference reference;
String job1,job2,jobdate1,jobdate2;
EditText jobI,jobII,jobDateI,jobDateII;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
        String pro=i.getStringExtra("projects");
        String jobTitle=i.getStringExtra("jobTitle");

        Intent job = new Intent(job.this,contact_info.class);

        Button back = findViewById(R.id.backToEducation);
        Button next = findViewById(R.id.jobnext);
        jobI = findViewById(R.id.job1);
        jobII = findViewById(R.id.job2);
        jobDateI = findViewById(R.id.jobdate1);

        jobDateI.setOnClickListener(new View.OnClickListener() {
            @Override

                public void onClick(View v) {
                    Dialog dialog=new Dialog(job.this);
                    dialog.setContentView(R.layout.yearspinner);
                    Spinner year = dialog.findViewById(R.id.yourid);
                    Spinner yearEnd = dialog.findViewById(R.id.yourid1);
                    Button ok = dialog.findViewById(R.id.chooseYear);
                      dialog.show();
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
                                text12.setText("Invalid date!");
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }else if (yearStart<=yearEnd){
                                jobDateI.setText(text+" till "+text1);
                                dialog.dismiss();
                            }
                        }
                    });


                }

            });



        jobDateII = findViewById(R.id.jobdate2);

        jobDateII.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Dialog dialog=new Dialog(job.this);
                dialog.setContentView(R.layout.yearspinner);
                Spinner year = dialog.findViewById(R.id.yourid);
                Spinner yearEnd = dialog.findViewById(R.id.yourid1);
                Button ok = dialog.findViewById(R.id.chooseYear);
                dialog.show();
                ok.setOnClickListener(v13 -> {
                    String text = year.getSelectedItem().toString();
                    String text1 = yearEnd.getSelectedItem().toString();
                    int yearStart=Integer.parseInt(text);
                    int yearEnd1 =Integer.parseInt(text1);
                    if(yearStart> yearEnd1){
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.warning_toast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text12= layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text12.setText("Invalid date!");
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }else if (yearStart<= yearEnd1){
                        jobDateII.setText(text+" till "+text1);
                        dialog.dismiss();
                    }
                });


            }

        });






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String job1 = jobI.getText().toString();
                String jobd1 = jobDateI.getText().toString();
                String job2 = jobII.getText().toString();
                String jobd2 = jobDateII.getText().toString();
     final Dialog dialog = new Dialog(job.this);
                    dialog.setContentView(R.layout.cancel);
                    Button ok, no;
                    ok = dialog.findViewById(R.id.ok);
                    no = dialog.findViewById(R.id.cancel);
                    ok.setOnClickListener(v1 -> {
                        dialog.dismiss();
                        startActivity(new Intent(job.this, education.class));
                    });
                    no.setOnClickListener(v12 -> dialog.dismiss());
                    dialog.show();


            }
        });









        next.setOnClickListener(v -> {
            job1 = jobI.getText().toString();
            job2 = jobII.getText().toString();
            jobdate1=jobDateI.getText().toString();
            jobdate2=jobDateII.getText().toString();
            Toast.makeText(job.this,jobdate1,Toast.LENGTH_LONG).show();
            if(job1.equals("") && job2.equals("")){
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.warning_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("At least one work place is required!");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }
            else if(jobdate1.equals("") && jobdate2.equals("")){
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.warning_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("At least one work place is required!");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
            }else if(job1.length()<8 && job2.length()<8){
                jobI.setError("A job input requires more than 8 chars");
            }


            else{
                job.putExtra("username",username);
                job.putExtra("fullname",fullname);
                job.putExtra("age",age);
                job.putExtra("zip",zip);
                job.putExtra("location",location);
                job.putExtra("school",school);
                job.putExtra("schoolDate",schooldate);
                job.putExtra("college",college);
                job.putExtra("collegeDate",collegedate);
                job.putExtra("projects",pro);
                job.putExtra("job1",job1);
                job.putExtra("job2",job2);
                job.putExtra("jobDate1",jobdate1);
                job.putExtra("jobDate2",jobdate2);
                job.putExtra("jobTitle",jobTitle);



                startActivity(job);
            }

        });

    }

    @Override
    public void onBackPressed() {

    }
}