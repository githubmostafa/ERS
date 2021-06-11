package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    public EditText emailId, password, confirmpassword;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    private Button close_;
ImageView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        help = findViewById(R.id.register_help);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confirmpassword= findViewById(R.id.ConfirmPassword);
        btnSignUp = findViewById(R.id.SignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String confpwd = confirmpassword.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your Password");
                    password.requestFocus();
                }
                else if(confpwd.isEmpty()){
                    confirmpassword.setError("Please Enter Confirm Password");
                    confirmpassword.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty() && confpwd.isEmpty()){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.warning_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Fill all fields please");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else if(!(pwd.equals(confpwd))){
                        LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.warning_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Password Or Confirm Password Not Match!");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty() && confpwd.isEmpty()) && pwd.equals(confpwd)){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.warning_toast,
                                        (ViewGroup) findViewById(R.id.toast_layout_root));
                                TextView text = layout.findViewById(R.id.text);
                                Toast toast = new Toast(getApplicationContext());
                                text.setText("Failed to sign uo, please try agian!");
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                            }
                            else{

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_success,
                                        (ViewGroup) findViewById(R.id.toast_layout_root));
                                TextView text = layout.findViewById(R.id.text);
                                Toast toast = new Toast(getApplicationContext());
                                text.setText("Signed up successfully!");
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();                                startActivity(new Intent(register.this,admin.class));
                            }
                        }
                    });
                }
                else{
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.warning_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Error occured!");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();                }
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(register.this);
                dialog.setContentView(R.layout.custom_register_help);
                dialog.show();


            }


        });
    }





    public  void Back(View v){
        startActivity(new Intent (register.this,log_or_start.class));

    }




}
