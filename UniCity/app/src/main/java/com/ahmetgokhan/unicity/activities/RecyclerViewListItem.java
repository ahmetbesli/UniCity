package com.ahmetgokhan.unicity.activities;


import java.util.Date;

/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItem {
    private String advertName;
    private String description;
    private int numberOfPerson;
    private Date advertDate;


    public RecyclerViewListItem(String advertName, String description, int numberOfPerson, Date advertDate) {
        this.advertName = advertName;
        this.description = description;
        this.numberOfPerson = numberOfPerson;
        this.advertDate = advertDate;
    }

    public String getAdvertName() {
        return advertName;
    }

    public String getDescription() {
        return description;
    }

    public int getNumberOfPerson() {
        return numberOfPerson;
    }

    public Date getAdvertDate() {
        return advertDate;
    }



}
