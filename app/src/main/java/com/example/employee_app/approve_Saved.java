package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class approve_Saved extends AppCompatActivity {
    TextView name,
            telePhone,mail,title;
    FirebaseAuth mAuth;
    FirebaseFirestore dbroot;
    FirebaseDatabase db ;
    DatabaseReference saved;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_saved);
        Intent getInfo = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        name = findViewById(R.id.name_employee_saved);
        telePhone = findViewById(R.id.phone_Approve_Saved);
        mail = findViewById(R.id.mail_Approve_saved);
        title = findViewById(R.id.for_Job_saved);
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
        String username = getInfo.getStringExtra("username");
        String id = getInfo.getStringExtra("id");

        name.setText(fullname);
        telePhone.setText(phone);
        mail.setText(email);
        title.setText("This Resume was sent to your Ad of title: " + jobTitle+" on "+date1);

        TextView agee,loc,post,scl,clg,prj,st,nd,hobbies,abt;
        agee = findViewById(R.id.age_Saved);
        loc = findViewById(R.id.location_saved);
        scl=findViewById(R.id.scl_saved);
        clg=findViewById(R.id.clg_saved);
        prj=findViewById(R.id.prj_saved);
        st=findViewById(R.id.st_saved);
        nd=findViewById(R.id.nd_saved);
        hobbies=findViewById(R.id.hobbies_saved);
        abt=findViewById(R.id.abt_saved);


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
        Button approve = findViewById(R.id.approved_saved_check);
        db=FirebaseDatabase.getInstance();
        DatabaseReference reference = db.getReference("Approved").child(username);
        reference.orderByKey().equalTo(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists
                    approve.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        dbroot=  FirebaseFirestore.getInstance();

        ImageView delete = findViewById(R.id.delete_Saved);
        delete.setOnClickListener(v ->{
            Dialog dialog = new Dialog(approve_Saved.this);
            dialog.setContentView(R.layout.remove_item);
            Button remove,dontRemove;
            remove = dialog.findViewById(R.id.okRemove);
            dontRemove=dialog.findViewById(R.id.cancelRemove);
            dialog.show();
            dontRemove.setOnClickListener(v1 -> dialog.dismiss());
            remove.setOnClickListener(v12 -> {
                db=FirebaseDatabase.getInstance();
                saved=db.getReference("Saved").child(username).child(id);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.warning_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("Removed item");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                saved.removeValue();
                startActivity(new Intent(approve_Saved.this,bookmark.class));
            });

        });



}
    public void callEmpSaved(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+telePhone.getText().toString()));
        startActivity(intent);

    }
    public void mailEmpSaved(View v){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+mail.getText().toString()));
        startActivity(intent);
    }

    public void backToSaved(View v) {
        startActivity(new Intent(approve_Saved.this, bookmark.class));
    }
}