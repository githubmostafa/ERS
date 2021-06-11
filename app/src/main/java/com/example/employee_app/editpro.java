package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editpro extends AppCompatActivity {
    private static final int REQUEST_OK = -1;
    FirebaseFirestore dbroot;
CollectionReference reference;
private  StorageReference mStorageRef;
private DatabaseReference mDatabaseRef;
private StorageTask uploadTask;


Button save ;


FirebaseAuth mauth;
DatabaseReference databaseReference;



    private EditText name,bio,phone,mail,web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpro);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //reference for edittexts
        name = findViewById(R.id.editname);
        bio = findViewById(R.id.editBio);
        phone = findViewById(R.id.editPhone);
        mail=findViewById(R.id.editEmail);
        web = findViewById(R.id.editwebsite);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            String name_ =(String) b.get("name");
            String bio_ =(String) b.get("bio");
            String phone_ =(String) b.get("phone");
            String email_ =(String) b.get("email");
            String website_ =(String) b.get("web");

            name.setText(name_);
            bio.setText(bio_);
            phone.setText(phone_);
            mail.setText(email_);
            web.setText(website_);
        }
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
        String mail_ = mauth.getCurrentUser().getEmail();
        String[] parts = mail_.split("@");
        String username = parts[0];



        //reference for button save
        save = findViewById(R.id.save_all);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_C = name.getText().toString();
                String bio_C = bio.getText().toString();
                String phone_C = phone.getText().toString();
                String mail_C = mail.getText().toString();
                String web_c = web.getText().toString();

                if (name_C.equals("")){
                    name.setError("Input Required");
                }else if(bio_C.equals("")){
                    bio.setError("Input Required");
                }else if(phone_C.equals("")){
                    phone.setError("Input Required");
                }else if (mail_C.equals("")){
                    mail.setError("Input Required");
                }else if (web_c.equals("")){
                    web.setError("Input Required");
                }else {
                    Intent saved = new Intent(editpro.this,profile.class);

                    startActivity(saved);

                    dbroot = FirebaseFirestore.getInstance();
                    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                    assert currentFirebaseUser != null;
                    String currentUser = currentFirebaseUser.getUid();
                    DocumentReference documentReference = dbroot
                            .collection("users")
                            .document(currentUser);
                    Map<String, Object> user = new HashMap<String, Object>();
                    user.put("Company Name", name_C);
                    user.put("Company Bio", bio_C);
                    user.put("Company Phone", phone_C);
                    user.put("Company Email", mail_C);
                    user.put("Company Website", web_c);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG", "Profile saved Successfully");
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_success,
                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                            TextView text = layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            text.setText("Profile edited successfully!");
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG", "SomeThin Went Wrong!");
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.warning_toast,
                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                            TextView text = layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            text.setText("Something went wrong!");
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }
                    });


                }


                }



        });





    }
    public void editprofileBack(View view){
        startActivity(new Intent(editpro.this,profile.class));
    }

    @Override
    public void onBackPressed() {
        return;
    }



    private  String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }



}