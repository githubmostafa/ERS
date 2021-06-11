package com.example.employee_app;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static java.lang.Character.isLowerCase;

public class

manage_ad extends AppCompatActivity {
    EditText title, subtitle, phone, address, details,mailaddress;
    Button submit;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    FirebaseAuth mAuth;
    ImageView back;
    ImageView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage__ad);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        help = findViewById(R.id.compose_help);
        mAuth = FirebaseAuth.getInstance();

        title = findViewById(R.id.title);
        subtitle = findViewById(R.id.subtitle);
        phone = findViewById(R.id.phone);
        mailaddress=findViewById(R.id.mailaddress);
        address = findViewById(R.id.address);
        details = findViewById(R.id.details);
        submit = findViewById(R.id.submit);
        back = findViewById(R.id.ClickBack);
        EditText salary = findViewById(R.id.salary);

        salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(manage_ad.this);
                dialog.setContentView(R.layout.salarydialog);
                dialog.show();
                EditText min = dialog.findViewById(R.id.salarymin);
                EditText max = dialog.findViewById(R.id.salarymax);
                min.setText("1000");
                max.setText("1200");

                Button submitSalary = dialog.findViewById(R.id.submitSalary);
                submitSalary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String salarymin=min.getText().toString();
                        String salarymax = max.getText().toString();
                        if(salarymin.equals("")){
                            min.setError("Field required");
                        }
                        else if(salarymax.equals("")){
                            max.setError("Field requireed");
                        }
                        else if (!salarymin.equals("") && !salarymax.equals("") && ( Integer.parseInt(salarymax)<= Integer.parseInt(salarymin) )){
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.warning_toast,
                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                            TextView text = layout.findViewById(R.id.text);
                            Toast toast = new Toast(getApplicationContext());
                            text.setText("Enter a valid salary");
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();

                        }



                                         else {

                            dialog.dismiss();

                                salary.setText(salarymin+"$, up to "+salarymax+"$");



                        }
                    }
                });
            }
        });
        String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
        assert email != null;
        String[] parts = email.split("@");

        String username = parts[0];
        String output = username.substring(0, 1).toUpperCase() + username.substring(1);

        submit.setOnClickListener(v -> {


            String title1 = title.getText().toString();
            String subtitle1 = subtitle.getText().toString();
            String phone1 = phone.getText().toString();
            String address1 = address.getText().toString();
            String details1 = details.getText().toString();
            String email1=mailaddress.getText().toString();
            String wage=salary.getText().toString();


            Date now = new Date();
            long timestamp = now.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String dateStr = sdf.format(timestamp);




            if (title1.equals("")) {
                title.setError("Fill this field");

            } else if (subtitle1.equals("")) {
                subtitle.setError("Fill this field");
            } else if (phone1.equals("")) {
                phone.setError("Fill this field");
            }else if (address1.equals("")) {
                address.setError("Fill this field");
            }else if (details1.equals("")) {
                details.setError("Fill this field");
            }else if (title1.length()<10) {
                title.setError("Title should be more than 10 Chars");
            }
            else if (subtitle1.length()<10) {
                subtitle.setError("Sub-Title requires more than 10 Chars");
            } else if (phone1.length()<6) {
                phone.setError("Phone requires more than 6 Chars");
            } else if (address1.length()<12) {
                address.setError("Location address requires more than 12 Chars");}
                else if (email1.length()<12) {
                    address.setError("Email address requires more than 12 Chars");
            } else if (details1.length()<30) {
                details.setError("Deatails require more than 30 Chars");
            }else if (isLowerCase(title1.charAt(0))){
                title.setError("Title should be Capitalized");
            }

            else {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Advertisements").child(username);

                String ad_id = reference.push().getKey();

                Ad ad = new Ad(title1, subtitle1,wage, phone1,email1, address1, details1, output, dateStr, ad_id);
                reference.push().setValue(ad);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_success,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("Ad submitted successfully");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                Intent b = new Intent(manage_ad.this, admin.class);
                startActivity(b);
            }
        });
        back.setOnClickListener(v -> {
            String title1 = title.getText().toString();
            String subtitle1 = subtitle.getText().toString();
            String phone1 = phone.getText().toString();
            String address1 = address.getText().toString();
            String details1 = details.getText().toString();
            String email1 = mailaddress.getText().toString();
            String wage = salary.getText().toString();

            Date now = new Date();
            long timestamp = now.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            String dateStr = sdf.format(timestamp);
            if ((!title1.isEmpty()) || (!subtitle1.isEmpty()) || (!phone1.isEmpty()) || (!address1.isEmpty()) || (!details1.isEmpty())) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_success,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("Saved as draft");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();
                Intent back = new Intent(manage_ad.this, admin.class);
                startActivity(back);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Drafts").child(username);

                String ad_id = reference.push().getKey();
                Ad draft = new Ad(title1, subtitle1,"", phone1,email1, address1, details1, output, dateStr,ad_id);
                reference.push().setValue(draft);
            }
            else{
                Intent back = new Intent(manage_ad.this, admin.class);
                startActivity(back);
            }
        });
        help.setOnClickListener(v -> {
            // custom dialog
            final Dialog dialog = new Dialog(manage_ad.this);
            dialog.setContentView(R.layout.composehelp);
            dialog.show();
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}