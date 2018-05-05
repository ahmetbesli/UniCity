package com.ahmetgokhan.unicity.activities.Profile;

public class RecyclerViewListItemCreated {

    private String advert_id;
    private String advertName;
    private String Description;
    private String NumberOfPerson;
    private String Date;
    private String CourseName;


    public RecyclerViewListItemCreated(String advert_id, String advertName, String description, String numberOfPerson, String date, String courseName) {
        this.advert_id = advert_id;
        this.advertName = advertName;
        Description = description;
        NumberOfPerson = numberOfPerson;
        Date = date;
        CourseName = courseName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNumberOfPerson() {
        return NumberOfPerson;
    }

    public void setNumberOfPerson(String numberOfPerson) {
        NumberOfPerson = numberOfPerson;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }
}
