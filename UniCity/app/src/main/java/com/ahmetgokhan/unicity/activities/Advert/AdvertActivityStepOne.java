package com.ahmetgokhan.unicity.activities.Advert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class AdvertActivityStepOne extends AppCompatActivity {

    EditText advertName,description,university,name,numbOfPerson;
    Button createAdvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);
        checkToken();
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




    public void checkToken(){
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> callToken = apiInterface.checkToken(getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).getString(Config.TOKEN,""));
        callToken.enqueue(new Callback<UniSocial>() {

            @Override
            public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {
                if(response.body().getMessage().equals("true")){
                    getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).edit().putBoolean(Config.LOGGING_STATUS,false).apply();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Please login again. Token timeout!",Toast.LENGTH_LONG).show();

                }else{

                    System.out.println("Error");
                }
            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }

        });
    }

}

