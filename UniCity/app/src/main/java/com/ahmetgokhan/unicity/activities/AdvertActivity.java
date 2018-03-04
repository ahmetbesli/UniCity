package com.ahmetgokhan.unicity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertActivity extends AppCompatActivity {

    EditText advertName,description,university,name,numbOfPerson;
    Button createAdvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        advertName = findViewById(R.id.advertName);
        description = findViewById(R.id.description);
        university = findViewById(R.id.university);
        name = findViewById(R.id.name);
        createAdvertButton = findViewById(R.id.createAdvertButton);
        numbOfPerson = findViewById(R.id.numbOfPerson);


        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface3 = ApiClient.getRetrofit().create(ApiInterface.class);
                String advertName_text = advertName.getText().toString().trim();
                String description_text = description.getText().toString().trim();
                String university_text = university.getText().toString().trim();
                String name_text = name.getText().toString().trim();
                int numberOfPerson = 0;
                try {
                    numberOfPerson = Integer.parseInt(numbOfPerson.getText().toString());
                }catch (Exception e){};






                if(advertName_text.isEmpty()){

                    Toast.makeText(AdvertActivity.this, "Please enter a advert NAME", Toast.LENGTH_SHORT).show();

                }
                else if(description_text.isEmpty()){

                    Toast.makeText(AdvertActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();

                }

                else if(String.valueOf(numberOfPerson).isEmpty() || numberOfPerson == 0){

                    Toast.makeText(AdvertActivity.this, "Please enter number of person", Toast.LENGTH_SHORT).show();

                }


                else {


                    Call<ArrayList<UniSocial>> call = apiInterface3.createAdvert(advertName_text, description_text, university_text, name_text, numberOfPerson);
                    call.enqueue(new Callback<ArrayList<UniSocial>>() {
                        @Override
                        public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                           
                        }

                        @Override
                        public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

                        }
                    });
                }
            }
        });






    }






}

