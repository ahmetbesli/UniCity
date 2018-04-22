package com.ahmetgokhan.unicity.activities.RequestsPage;


/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemRequests {


    private String advertName;
    private String advert_id;
    private String requesterID;
    private String applyID;
    private String applierProfilePhoto;
    private String requesterName;





    public RecyclerViewListItemRequests(String requesterName, String advertName, String advert_id, String requesterID, String applyID, String applierProfilePhoto) {
        this.applyID = applyID;
        this.advert_id = advert_id;
        this.requesterID = requesterID;
        this.advertName = advertName;
        this.applierProfilePhoto = applierProfilePhoto;
        this.requesterName = requesterName;

    }

    public String getRequesterName() {
        return requesterName;
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

    public String getRequesterID() {
        return requesterID;
    }

    public void setRequesterID(String requesterID) {
        this.requesterID = requesterID;
    }

    public String getApplyID() {
        return applyID;
    }

    public void setApplyID(String applyID) {
        this.applyID = applyID;
    }

    public String getApplierProfilePhoto() {
        return applierProfilePhoto;
    }

    public void setApplierProfilePhoto(String applierProfilePhoto) {
        this.applierProfilePhoto = applierProfilePhoto;
    }






}
