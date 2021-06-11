package com.example.employee_app;

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

public class resume_list extends ArrayAdapter<Resume>
{
    private final Activity context;
    List<Resume> ads;


    public resume_list(Activity context, List<Resume> ads){
        super(context,R.layout.mylist_resumes,ads);
        this.context = context;
        this.ads=ads;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.mylist_resumes,null,true);
        TextView fullname = (TextView)listViewItem.findViewById(R.id.name_emp);
        TextView email = (TextView)listViewItem.findViewById(R.id.mail_emp);
        TextView phone_emp = (TextView)listViewItem.findViewById(R.id.phone_emp);
        TextView age = (TextView)listViewItem.findViewById(R.id.age_emp);
        TextView date = (TextView)listViewItem.findViewById(R.id.date_emp);

        Resume resume = ads.get(position);
        String to = "Ad by you: "+resume.getJobTitle();
        fullname.setText(resume.getFullname());
        email.setText(resume.getEmail());
        phone_emp.setText(resume.getPhone());
        age.setText(to);
        date.setText(resume.getDate());
        return listViewItem;

    }
}
