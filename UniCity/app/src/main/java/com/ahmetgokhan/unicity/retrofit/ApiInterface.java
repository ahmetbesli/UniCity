package com.ahmetgokhan.unicity.retrofit;

import com.ahmetgokhan.unicity.overridden.UniSocial;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login/")
    Call<UniSocial> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("getFaculty/")
    Call<ArrayList<UniSocial>> getFaculty(@Field("university") String university);

    @GET("getUniversities/")
    Call<ArrayList<UniSocial>> getUniversities();


}