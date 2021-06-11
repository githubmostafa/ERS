package com.example.employee_app;

import android.app.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class draft_list extends ArrayAdapter<Ad> {
    private final Activity context;
    List<Ad> drafts;

    public draft_list(Activity context, List<Ad> drafts) {
        super(context, R.layout.mylist_drafts, drafts);
        this.context = context;
        this.drafts = drafts;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.mylist_drafts,null,true);
        TextView draft_title = (TextView)listViewItem.findViewById(R.id.userInfo_draft);
        TextView draft_address = (TextView)listViewItem.findViewById(R.id.address_list_draft);
        TextView draft_phone = (TextView)listViewItem.findViewById(R.id.phone_list_draft);
        TextView draft_date = (TextView)listViewItem.findViewById(R.id.date_list_draft);
        TextView draft_username = (TextView)listViewItem.findViewById(R.id.username_list_draft);

        Ad draft = drafts.get(position);
        draft_title.setText(draft.getTitle());
        draft_username.setText(draft.getUsername());
        draft_phone.setText(draft.getPhone());
        draft_address.setText(draft.getAddress());
        draft_date.setText(draft.getTimestamp());
        return listViewItem;

    }

}