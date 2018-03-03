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

    TextView emailTextView, userProfileNameTextView;
    String emailText,userProfilNameText,token;
    SharedPreferences preferences;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.material_design_profile_screen_xml_ui_design);

        emailTextView = findViewById(R.id.emailTextView);
        userProfileNameTextView = findViewById(R.id.user_profile_name);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        token = preferences.getString("token", null);



        Call<UniSocial> call = apiInterface.getProfile(token);
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                emailText = response.body().getEmail();
                emailTextView.setText(emailText);
            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });


    }
}
