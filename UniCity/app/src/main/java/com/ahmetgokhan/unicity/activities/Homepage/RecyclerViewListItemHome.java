package com.ahmetgokhan.unicity.activities.Homepage;




/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemHome {
    private String courseName;
    private String advertName;
    private String description;
    private int numberOfPerson;
    private String advertDate;
    private String advert_id;




    public RecyclerViewListItemHome(String courseName, String advertName, String description, int numberOfPerson, String advertDate, String advert_id) {
        this.courseName = courseName;
        this.advertName = advertName;
        this.description = description;
        this.numberOfPerson = numberOfPerson;
        this.advertDate = advertDate;
        this.advert_id = advert_id;

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

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

}
