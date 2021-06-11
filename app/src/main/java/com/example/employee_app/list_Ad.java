package com.example.employee_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class list_Ad extends ArrayAdapter<Ad>
{
    private final Activity context;
    List<Ad> ads;


    public list_Ad(Activity context, List<Ad> ads){
        super(context,R.layout.mylist,ads);
        this.context = context;
        this.ads=ads;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.mylist,null,true);
        TextView tv_title = (TextView)listViewItem.findViewById(R.id.userInfo);
        TextView address = (TextView)listViewItem.findViewById(R.id.address_list);
        TextView phone = (TextView)listViewItem.findViewById(R.id.phone_list);
        TextView date = (TextView)listViewItem.findViewById(R.id.date_list);
        TextView username = (TextView)listViewItem.findViewById(R.id.username_list);
        ImageView profile = listViewItem.findViewById(R.id.profile);

        Ad ad = ads.get(position);
        tv_title.setText(ad.getTitle());
        username.setText(ad.getUsername());
        phone.setText(ad.getPhone());
        address.setText(ad.getAddress());
        date.setText(ad.getTimestamp());






        return listViewItem;

    }
}
