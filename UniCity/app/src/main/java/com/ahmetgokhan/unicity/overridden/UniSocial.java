package com.ahmetgokhan.unicity.overridden;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UniSocial {

    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("universities")
    @Expose
    public String universities;

    @SerializedName("university")
    @Expose
    public String university;

    @SerializedName("advertName")
    @Expose
    private String advertName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("numberOfPerson")
    @Expose
    public int numberOfPerson;


    @SerializedName("courseName")
    @Expose
    public String courseName;


    @SerializedName("advert_date")
    @Expose
    public Date advertDate;


    @SerializedName("surname")
    @Expose
    public String surname;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("cover_photo")
    @Expose
    public String cover_photo;

    @SerializedName("profile_photo")
    @Expose
    public String profile_photo;

    @SerializedName("faculties")
    @Expose
    public String faculty;

    @SerializedName("departments")
    @Expose
    public String departments;

    @SerializedName("courses")
    @Expose
    public String courses;

    @SerializedName("department")
    @Expose
    public String department;

    @SerializedName("number_subs")
    @Expose
    public String number_subs;

    @SerializedName("number_adverts")
    @Expose
    public String number_adverts;



    public UniSocial(String message,
                     String name,
                     String surname,
                     String email,
                     String token,
                     String department,
                     String universities,
                     String advertName,
                     String description,
                     String cover_photo,
                     String profile_photo,
                     String faculty,
                     String number_adverts,
                     String number_subs) {

        this.faculty = faculty;
        this.cover_photo = cover_photo;
        this.profile_photo = profile_photo;
        this.message = message;
        this.universities = universities;
        this.name = name;
        this.token = token;
        this.advertName = advertName;
        this.description = description;
        this.surname = surname;
        this.email = email;
        this.department = department;
        this.number_adverts = number_adverts;
        this.number_subs = number_subs;


    }


    public String getNumber_subs() {
        return number_subs;
    }

    public void setNumber_subs(String number_subs) {
        this.number_subs = number_subs;
    }

    public String getNumber_adverts() {
        return number_adverts;
    }

    public void setNumber_adverts(String number_adverts) {
        this.number_adverts = number_adverts;
    }


    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getUniversities() {
        return universities;
    }

    public void setUniversities(String universities) {
        this.universities = universities;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) { this.token = token; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAdvertName() { return advertName; }

    public void setAdvertName(String advertName) { this.advertName = advertName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }


    public String getSurname() { return surname; }

    public void setSurname(String surname) {this.surname = surname;}

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getUniversity() { return university; }

    public void setUniversity(String university) { this.university = university; }

    public String getCourses() { return courses; }

    public void setCourses(String courses) { this.courses = courses; }

    public String getDepartments() { return departments; }

    public void setDepartments(String departments) { this.departments = departments; }

    public int getNumberOfPerson() { return numberOfPerson; }

    public void setNumberOfPerson(int numberOfPerson) { this.numberOfPerson = numberOfPerson; }

    public Date getAdvertDate() { return advertDate; }

    public void setAdvertDate(Date advertDate) { this.advertDate = advertDate; }

    public String getCourseName() { return courseName; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getDepartment() { return department; }

    public void setDepartment(String department) { this.department = department; }



}

