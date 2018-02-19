package com.ahmetgokhan.unicity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createAdvertActivity extends AppCompatActivity {
    EditText advertName,description,university,name,surname;
    Button createAdvertButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);


        advertName = findViewById(R.id.advertName);
        description = findViewById(R.id.description);
        university = findViewById(R.id.university);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        createAdvertButton = findViewById(R.id.createAdvertButton);


        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface3 = ApiClient.getRetrofit().create(ApiInterface.class);
                String advertName_text = advertName.getText().toString().trim();
                String description_text = description.getText().toString().trim();
                String university_text = university.getText().toString().trim();
                String name_text = name.getText().toString().trim();


                Call<ArrayList<UniSocial>> call = apiInterface3.createAdvert(advertName_text,description_text,university_text,name_text);
                call.enqueue(new Callback<ArrayList<UniSocial>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

                    }
                });
            }
        });






    }






}

