package com.ahmetgokhan.unicity.activities.Subscribe;


/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemSubscription {
    private String courseName;
    private String butonText;






    public RecyclerViewListItemSubscription(String courseName,String butonText) {

        this.courseName = courseName;
        this.butonText = butonText;

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getButonText() {
        return butonText;
    }

    public void setButonText(String butonText) {
        this.butonText = butonText;
    }



}
