package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseUser;
public class profile extends AppCompatActivity {

    TextView user;
    FirebaseAuth mAuth;


    TextView nameText,bioText,phoneText,emailText,websiteText,profilePhoto;
    ImageView image_profile;
    Uri imageUri;
    UploadTask uploadTask;
    StorageReference storageReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference ;
    FirebaseFirestore dbroot=  FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        user = findViewById(R.id.userEmail);
        mAuth = FirebaseAuth.getInstance();
        user.setText( mAuth.getCurrentUser().getEmail());
        Intent get = getIntent();

        //TextView Referrence
        nameText = findViewById(R.id.CompanyName);
        bioText = findViewById(R.id.about);
        phoneText = findViewById(R.id.Phone);
        emailText = findViewById(R.id.companyEmail);
        websiteText =findViewById(R.id.companywebsite);



        String email_this=mAuth.getCurrentUser().getEmail();
        String []parts = email_this.split("@");
        String username = parts[0];

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
                //setting data
                nameText.setText(name_snap);
                bioText.setText(bio_snap);
                phoneText.setText(phone_snap);
                emailText.setText(email_snap);
                websiteText.setText(web_snap);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            Log.d("failed : ","Cant fetch the user's data");
            }
        });





    }


public void profileBack(View v){

        startActivity(new Intent(profile.this,admin.class));

    }

    public void ClickMoreApps(View v)
    {

    }


    public void edit(View view) {


        Intent i = new Intent(profile.this,editpro.class);
        i.putExtra("name",nameText.getText().toString());
        i.putExtra("bio",bioText.getText().toString());
        i.putExtra("phone",phoneText.getText().toString());
        i.putExtra("email",emailText.getText().toString());
        i.putExtra("web",websiteText.getText().toString());
        startActivity(i);


    }
}




