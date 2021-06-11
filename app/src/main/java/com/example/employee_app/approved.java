package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class approved extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference reference;
    ListView listViewAds;
    List<Resume> resumes;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        resumes = new ArrayList<Resume>();
        listViewAds = findViewById(R.id.resumes_approved);
        Intent employeeApprove = new Intent (approved.this,approve_emp.class);
        mauth = FirebaseAuth.getInstance();
        String email = mauth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String usr = parts[0];

        String output = usr.substring(0, 1).toUpperCase() + usr.substring(1);
        db=FirebaseDatabase.getInstance();
        List<String> ids = new ArrayList<>();
        reference = db.getReference("Approved").child(output);
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
                resume_list resumeAdapter = new resume_list(approved.this,resumes);
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
                String fullname,
                        phone,email;

                fullname = resume.getFullname();

                phone=resume.getPhone();
                email=resume.getEmail();
                BottomSheetDialog dialog = new BottomSheetDialog(approved.this);
                dialog.setContentView(R.layout.approveddialog);
                dialog.show();
                TextView name =dialog.findViewById(R.id.contactname);
                name.setText("Contact "+fullname);
                Button mailed,phoned;
                mailed=dialog.findViewById(R.id.mailApproved);
                phoned =dialog.findViewById(R.id.phoneApproved);
                mailed.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"+email));

                    startActivity(intent);
                });
                phoned.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phone));
                    startActivity(intent);
                });
                }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();





    }
    public void backToAdmin_approved(View view){
        Intent inbox = new Intent(com.example.employee_app.approved.this,admin.class);
        startActivity(inbox);
    }
}