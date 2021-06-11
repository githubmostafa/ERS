+package com.example.employee_app;


import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class emp_inter extends AppCompatActivity {
    DrawerLayout drawerLayout;
    FirebaseDatabase db;
    List<Ad> advertisements;
    ListView listViewAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_inter);
        drawerLayout = findViewById(R.id.drawer_layout_emp);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        advertisements = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        listViewAds = findViewById(R.id.listView1);

        DatabaseReference itemsRef = db.getReference().child("Advertisements");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    Log.d("TAG", name);
                    assert name != null;
                    DatabaseReference ref_ads = itemsRef.child(name);
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
                            list_Ad adAdapter = new list_Ad(emp_inter.this,advertisements);
                            listViewAds.setAdapter(adAdapter);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Don't ignore potential errors!
            }
        };
        itemsRef.addListenerForSingleValueEvent(eventListener);
        listViewAds.setOnItemClickListener((parent, view, position, id) -> {
            Ad ad = advertisements.get(position);

            String title,subtitle,phone,email,details,date,mail;
            title = ad.getTitle();
            subtitle = ad.getSubtitle();
            phone=ad.getPhone();
            email=ad.getAddress();
            details=ad.getDetails();
            date = ad.getTimestamp();
            mail=ad.getEmail();


            BottomSheetDialog dialog_Apply = new BottomSheetDialog(emp_inter.this);

            dialog_Apply.setContentView(R.layout.apply_dialog);
            TextView title_dialog,subtitle_dialog,phone_dialog,email_dialog,details_dialog,date_dialog,mail_dialog;
            ///////////////////////////////////////////////////////
            title_dialog = dialog_Apply.findViewById(R.id.jobTitle_apply);
            subtitle_dialog = dialog_Apply.findViewById(R.id.jobSubtitle_apply);
            phone_dialog=dialog_Apply.findViewById(R.id.jobPhone_apply);
            email_dialog = dialog_Apply.findViewById(R.id.jobaddress_apply);
            details_dialog = dialog_Apply.findViewById(R.id.jobDetails_apply);
            date_dialog = dialog_Apply.findViewById(R.id.jobDate_apply);
            mail_dialog=dialog_Apply.findViewById(R.id.jobMail_apply);
            //////////////////////////////////////////////////////
            assert title_dialog != null;
            title_dialog.setText(title);
            assert subtitle_dialog != null;
            subtitle_dialog.setText(subtitle);
            assert phone_dialog != null;
            phone_dialog.setText(phone);
            assert email_dialog != null;
            email_dialog.setText(email);
            assert details_dialog != null;
            details_dialog.setText(details);
            assert date_dialog != null;
            date_dialog.setText(date);
            assert mail_dialog != null;
            mail_dialog.setText(mail);
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
            ///////////////////////////////////////////////////////
            Button apply;
            apply = dialog_Apply.findViewById(R.id.apply);


            assert apply != null;
            apply.setOnClickListener(v -> {
                Intent Apply = new Intent(emp_inter.this,apply.class);
                Apply.putExtra("jobTitle",title);
                Apply.putExtra("username",ad.getUsername());
                dialog_Apply.dismiss();
                startActivity(Apply);


            });



            dialog_Apply.show();




        });






    }


    @Override
    public void onBackPressed() {

    }

    public void ClickMenu(View view) {
        openDrawer(drawerLayout);

    }

    public void openDrawer(DrawerLayout drawerLayout) {
        //open Drawer
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(drawerLayout);
    }

    public void closeDrawer(DrawerLayout drawerLayout) {
        //open Drawer
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }



   /* public void ClickSearch(View v) {
        startActivity(new Intent(emp_inter.this, search.class));
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    /*public void ClickHome(View v) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }*/

 /*   public void ClickBookmark(View v) {
        startActivity(new Intent(emp_inter.this, bookmark_emp.class));
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

    }*/


    public void ClickAboutUs_emp(View v) {
        startActivity(new Intent(emp_inter.this, about_emp.class));

    }


    public void ClickExit(View v) {

        Dialog dialog = new Dialog(emp_inter.this);
        dialog.setContentView(R.layout.exit);
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
                text.setText("Exited");
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
                startActivity(new Intent(emp_inter.this,log_or_start.class));
            }
        });
        dialog.show();

    }

    public void ClickContactus(View v) {

        BottomSheetDialog dialog = new BottomSheetDialog(emp_inter.this);

        dialog.setContentView(R.layout.bottomdialog);

        dialog.show();

    }

    public void dialContact(View v) {


        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0987654321"));
        startActivity(intent);
    }

    public void mailContact(View v) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:developers@example.com"));

        startActivity(intent);

    }


    public void ClickRateUs(View v)
    {
        BottomSheetDialog dialog = new BottomSheetDialog(emp_inter.this);

        dialog.setContentView(R.layout.bottomdialog_rate);

        dialog.show();



    }







    public void ClickMoreApps(View v) {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://play.google.com/store/apps"));
        startActivity(i);


    }
    public void ClickSettings(View v){

        startActivity(new Intent(emp_inter.this,settings.class));
    }




}