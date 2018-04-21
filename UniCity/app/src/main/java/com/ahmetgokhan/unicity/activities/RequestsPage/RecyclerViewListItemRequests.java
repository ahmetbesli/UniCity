package com.ahmetgokhan.unicity.activities.RequestsPage;


import android.widget.ImageButton;

/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemRequests {

    private String requesterName;
    private String advertName;
    private String advert_id;
    private String requesterID;





    public RecyclerViewListItemRequests(String requesterName, String advertName,String advert_id,String requesterID) {
        this.requesterName = requesterName;
        this.advertName = advertName;
        this.advert_id = advert_id;
        this.requesterID = requesterID;

    }
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }




}
