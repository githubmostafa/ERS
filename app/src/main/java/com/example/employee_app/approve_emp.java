package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class approve_emp extends AppCompatActivity {

    TextView name,
            telePhone,mail,title;
    FirebaseAuth mAuth;
    FirebaseFirestore dbroot;
    FirebaseDatabase db ;
    DatabaseReference saved,saved1;

    DatabaseReference getSaved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_emp);
        db = FirebaseDatabase.getInstance();


        Intent getInfo = getIntent();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        Button approve = findViewById(R.id.approve);
        name = findViewById(R.id.name_employee);
        telePhone = findViewById(R.id.phone_Approve);
        mail = findViewById(R.id.mail_Approve);
        title = findViewById(R.id.for_Job);
        String jobTitle = getInfo.getStringExtra("jobTitle");
        String fullname = getInfo.getStringExtra("fullname");
        String projects = getInfo.getStringExtra("projects");
        String age = getInfo.getStringExtra("age");
        String phone = getInfo.getStringExtra("phone");
        String location = getInfo.getStringExtra("location");
        String email = getInfo.getStringExtra("email");
        String zip = getInfo.getStringExtra("zip");
        String skills = getInfo.getStringExtra("skills");
        String school = getInfo.getStringExtra("school");
        String about = getInfo.getStringExtra("about");
        String schoolDate = getInfo.getStringExtra("schooDate");
        String job1 = getInfo.getStringExtra("job1");
        String college = getInfo.getStringExtra("college");
        String job2 = getInfo.getStringExtra("job2");
        String collegedate = getInfo.getStringExtra("collegeDate");
        String jobDate1 = getInfo.getStringExtra("jobdate1");
        String jobdate2 = getInfo.getStringExtra("jobdate2");
        String date1 = getInfo.getStringExtra("date");
        String resumeId = getInfo.getStringExtra("resumeid");
        String username = getInfo.getStringExtra("username");
        String id = getInfo.getStringExtra("id");

        ImageView save = findViewById(R.id.resumeBookmark);
        ImageView issaved = findViewById(R.id.saved);
        Button approved_btn = findViewById(R.id.approved);
        TextView savedd = findViewById(R.id.savedd);
        DatabaseReference ifsaved = db.getReference("Saved").child(username);
        DatabaseReference ifapproved = db.getReference("Approved").child(username);

        ifsaved.orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists
                    issaved.setVisibility(View.VISIBLE);
                    save.setVisibility(View.GONE);
                    savedd.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        ifapproved.orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists

                    approve.setVisibility(View.GONE);
                    approved_btn.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        name.setText(fullname);
        telePhone.setText(phone);
        mail.setText(email);
        title.setText("This Resume was sent to your Ad of title: " + jobTitle+" on "+date1);

       TextView agee,loc,post,scl,clg,prj,st,nd,hobbies,abt;
        agee = findViewById(R.id.age);
        loc = findViewById(R.id.location);
        scl=findViewById(R.id.scl);
        clg=findViewById(R.id.clg);
        prj=findViewById(R.id.prj);
        st=findViewById(R.id.st);
        nd=findViewById(R.id.nd);
        hobbies=findViewById(R.id.hobbies);
        abt=findViewById(R.id.abt);
        //set these textViews with by the values steve got
        agee.setText(age);
        loc.setText(location);
        scl.setText(school+", From "+schoolDate);
        clg.setText(college+", From "+collegedate);
        prj.setText(projects);
        st.setText(job1+", From "+jobDate1);
        nd.setText(job2+", From "+jobdate2);
        hobbies.setText(skills);
        abt.setText(about);


        dbroot=  FirebaseFirestore.getInstance();



        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        assert currentFirebaseUser != null;
        String currentUser = currentFirebaseUser.getUid();
        DocumentReference documentReference = dbroot
                .collection("users")
                .document(currentUser);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //getting data
                String name_snap = documentSnapshot.getString("Company Name");
                String bio_snap = documentSnapshot.getString("Company Bio");
                String phone_snap = documentSnapshot.getString("Company Phone");
                String email_snap = documentSnapshot.getString("Company Email");
                String web_snap = documentSnapshot.getString("Company Website");


                approve.setOnClickListener(v -> {
                    BottomSheetDialog approve1 = new BottomSheetDialog(approve_emp.this);
                    approve1.setContentView(R.layout.bottomdialog_approve);
                    Button mailApprove,phoneApprove;
                    mailApprove = approve1.findViewById(R.id.mailApprove);
                    phoneApprove = approve1.findViewById(R.id.phoneApprove);
                    approve1.show();
                    mailApprove.setOnClickListener(v1 -> {
                        final DatabaseReference approved = db.getReference("Approved").child(username).child(id);
                        Resume resume = new Resume(jobTitle,fullname,age,location,zip,school,schoolDate,college,
                                collegedate,projects,phone,email,skills,about,job1,job2,jobDate1,jobdate2,date1,resumeId);

                        approved.setValue(resume);

                        String body = "Dear "+fullname+"\n\n\nThank you for your Resume on our ad " +jobTitle+",At "+name_snap+
                                ",\nwe are delighted to formally accept  your resume," +
                                " \nand We are very much looking forward to joining our team." +

                                "\n\nPlease contact us to discuss the job." +
                                "\n\n\nOur Email: "+email_snap+"\n" +
                                "Our Phone: "+phone_snap+"\n" +
                                "or visit our website at: "+web_snap
                                +"\n\n\n\n"+name_snap+": "+bio_snap
                                        ;


                        final Intent emailIntent = new Intent( Intent.ACTION_SEND);

                        emailIntent.setType("plain/text");

                        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                                new String[] { email });

                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                                "Resume accepted - "+username);

                        emailIntent.putExtra(Intent.EXTRA_TEXT,
                                body);
                        approve1.dismiss();
                        startActivity(emailIntent);
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.redirectingtoast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text.setText("Redirecting...");
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();


                    });

                    phoneApprove.setOnClickListener(v12 -> {
                        final DatabaseReference approved = db.getReference("Approved").child(username).child(id);
                        Resume resume = new Resume(jobTitle,fullname,age,location,zip,school,schoolDate,college,
                                collegedate,projects,phone,email,skills,about,job1,job2,jobDate1,jobdate2,date1,"resumeId");

                        approved.setValue(resume);

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+telePhone.getText().toString()));
                        approve1.dismiss();

                        startActivity(intent);
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.redirectingtoast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text.setText("Redirecting...");
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                    });




                });




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failed : ","Cant fetch the user's data");
            }
        });



       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               saved=db.getReference("Saved").child(username).child(id);
               Resume resume = new Resume(jobTitle,fullname,age,location,zip,school,schoolDate,college,
                       collegedate,projects,phone,email,skills,about,job1,job2,jobDate1,jobdate2,date1,resumeId);

               saved.setValue(resume);

           }
       });
        ImageView delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(approve_emp.this);
                dialog.setContentView(R.layout.delete_item);
                Button remove,dontRemove;
                remove = dialog.findViewById(R.id.okdelete);
                dontRemove=dialog.findViewById(R.id.canceldelete);
                dialog.show();
                dontRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saved=db.getReference("Resumes").child(username).child(id);
                        saved1=db.getReference("Saved").child(username).child(id);
                        saved1.removeValue();
                        saved.removeValue();

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.warning_toast,
                                (ViewGroup) findViewById(R.id.toast_layout_root));
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text.setText("Resume deleted successfully!");
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                        startActivity(new Intent(getApplicationContext(),inbox.class));
                    }
                });

            }
        });








    }
    public void callEmp(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+telePhone.getText().toString()));
        startActivity(intent);

    }
    public void mailEmp(View v){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+mail.getText().toString()));

        startActivity(intent);
    }
            public void backToInbox(View v) {
                startActivity(new Intent(approve_emp.this, inbox.class));
            }
        }
