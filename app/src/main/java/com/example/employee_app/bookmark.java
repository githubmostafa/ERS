package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class bookmark extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference reference;

    List<Resume> resumes;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
        ActionBar actionBar = getSupportActionBar();
        Intent employeeApprove = new Intent (bookmark.this,approve_Saved.class);
        actionBar.hide();
        resumes = new ArrayList<Resume>();
        ListView listViewAds = findViewById(R.id.listView_saved);
        db = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        String email = mauth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String usr = parts[0];

        String output = usr.substring(0, 1).toUpperCase() + usr.substring(1);
        db=FirebaseDatabase.getInstance();
        List<String> ids = new ArrayList<>();
        reference = db.getReference("Saved").child(output);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resumes.clear();
                ids.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    Resume s=dataSnapshot.getValue(Resume.class);
                    resumes.add(s);
                    String id = dataSnapshot.getKey();
                    ids.add(id);
                }
                resume_list resumeAdapter = new resume_list(bookmark.this,resumes);
                listViewAds.setAdapter(resumeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resume resume = resumes.get(position);
                String fullname,age,location,zip,
                        school,schooDate,college,collegeDate,projects,
                        phone,email,skills,about,job1,job2,jobdate1,jobdate2, date,resumeid;
                String jobTitle;
                fullname = resume.getFullname();
                age=resume.getAge();
                location=resume.getLocation();
                zip=resume.getZip();
                school=resume.getSchool();
                schooDate=resume.getSchooDate();
                college=resume.getCollege();
                collegeDate=resume.getCollegeDate();
                projects=resume.getProjects();
                phone=resume.getPhone();
                email=resume.getEmail();
                skills=resume.getSkills();
                about=resume.getAbout();
                job1=resume.getJob1();
                job2=resume.getJob2();
                jobdate1=resume.getJobdate1();
                jobdate2=resume.getJobdate2();
                date=resume.getDate();
                jobTitle=resume.getJobTitle();
                String resume_id = ids.get(position);
                ImageView save = findViewById(R.id.resumeBookmark);


                employeeApprove.putExtra("jobTitle",jobTitle);
                employeeApprove.putExtra("fullname",fullname);
                employeeApprove.putExtra("age",age);
                employeeApprove.putExtra("location",location);
                employeeApprove.putExtra("zip",zip);
                employeeApprove.putExtra("school",school);
                employeeApprove.putExtra("schooDate",schooDate);
                employeeApprove.putExtra("college",college);
                employeeApprove.putExtra("collegeDate",collegeDate);
                employeeApprove.putExtra("projects",projects);
                employeeApprove.putExtra("phone",phone);
                employeeApprove.putExtra("email",email);
                employeeApprove.putExtra("skills",skills);
                employeeApprove.putExtra("about",about);
                employeeApprove.putExtra("job1",job1);
                employeeApprove.putExtra("job2",job2);
                employeeApprove.putExtra("jobdate1",jobdate1);
                employeeApprove.putExtra("jobdate2",jobdate2);
                employeeApprove.putExtra("date",date);
                employeeApprove.putExtra("username",output);
                employeeApprove.putExtra("id",ids.get(position));

                startActivity(employeeApprove);



             }
        });


    }
    public void backAdmin(View view){
        Intent inbox = new Intent(com.example.employee_app.bookmark.this,admin.class);
        startActivity(inbox);
    }
}