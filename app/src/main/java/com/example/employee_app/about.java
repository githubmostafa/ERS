package com.example.employee_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class about extends AppCompatActivity {
    int studentCounter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        TextView rated =findViewById(R.id.ratedText);
         studentCounter = 0;
        List<String> rates=new ArrayList<>();



        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("rate");


        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                studentCounter = studentCounter + 1;

                String strCounter = String.valueOf(studentCounter);

                //Showing the user counter in the textview



               mDatabaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       double sum = 0;
                       for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                           rates.clear();
                           String rate = dataSnapshot.getValue(String.class);
                           rates.add(rate);

                           for (int i = 0; i < rates.size(); i++) {
                               sum += Double.parseDouble(rates.get(i));




                                /*  double avg = sum/Double.parseDouble(strCounter);
                               */
                           }

                       }
                       DecimalFormat df = new DecimalFormat("#.#");
                       Double tot = Double.parseDouble(df.format(sum));
                       Double total = (tot) / (Double.parseDouble(strCounter));
                       TextView review = findViewById(R.id.reviews);
                       if (Integer.parseInt(strCounter) !=1){
                           rated.setText("Our Rate: "+" "+ total);
                           review.setText("("+strCounter+" reviews)");

                       }else {
                           rated.setText("Our Rate: " + " " + total);
                           review.setText("("+strCounter+" review)");
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });





            }



            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






            }
    public  void aboutBack(View view){
        startActivity(new Intent(about.this,admin.class));

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
    }
