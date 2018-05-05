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
    @POST("getDoneProjectsForProfileUsers/")
    Call<ArrayList<UniSocial>> getDoneProjectsForProfileUsers(@Field("username") String username);

    @FormUrlEncoded
    @POST("getCreatedProjectsForProfileUsers/")
    Call<ArrayList<UniSocial>> getCreatedProjectsForProfileUsers(@Field("username") String username);

    @FormUrlEncoded
    @POST("getWorkingProjectsForProfileUsers/")
    Call<ArrayList<UniSocial>> getWorkingProjectsForProfileUsers(@Field("username") String username);

    @FormUrlEncoded
    @POST("getMessageList/")
    Call<ArrayList<UniSocial>> getMessageList(@Field("token") String token);

    @FormUrlEncoded
    @POST("getDoneProjectsForProfile/")
    Call<ArrayList<UniSocial>> getDoneProjects(@Field("token") String token);

    @FormUrlEncoded
    @POST("getCreatedProjectsForProfile/")
    Call<ArrayList<UniSocial>> getCreatedProjects(@Field("token") String token);

    @FormUrlEncoded
    @POST("updateProfile/")
    Call<UniSocial> updateProfile(@Field("name") String name,@Field("surname") String surname, @Field("department") String department, @Field("profile_photo") String profile_photo, @Field("cover_photo") String cover_photo,@Field("token") String token);

    @FormUrlEncoded
    @POST("checkToken/")
    Call<UniSocial> checkToken(@Field("token") String token);

    @FormUrlEncoded
    @POST("createRoom/")
    Call<UniSocial> createRoom(@Field("to_username") String username,@Field("token") String token, @Field("name") String name);

    @FormUrlEncoded
    @POST("roomExits/")
    Call<UniSocial> roomExits(@Field("to_username") String username,@Field("token") String token);

    @FormUrlEncoded
    @POST("getMessages/")
    Call<ArrayList<UniSocial>> getConversations(@Field("thread_id") String thread_id);

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
    Call<UniSocial> replyApplies(@Field("reply") String reply, @Field("applyID") String applyID,@Field("advert_id") String advert_id);

    @FormUrlEncoded
    @POST("checkAdvert/")
    Call<UniSocial> checkAdvert(@Field("advert_id") String advert_id);




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
    @POST("getAdvertPage/")
    Call<UniSocial> getAdvertPage(@Field("advert_id") String advert_id);

    @FormUrlEncoded
    @POST("getWorkingUsers/")
    Call<ArrayList<UniSocial>> getWorkingUsers(@Field("advert_id") String advert_id);

    @FormUrlEncoded
    @POST("getApplies/")
    Call<ArrayList<UniSocial>> getApplies(@Field("token") String token,@Field("situation") String situation);

    @FormUrlEncoded
    @POST("unApply/")
    Call<UniSocial> unApply(@Field("token") String token, @Field("advert_id") String advert_id);

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

    @FormUrlEncoded
    @POST("getProfileFromUserID/")
    Call<UniSocial> getProfileFromUserID(@Field("user_id") String user_id);


    @FormUrlEncoded
    @POST("removeWorker/")
    Call<UniSocial> removeWorker(@Field("advert_id") String advert_id,@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("updateAdverts/")
    Call<UniSocial> updateAdverts(@Field("reply") String reply,@Field("advert_id") String advert_id);

}

