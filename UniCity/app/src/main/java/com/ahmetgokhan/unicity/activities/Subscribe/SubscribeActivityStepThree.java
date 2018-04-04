package com.ahmetgokhan.unicity.activities.Subscribe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Profile.ProfileActivity;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapterWithButton;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubscribeActivityStepThree extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ListView listView;
    String department;
    ImageButton profileComplateButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_step_three);
        department = getIntent().getStringExtra("department");

        listView = findViewById(R.id.listViewSubscription3);
        //profileComplateButton = findViewById(R.id.profileComplateButton);

        /*profileComplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });*/
        generateListContent();


        System.out.println(department);
        System.out.println("TOKEEEN" + getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> call = apiInterface.updateUser(department, getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView text = (TextView)parent.getChildAt(position).findViewById(R.id.listViewText);
               Log.e("text bastıkkkkkk", text.getText().toString());
            }
        });




    }
    private void generateListContent() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses(null,null,department);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                Log.e("hello",String.valueOf(response.body().size()));
                for (int i = 0; i < response.body().size(); i++) {

                    data.add(response.body().get(i).getCourses());

                }
                listView.setAdapter(new SubscribeAdapterWithButton(getApplicationContext(),R.layout.list_item_with_button,data));

            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
