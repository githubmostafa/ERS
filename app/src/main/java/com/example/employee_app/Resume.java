package com.example.employee_app;

public class Resume {
   private String jobTitle,fullname,age,location,zip,
            school,schooDate,college,collegeDate,projects,
          phone,email,skills,about,job1,job2,jobdate1,jobdate2, date,resumeid;

    public Resume(String jobTitle, String fullname, String age, String location, String zip,
                  String school, String schooDate, String college, String collegeDate,
                  String projects, String phone, String email,
                  String skills, String about, String job1, String job2, String jobdate1,
                  String jobdate2, String date, String resumeid) {
        this.jobTitle = jobTitle;
        this.fullname = fullname;
        this.age = age;
        this.location = location;
        this.zip = zip;
        this.school = school;
        this.schooDate = schooDate;
        this.college = college;
        this.collegeDate = collegeDate;
        this.projects = projects;
        this.phone = phone;
        this.email = email;
        this.skills = skills;
        this.about = about;
        this.job1 = job1;
        this.job2 = job2;
        this.jobdate1 = jobdate1;
        this.jobdate2 = jobdate2;
        this.date = date;
        this.resumeid = resumeid;
    }

    public Resume() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchooDate() {
        return schooDate;
    }

    public void setSchooDate(String schooDate) {
        this.schooDate = schooDate;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCollegeDate() {
        return collegeDate;
    }

    public void setCollegeDate(String collegeDate) {
        this.collegeDate = collegeDate;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getJob1() {
        return job1;
    }

    public void setJob1(String job1) {
        this.job1 = job1;
    }

    public String getJob2() {
        return job2;
    }

    public void setJob2(String job2) {
        this.job2 = job2;
    }

    public String getJobdate1() {
        return jobdate1;
    }

    public void setJobdate1(String jobdate1) {
        this.jobdate1 = jobdate1;
    }

    public String getJobdate2() {
        return jobdate2;
    }

    public void setJobdate2(String jobdate2) {
        this.jobdate2 = jobdate2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResumeid() {
        return resumeid;
    }

    public void setResumeid(String resumeid) {
        this.resumeid = resumeid;
    }
}
