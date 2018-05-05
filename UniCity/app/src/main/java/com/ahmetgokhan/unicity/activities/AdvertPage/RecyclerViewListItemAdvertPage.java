package com.ahmetgokhan.unicity.activities.AdvertPage;


/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemAdvertPage {


    private String workerName;
    private String workerID;
    private String advert_id;
    private String creatorID;
    private String workerProfilePhoto;
    private String butonText;










    public RecyclerViewListItemAdvertPage(String workerName, String workerID, String advert_id,String creatorID,String workerProfilePhoto,String butonText) {
        this.workerID = workerID;
        this.advert_id = advert_id;
        this.workerName = workerName;
        this.creatorID = creatorID;
        this.workerProfilePhoto = workerProfilePhoto;
        this.butonText = butonText;



    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerID() {
        return workerID;
    }

    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getWorkerProfilePhoto() {
        return workerProfilePhoto;
    }

    public void setWorkerProfilePhoto(String workerProfilePhoto) {
        this.workerProfilePhoto = workerProfilePhoto;
    }

    public String getButonText() {
        return butonText;
    }








}
