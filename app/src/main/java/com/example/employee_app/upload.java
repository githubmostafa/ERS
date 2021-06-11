package com.example.employee_app;

public class upload {
    private  String mname;
    private String mImageUri;

    public upload() {
    }

    public upload(String mname, String mImageUri) {
        if (mname.trim().equals("")){
            mname = "Noname";
        }
        this.mname = mname;
        this.mImageUri = mImageUri;

    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }
}
