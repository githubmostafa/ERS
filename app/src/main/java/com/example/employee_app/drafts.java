package com.example.employee_app;

import androidx.annotation.NonNull;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Character.isLowerCase;

public class drafts extends AppCompatActivity {
    FirebaseAuth mAuth;


    List<Ad> Drafts;
    List<String> ids;
    String email;
    FirebaseDatabase db;
    DatabaseReference ref_drafts;
    DatabaseReference  ref_ads;
    ListView listViewDrafts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drafts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Drafts = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String username = parts[0];
        db=FirebaseDatabase.getInstance();
        ref_drafts = db.getReference("Drafts").child(username);
        listViewDrafts = findViewById(R.id.listView_drafts);
        db=FirebaseDatabase.getInstance();
        ref_ads = db.getReference("Advertisements").child(username);
        ids = new ArrayList<>();

        ref_drafts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Drafts.clear();
                ids.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Ad ad = dataSnapshot.getValue(Ad.class);
                    Drafts.add(ad);
                    String id=dataSnapshot.getKey();
                    ids.add(id);
                }
                draft_list adAdapter = new draft_list(drafts.this,Drafts);
                listViewDrafts.setAdapter(adAdapter);
                listViewDrafts.setOnItemClickListener((parent, view, position, id) -> {
                    Ad draft =Drafts.get(position);
                    String title,subtitle,phone,email,details,date,address;
                    title = draft.getTitle();
                    subtitle = draft.getSubtitle();
                    phone=draft.getPhone();
                    email=draft.getEmail();
                    details=draft.getDetails();
                    date = draft.getTimestamp();
                    address=draft.getAddress();
                    String salary_ad=draft.getSalary();
                    String ad_id=ids.get(position);
                    String email_ = mAuth.getCurrentUser().getEmail();
                    String[] parts = email_.split("@");
                    String username = parts[0];
                    String output = username.substring(0, 1).toUpperCase() + username.substring(1);

                    BottomSheetDialog dialog = new BottomSheetDialog(drafts.this);


                    dialog.setContentView(R.layout.draft_detailed);

                    TextView title_dialog,subtitle_dialog,phone_dialog,email_dialog,details_dialog,date_dialog;
                    title_dialog = dialog.findViewById(R.id.jobTitle_Draft);
                    subtitle_dialog = dialog.findViewById(R.id.jobSubtitle_Draft);
                    phone_dialog=dialog.findViewById(R.id.jobPhone_Draft);
                    email_dialog = dialog.findViewById(R.id.jobaddress_Draft);
                    details_dialog = dialog.findViewById(R.id.jobDetails_Draft);
                    date_dialog = dialog.findViewById(R.id.jobDate_Draft);
                    TextView salary_dialog = dialog.findViewById(R.id.jobsalary_Draft);
                    ImageView delete = dialog.findViewById(R.id.deletedraft);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog dialog1 = new Dialog(drafts.this);
                            dialog1.setContentView(R.layout.delete_draft);
                            Button ok,cancel;
                            dialog1.show();
                            ok=dialog1.findViewById(R.id.okdeletedraft);
                            cancel=dialog1.findViewById(R.id.canceldeletedraft);
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.warning_toast,
                                            (ViewGroup) findViewById(R.id.toast_layout_root));
                                    TextView text = layout.findViewById(R.id.text);
                                    Toast toast = new Toast(getApplicationContext());
                                    text.setText("Draft item deleted!");
                                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                                    toast.setDuration(Toast.LENGTH_SHORT);
                                    toast.setView(layout);
                                    toast.show();
                                    DatabaseReference saved=db.getReference("Drafts").child(username).child(ad_id);
                                    saved.removeValue();
                                    dialog1.dismiss();
                                    dialog.dismiss();
                                }
                            });
                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog1.dismiss();
                                }
                            });

                        }
                    });
                    //////////////////////////////////////////////////////
                    title_dialog.setText(title);
                    subtitle_dialog.setText(subtitle);
                    phone_dialog.setText(phone);
                    email_dialog.setText(email);
                    details_dialog.setText(details);
                    date_dialog.setText(date);
                    salary_dialog.setText(salary_ad);
                    ///////////////////////////////////////////////////////
                    Button compose_Draft;
                    compose_Draft = dialog.findViewById(R.id.compose_draft);
                    compose_Draft.setOnClickListener(v -> {
                        dialog.dismiss();

                        // custom dialog
                        final Dialog draft_edit= new Dialog(drafts.this);
                        draft_edit.setContentView(R.layout.edit_draft);
                        EditText title_Edit,subtitle_edit,salary_edit,phone_edit,address_edit,details_edit;
                        Button submit_draft=draft_edit.findViewById(R.id.submit_draft);

                        title_Edit=draft_edit.findViewById(R.id.title_Edit_draft);
                        subtitle_edit = draft_edit.findViewById(R.id.subtitle_edit_draft);
                        phone_edit=draft_edit.findViewById(R.id.phone_edit_draft);
                        address_edit = draft_edit.findViewById(R.id.address_edit_draft);
                        details_edit=draft_edit.findViewById(R.id.details_edit_draft);
                        EditText mail_edit=draft_edit.findViewById(R.id.mail_edit_draft);
                        salary_edit =draft_edit.findViewById(R.id.salary_edit_draft);

                        title_Edit.setText(title);
                        subtitle_edit.setText(subtitle);
                        phone_edit.setText(phone);
                        address_edit.setText(email);
                        details_edit.setText(details);
                        salary_edit.setText(salary_ad);

                        salary_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Dialog dialog = new Dialog(drafts.this);
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
                                        if(!salarymin.equals("") && !salarymax.equals("") && ( Integer.parseInt(salarymax)<= Integer.parseInt(salarymin) )){
                                            LayoutInflater inflater = getLayoutInflater();
                                            View layout = inflater.inflate(R.layout.warning_toast,
                                                    (ViewGroup) findViewById(R.id.toast_layout_root));
                                            TextView text = layout.findViewById(R.id.text);
                                            Toast toast = new Toast(getApplicationContext());
                                            text.setText("Enter a valid salary please");
                                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                                            toast.setDuration(Toast.LENGTH_SHORT);
                                            toast.setView(layout);
                                            toast.show();
                                        }else  if (salarymax.equals("")){
                                            Toast.makeText(drafts.this,"Fill all fields please",Toast.LENGTH_SHORT).show();
                                        }else if (salarymin.equals("")){
                                            Toast.makeText(drafts.this,"Fill all fields please",Toast.LENGTH_SHORT).show();
                                        }else {
                                            dialog.dismiss();
                                            salary_edit.setText(salarymin+"$, up to "+salarymax+"$");

                                        }
                                    }
                                });
                            }
                        });



                        submit_draft.setOnClickListener(v1 -> {
                            String title1 = title_Edit.getText().toString();
                            String subtitle1 = subtitle_edit.getText().toString();
                            String phone1 = phone_edit.getText().toString();
                            String address1 = address_edit.getText().toString();
                            String details1 = details_edit.getText().toString();
                            String email1 = mail_edit.getText().toString();
                            String salary= salary_edit.getText().toString();



                            Date now = new Date();
                            long timestamp = now.getTime();
                            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                            String dateStr = sdf.format(timestamp);

                            if (title1.equals("")) {
                                title_Edit.setError("Fill this field");

                            } else if (subtitle1.equals("")) {
                                subtitle_edit.setError("Fill this field");
                            } else if (phone1.equals("")) {
                                phone_edit.setError("Fill this field");
                            } else if (address1.equals("")) {
                                address_edit.setError("Fill this field");
                            } else if (details1.equals("")) {
                                details_edit.setError("Fill this field");
                            }else if (details1.equals("")) {
                                mail_edit.setError("Fill this field");
                            }
                            else if (title1.length()<10) {
                                title_Edit.setError("Title should be >10 Chars");
                            }

                            else if (subtitle1.length()<10) {
                                subtitle_edit.setError("Sub-Title requires more than 10 Chars");
                            } else if (phone1.length()<6) {
                                phone_edit.setError("Phone requires more than 6 Chars");
                            } else if (address1.length()<12) {
                                mail_edit.setError("Email requires more than 12 Chars");
                            } else if (details1.length()<30) {
                                details_edit.setError("Datails require more than 30 Chars");
                            }
                            else if (email1.length()<12) {
                                address_edit.setError("Email address requires more than 12 Chars");
                            }
                            else if (isLowerCase(title1.charAt(0))){
                                title_Edit.setError("Title should be Capitalized");
                            }






                            else {



                                Ad ad = new Ad(title1, subtitle1,salary, phone1,email1, address1, details1, output, dateStr,ad_id);
                                ref_ads.push().setValue(ad);
                                DatabaseReference ref = db.getReference("Drafts").child(username).child(ad_id);
                                ref.removeValue();

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
                                Intent b = new Intent(drafts.this, admin.class);
                                startActivity(b);

                            }


                        });

                        draft_edit.show();




                    });

                    dialog.show();
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    public void back_drafts(View v){
        startActivity(new Intent(drafts.this,admin.class));
    }
}