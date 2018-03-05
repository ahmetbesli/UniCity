package com.ahmetgokhan.unicity.overridden;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("faculty")
    @Expose
    public String faculty;



    public UniSocial(String message,
                     String name,
                     String surname,
                     String email,
                     String token,
                     String universities,
                     String advertName,
                     String description,
                     String cover_photo,
                     String profile_photo,
                     String faculty) {

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

}

