package com.ahmetgokhan.unicity.activities.Search;


/**
 * Created by gokhankilic on 9.03.2018.
 */

public class RecyclerViewListItemSearch {
    private String nameSurname;
    private String uniName;
    private String departmentName;
    private String profilePhotoUrl;
    private String userNameHidden;




    public RecyclerViewListItemSearch(String nameSurname, String uniName, String departmentName,  String profilePhotoUrl, String userNameHidden) {
        this.nameSurname = nameSurname;
        this.uniName = uniName;
        this.departmentName = departmentName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.userNameHidden = userNameHidden;
    }
    public String getNameSurname() {  return nameSurname; }

    public String getUniName() {
        return uniName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public String getUserNameHidden() { return userNameHidden; }
}
