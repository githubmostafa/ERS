package com.example.employee_app;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Button bt_google;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    ImageView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

help=findViewById(R.id.login_help);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.LoginEmail);
        password = findViewById(R.id.LoginPassword);
        btnSignIn = findViewById(R.id.SignIn);
        bt_google = (Button) findViewById(R.id.google);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_success,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("You are logged in!");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();                     startActivity(new Intent(login.this,admin.class));
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.redirectingtoast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Please login");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();                 }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your Password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.warning_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Fields are empty");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.warning_toast,
                                        (ViewGroup) findViewById(R.id.toast_layout_root));
                                TextView text = layout.findViewById(R.id.text);
                                Toast toast = new Toast(getApplicationContext());
                                text.setText("Login error, please try again");
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();                             } else {
                                Intent adminIntent = new Intent(login.this,admin.class);

                                startActivity(adminIntent);
                            }
                        }
                    });
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.warning_toast,
                            (ViewGroup) findViewById(R.id.toast_layout_root));
                    TextView text = layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    text.setText("Error occured!");
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();                 }
            }
        });


        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(login.this);
                dialog.setContentView(R.layout.loginhelp);
                dialog.show();


            }


        });




    }
    //////////////////////////////////////////////////////////////////




    

    ////////////////////////////////////////////////////////////////////////
    public void back(View v) {
        startActivity(new Intent(login.this, log_or_start.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    public void onBackPressed() {
        return;
    }
    public void otherway(View view){


    }

    public void ClickOtherWays(View v)
    {

        BottomSheetDialog dialog = new BottomSheetDialog(login.this);

        dialog.setContentView(R.layout.bottomdialog_login);

        dialog.show();

    }
}

