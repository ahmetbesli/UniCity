package com.ahmetgokhan.unicity.retrofit;

import com.ahmetgokhan.unicity.overridden.UniSocial;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {



    @FormUrlEncoded
    @POST("checkToken/")
    Call<UniSocial> checkToken(@Field("token") String token);

    @FormUrlEncoded
    @POST("getProfile/")
    Call<UniSocial> getProfile(@Field("token") String token);

    @FormUrlEncoded
    @POST("isSubscribed/")
    Call<ArrayList<UniSocial>> isSubscribed(@Field("token") String token);

    @FormUrlEncoded
    @POST("subscribe/")
    Call<UniSocial> subscribe(@Field("token") String token, @Field("course_name") String course_name);

    @FormUrlEncoded
    @POST("unsubscribe/")
    Call<UniSocial> unsubscribe(@Field("token") String token, @Field("course_name") String course_name);

    @FormUrlEncoded
    @POST("getCourses/")
    Call<ArrayList<UniSocial>> getCourses(@Field("universityName") String university,
                                          @Field("facultyName") String facultyName,
                                          @Field("departmentName") String departmentName
    );

    @FormUrlEncoded
    @POST("createAdvert/")
    Call<UniSocial> createAdvert(
            @Field("token") String token,
            @Field("advertName") String advertName,
            @Field("description") String description,
            @Field("numberOfPerson") int numbOfPerson,
            @Field("courseName")  String courseName

    );

    @FormUrlEncoded
    @POST("login/")
    Call<UniSocial> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("getAdvertsID/")
    Call<UniSocial> getAdvertsID(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("saveToken/")
    Call<UniSocial> saveToken(@Field("firebaseToken") String firebaseToken, @Field("token") String token);

    @FormUrlEncoded
    @POST("replyApplies/")
    Call<UniSocial> replyApplies(@Field("reply") String reply, @Field("applyID") String applyID);



    @FormUrlEncoded
    @POST("register/")
    Call<UniSocial> register(@Field("name") String name,
                             @Field("surname") String surname,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("university") String university);




    @GET("getUniversities/")
    Call<ArrayList<UniSocial>> getUniversities();

    @GET("getAdverts/")
    Call<ArrayList<UniSocial>> getAdverts();


    @FormUrlEncoded
    @POST("getFaculty/")
    Call<ArrayList<UniSocial>> getFaculty(@Field("university") String university);

    @FormUrlEncoded
    @POST("getApplies/")
    Call<ArrayList<UniSocial>> getApplies(@Field("token") String token,@Field("situation") String situation);

    @FormUrlEncoded
    @POST("updateUser/")
    Call<UniSocial> updateUser(@Field("department") String department,
                               @Field("token") String token);
    @FormUrlEncoded
    @POST("getHomepageAdverts/")
    Call<ArrayList<UniSocial>> getHomepageAdverts(@Field("token") String token);

    @FormUrlEncoded
    @POST("getProfileSearch/")
    Call<ArrayList<UniSocial>> getProfileSearch(@Field("nameSurname") String nameSurname);

    @FormUrlEncoded
    @POST("getUsersProfile/")
    Call<UniSocial> getUsersProfile(@Field("username") String username);

    @FormUrlEncoded
    @POST("getAdvertApply/")
    Call<UniSocial> getAdvertApply(@Field("token") String token, @Field("advert_id") String advert_id);

    @FormUrlEncoded
    @POST("getProjectsForProfile/")
    Call<ArrayList<UniSocial>> getProjectsList(@Field("token") String token);

}

