package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

public class AdvertActivityStepOne extends AppCompatActivity {

    EditText advertName,description,university,name,numbOfPerson;
    Button createAdvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        advertName = findViewById(R.id.advertName);
        description = findViewById(R.id.description);
        createAdvertButton = findViewById(R.id.createAdvertButton);
        numbOfPerson = findViewById(R.id.numbOfPerson);


        createAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiInterface apiInterface3 = ApiClient.getRetrofit().create(ApiInterface.class);

                String advertName_text = advertName.getText().toString().trim();
                String description_text = description.getText().toString().trim();
                int numberOfPerson = 0;
                try {
                    numberOfPerson = Integer.parseInt(numbOfPerson.getText().toString());
                }catch (Exception e){};






                if(advertName_text.isEmpty()){

                    Toast.makeText(AdvertActivityStepOne.this, "Please enter a advert NAME", Toast.LENGTH_SHORT).show();

                }
                else if(description_text.isEmpty()){

                    Toast.makeText(AdvertActivityStepOne.this, "Please enter a description", Toast.LENGTH_SHORT).show();

                }

                else if(String.valueOf(numberOfPerson).isEmpty() || numberOfPerson == 0){

                    Toast.makeText(AdvertActivityStepOne.this, "Please enter number of person", Toast.LENGTH_SHORT).show();

                }


                else {
                        Intent intent = new Intent(getApplicationContext(), AdvertActivityStepTwo.class);
                        intent.putExtra("advertName", advertName_text);
                        intent.putExtra("description", description_text);
                        intent.putExtra("numberOfPerson", numberOfPerson);
                        startActivity(intent);

                }
            }
        });






    }






}

