package com.ahmetgokhan.unicity.activities.ProfileLists;

public class ProjectsListTypeData {
    String advert_id;
    String advertName;

    public ProjectsListTypeData(String advert_id, String advertName) {
        this.advert_id = advert_id;
        this.advertName = advertName;
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
