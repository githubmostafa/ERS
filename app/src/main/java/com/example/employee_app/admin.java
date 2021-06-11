package com.example.employee_app;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static java.lang.Character.isLowerCase;

public class admin extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView Acount;
    FirebaseAuth mAuth;
    ImageView openmenu;
    List <String> rates;
    List<Ad> advertisements;

    String email;
    FirebaseDatabase db;
    DatabaseReference ref_ads;
    ListView listViewAds;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        advertisements = new ArrayList<>();
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        drawerLayout = findViewById(R.id.drawer_layout_emp);
        Acount = findViewById(R.id.accountText);

        mAuth = FirebaseAuth.getInstance();
        email = mAuth.getCurrentUser().getEmail();
        String[] parts = email.split("@");
        String username = parts[0];
        db=FirebaseDatabase.getInstance();
        ref_ads = db.getReference("Advertisements").child(username);
        DatabaseReference reference = db.getReference("rate");
        LinearLayout layout = findViewById(R.id.rateus);
        LinearLayout layout1 = findViewById(R.id.rated);


        reference.orderByKey().equalTo(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists

                    layout.setVisibility(View.GONE);
                    layout1.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        List<String> ids = new ArrayList<>();
        ref_ads.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                advertisements.clear();
                ids.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Ad ad = dataSnapshot.getValue(Ad.class);
                    advertisements.add(ad);
                    String id = dataSnapshot.getKey();
                    ids.add(id);

                }
                ad_list adAdapter = new ad_list(admin.this,advertisements);
                listViewAds.setAdapter(adAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Acount.setText(mAuth.getCurrentUser().getEmail());
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        openmenu = findViewById(R.id.open_menu);
        openmenu.setOnClickListener(v -> {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(admin.this, openmenu);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.toolbar_menu, popup.getMenu());

            popup.show();
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(new Intent(admin.this, manage_ad.class)));
        listViewAds = findViewById(R.id.listView);
        listViewAds.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ad ad = advertisements.get(position);

                String title,subtitle,phone,email,details,date,mail;
                title = ad.getTitle();
                subtitle = ad.getSubtitle();
                phone=ad.getPhone();
                email=ad.getAddress();
                details=ad.getDetails();
                date = ad.getTimestamp();
                mail=ad.getEmail();
                String salary = ad.getSalary();
                String adId = ids.get(position);


                BottomSheetDialog dialog_Edit = new BottomSheetDialog(admin.this);


                dialog_Edit.setContentView(R.layout.ad_detailed);
                TextView title_dialog,subtitle_dialog,phone_dialog,email_dialog,details_dialog,date_dialog,mail_dialog;
                ///////////////////////////////////////////////////////
                title_dialog = dialog_Edit.findViewById(R.id.Title_edit_bottom);
                subtitle_dialog = dialog_Edit.findViewById(R.id.Subtitle_edit_bottom);
                phone_dialog=dialog_Edit.findViewById(R.id.Phone_edit_bottom);
                email_dialog = dialog_Edit.findViewById(R.id.address_edit_bottom);
                details_dialog = dialog_Edit.findViewById(R.id.Details_edit_bottom);
                date_dialog = dialog_Edit.findViewById(R.id.Date_edit_bottom);
                mail_dialog=dialog_Edit.findViewById(R.id.mail_edit_bottom);
                TextView salaryRange = dialog_Edit.findViewById(R.id.salary_edit_bottom);
                //////////////////////////////////////////////////////
                title_dialog.setText(title);
                subtitle_dialog.setText(subtitle);
                phone_dialog.setText(phone);
                email_dialog.setText(email);
                details_dialog.setText(details);
                date_dialog.setText(date);
                mail_dialog.setText(mail);
                salaryRange.setText(salary);

                ///////////////////////////////////////////////////////
                Button edit,delete;
                edit = dialog_Edit.findViewById(R.id.edit_Ad);
                delete = dialog_Edit.findViewById(R.id.delete_Ad);


                edit.setOnClickListener(v -> {
                    dialog_Edit.dismiss();


                        // custom dialog
                    final Dialog dialog_ed= new Dialog(admin.this);
                    dialog_ed.setContentView(R.layout.edit_ad);
                    EditText title_Edit,subtitle_edit,phone_edit,email_edit,details_edit,loc_Edit;
                    dialog_ed.show();
                    title_Edit=dialog_ed.findViewById(R.id.title_Edit);
                    subtitle_edit = dialog_ed.findViewById(R.id.subtitle_edit);
                    phone_edit=dialog_ed.findViewById(R.id.phone_edit);
                    email_edit = dialog_ed.findViewById(R.id.address_edit);
                    details_edit=dialog_ed.findViewById(R.id.details_edit);
                    loc_Edit=dialog_ed.findViewById(R.id.mail_edit);
                    EditText salaryEdit = dialog_ed.findViewById(R.id.salary_Edit);
                    Button finalUpdate = dialog_ed.findViewById(R.id.finalUpdate);
                    title_Edit.setText(title);
                    subtitle_edit.setText(subtitle);
                    phone_edit.setText(phone);
                    email_edit.setText(email);
                    loc_Edit.setText(email);
                    details_edit.setText(details);
                    salaryEdit.setText(salaryRange.getText().toString());
                    salaryEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Dialog dialog = new Dialog(admin.this);
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
                                    LayoutInflater inflater = getLayoutInflater();
                                    View layout = inflater.inflate(R.layout.warning_toast,
                                            (ViewGroup) findViewById(R.id.toast_layout_root));
                                    TextView text = layout.findViewById(R.id.text);
                                    Toast toast = new Toast(getApplicationContext());


                                    String salarymin=min.getText().toString();
                                    String salarymax = max.getText().toString();
                                    if(!salarymin.equals("") && !salarymax.equals("") && ( Integer.parseInt(salarymax)<= Integer.parseInt(salarymin) )){
                                        text.setText("Enter A valid Salary");

                                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setView(layout);
                                        toast.show();
                                    }else  if (salarymax.equals("")){
                                        text.setText("Fill up all fields please");
                                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setView(layout);
                                        toast.show();
                                    }else if (salarymin.equals("")){
                                        text.setText("Fill up all fields please");
                                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                                        toast.setDuration(Toast.LENGTH_LONG);
                                        toast.setView(layout);
                                        toast.show();
                                    }else {
                                        dialog.dismiss();
                                        salaryEdit.setText(salarymin+"$, up to "+salarymax+"$");

                                    }
                                }
                            });
                        }
                    });

                    String email_ = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
                    assert email_ != null;
                    String[] parts = email_.split("@");

                    String username = parts[0];
                    String output = username.substring(0, 1).toUpperCase() + username.substring(1);


                    finalUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String title1 = title_Edit.getText().toString();
                            String subtitle1 = subtitle_edit.getText().toString();
                            String phone1 = phone_edit.getText().toString();
                            String address1 = loc_Edit.getText().toString();
                            String details1 = details_edit.getText().toString();
                            String email1 = email_edit.getText().toString();
                            String wage = salaryEdit.getText().toString();


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
                                loc_Edit.setError("Fill this field");
                            } else if (details1.equals("")) {
                                details_edit.setError("Fill this field");
                            } else if (title1.length() < 6) {
                                title_Edit.setError("Title requires more than 6 Chars");
                            } else if (subtitle1.length() < 10) {
                                subtitle_edit.setError("Sub-Title requires more than 10 Chars");
                            } else if (phone1.length() < 6) {
                                phone_edit.setError("Phone requires more than 6 Chars");
                            } else if (address1.length() < 12) {
                                loc_Edit.setError("Location address requires more than 12 Chars");
                            } else if (email1.length() < 12) {
                                email_edit.setError("Email address requires more than 12 Chars");
                            } else if (details1.length() < 30) {
                                details_edit.setError("Deatails require more than 30 Chars");
                            } else if (isLowerCase(title1.charAt(0))) {
                                title_Edit.setError("Title should be Capitalized");
                            } else {

                                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                                DatabaseReference reference = rootNode.getReference("Advertisements").child(username).child(adId);

                                String ad_id = reference.push().getKey();

                                Ad ad = new Ad(title1, subtitle1, wage, phone1, email1, address1, details1, output, dateStr, ad_id);
                                reference.setValue(ad);
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_success,
                                        (ViewGroup) findViewById(R.id.toast_layout_root));
                                TextView text = layout.findViewById(R.id.text);
                                Toast toast = new Toast(getApplicationContext());
                                text.setText("Ad edited successfully");
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(layout);
                                toast.show();
                                dialog_ed.dismiss();
                            }
                            }

                        });






                    mail_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:"+mail));

                            startActivity(intent);
                        }
                    });
                    phone_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+phone));
                            startActivity(intent);
                        }
                    });
                });
                delete.setOnClickListener(v -> {
                   Dialog dialog = new Dialog(admin.this);
                   dialog.setContentView(R.layout.delete_draft);
                   dialog.show();
                   Button ok,cancel;
                   cancel = dialog.findViewById(R.id.canceldeletedraft);
                   ok = dialog.findViewById(R.id.okdeletedraft);
                   cancel.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                       }
                   });
                   ok.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           DatabaseReference ref = db.getReference
                                   ("Advertisements").child(username).child(adId);
                           ref.removeValue();
                           LayoutInflater inflater = getLayoutInflater();
                           View layout = inflater.inflate(R.layout.warning_toast,
                                   (ViewGroup) findViewById(R.id.toast_layout_root));
                           TextView text = layout.findViewById(R.id.text);
                           Toast toast = new Toast(getApplicationContext());
                           text.setText("Ad deleted successfully");
                           toast.setGravity(Gravity.BOTTOM, 0, 100);
                           toast.setDuration(Toast.LENGTH_SHORT);
                           toast.setView(layout);
                           toast.show();
                           dialog.dismiss();
                           dialog_Edit.dismiss();
                       }
                   });

                });
                dialog_Edit.show();
            }
        });

}

public void cickRateUs_Admin(View v)
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mauth;
    mauth=FirebaseAuth.getInstance();
    String email = mauth.getCurrentUser().getEmail();
    String []parts = email.split("@");
    String username = parts[0];
    DatabaseReference databaseReference = database.getReference("rate").child(username);
    BottomSheetDialog dialog = new BottomSheetDialog(admin.this);
    dialog.setContentView(R.layout.bottomdialog_rate);
    dialog.show();

    ImageView star1,star2,star3,star4,star5;
    star1 = dialog.findViewById(R.id.star1);
    star2=dialog.findViewById(R.id.star2);
    star3=dialog.findViewById(R.id.star3);
    star4=dialog.findViewById(R.id.star4);
    star5 = dialog.findViewById(R.id.star5);
    star1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            databaseReference.setValue("1");
            dialog.dismiss();
        }
    });
    star2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            databaseReference.setValue("2");
            dialog.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_success,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = layout.findViewById(R.id.text);
            Toast toast = new Toast(getApplicationContext());
            text.setText("Rated, Thank you!");
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    });
    star3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            databaseReference.setValue("3");
            dialog.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_success,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = layout.findViewById(R.id.text);
            Toast toast = new Toast(getApplicationContext());
            text.setText("Rated, Thank you!");
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    });
    star4.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            databaseReference.setValue("4");
            dialog.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_success,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = layout.findViewById(R.id.text);
            Toast toast = new Toast(getApplicationContext());
            text.setText("Rated, Thank you!");
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    });
    star5.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v1) {
            databaseReference.setValue("5");
            dialog.dismiss();
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_success,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = layout.findViewById(R.id.text);
            Toast toast = new Toast(getApplicationContext());
            text.setText("Rated, Thank you!");
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }
    });



}
    @Override
    protected void onStop() {
        super.onStop();
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);

    }
    public void openDrawer(DrawerLayout drawerLayout) {
        //open Drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public void closeDrawer(DrawerLayout drawerLayout) {
        //open Drawer
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public void ClickAccount(View v){
        startActivity(new Intent(admin.this, profile.class));
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void clickInbox(View v){
        startActivity(new Intent(admin.this,inbox.class));
    }






    public void ClickAboutUs(View v){
        startActivity(new Intent(admin.this,about.class));
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);

    }


    public void ClickLogOut(View v){
        Dialog dialog = new Dialog(admin.this);
        dialog.setContentView(R.layout.logout);
        TextView no = dialog.findViewById(R.id.cancellogout);
        TextView yes = dialog.findViewById(R.id.ok);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                FirebaseAuth.getInstance().signOut();
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.warning_toast,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("Logged out");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            startActivity(new Intent(admin.this,log_or_start.class));
            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
    }
    public void ClickContactUs(View v)
    {

        BottomSheetDialog dialog = new BottomSheetDialog(admin.this);

        dialog.setContentView(R.layout.bottomdialog);

        dialog.show();

    }

public void dialContact(View v){


    Intent intent = new Intent(Intent.ACTION_DIAL);
    intent.setData(Uri.parse("tel:0987654321"));
    startActivity(intent);
}

    public void mailContact(View v){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:developers@example.com"));

        startActivity(intent);

    }

    public void ClickMoreApps(View v){
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://play.google.com/store/apps"));
        startActivity(i);


    }

    public void ClickSaved(View v)
    {
        startActivity(new Intent(admin.this,bookmark.class));
}




    public void ClickDraft(MenuItem item)
    {
        startActivity(new Intent(admin.this,drafts.class));
        overridePendingTransition  (R.anim.right_slide_out,R.anim.right_slide_in);
    }

    public void Bookmark(MenuItem item)
    {
        startActivity(new Intent(admin.this,bookmark.class));
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }
    public void ClickAdd(MenuItem item)
    {
        startActivity(new Intent(admin.this,manage_ad.class));
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void ClickApproved(View view)
    {
        startActivity(new Intent(admin.this,approved.class));
        overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
    }
    public  void clickDraft(View view){
        startActivity(new Intent(getApplicationContext(),drafts.class));
    }
}
