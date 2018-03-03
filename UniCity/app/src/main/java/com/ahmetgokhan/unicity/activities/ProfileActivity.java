package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView emailTextView, userProfileNameTextView,userProfileShortBioTextView;
    String emailText,userProfileNameText,token,userProfileShortBioText;
    SharedPreferences preferences;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);

        emailTextView = findViewById(R.id.emailTextView);
        userProfileNameTextView = findViewById(R.id.user_profile_name);
        userProfileShortBioTextView = findViewById(R.id.user_profile_short_bio);

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        token = preferences.getString("token", null);



        Call<ArrayList<UniSocial>> call = apiInterface.getProfile(token);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>>response) {
                for(int i = 0; i < response.body().size(); i++) {

                    System.out.println(response.body().size());
                    System.out.println(response.body().get(i).getEmail());
                    System.out.println(response.body().get(i).getName() + response.body().get(i).getSurname());
                    System.out.println(response.body().get(i).getUniversity());

                    userProfileNameText = response.body().get(i).getName() +" " + response.body().get(i).getSurname();
                    userProfileShortBioText = response.body().get(i).getUniversity();
                    emailText = response.body().get(i).getEmail();

                    userProfileNameTextView.setText(userProfileNameText);
                    userProfileShortBioTextView.setText(userProfileShortBioText);
                    emailTextView.setText(emailText);






                }
            }



            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

            }
        });


    }
}
