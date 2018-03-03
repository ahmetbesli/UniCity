package com.ahmetgokhan.unicity.retrofit;

import com.ahmetgokhan.unicity.overridden.UniSocial;

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
    @POST("getProfile/")
    Call<ArrayList<UniSocial>> getProfile(@Field("token") String token);

    @FormUrlEncoded
    @POST("login/")
    Call<UniSocial> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("getFaculty/")
    Call<ArrayList<UniSocial>> getFaculty(@Field("university") String university);

    @FormUrlEncoded
    @POST("register/")
    Call<UniSocial> register(@Field("name") String name,
                             @Field("surname") String surname,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("university") String university);

    @FormUrlEncoded
    @POST("createAdvert/")
    Call<ArrayList<UniSocial>> createAdvert(

            @Field("advertName") String advertName,
            @Field("description") String description,
            @Field("university") String university,
            @Field("name") String name,
            @Field("numbOfPerson") int numbOfPerson

    );


    @GET("getUniversities/")
    Call<ArrayList<UniSocial>> getUniversities();

    @FormUrlEncoded
    @POST("getDepartment/")
    Call<ArrayList<UniSocial>> getDeparment(@Field("faculty") String faculty, @Field("university") String university);



}

