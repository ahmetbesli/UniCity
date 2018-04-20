package com.ahmetgokhan.unicity.activities.Profile;


import java.util.Date;

/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemProfile {
    private String courseName;
    private String advertName;
    private String description;
    private int numberOfPerson;
    private String advertDate;




    public RecyclerViewListItemProfile(String courseName, String advertName, String description, int numberOfPerson, String advertDate) {
        this.courseName = courseName;
        this.advertName = advertName;
        this.description = description;
        this.numberOfPerson = numberOfPerson;
        this.advertDate = advertDate;
    }
    public String getCourseName() {  return courseName; }


    public String getAdvertName() {
        return advertName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public String getAdvertDate() {
        return advertDate;
    }



}
