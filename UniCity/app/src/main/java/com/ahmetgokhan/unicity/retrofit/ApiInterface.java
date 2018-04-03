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
    @POST("getProfile/")
    Call<UniSocial> getProfile(@Field("token") String token);

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

            @Field("advertName") String advertName,
            @Field("description") String description,
            @Field("numberOfPerson") int numbOfPerson,
            @Field("courseName")  String courseName

    );

    @FormUrlEncoded
    @POST("login/")
    Call<UniSocial> login(@Field("username") String username, @Field("password") String password);


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
    @POST("updateUser/")
    Call<UniSocial> updateUser(@Field("department") String department,
                               @Field("token") String token);


}

