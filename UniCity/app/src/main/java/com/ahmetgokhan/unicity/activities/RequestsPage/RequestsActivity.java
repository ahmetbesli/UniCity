package com.ahmetgokhan.unicity.activities.RequestsPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Search.RecyclerItemClickListener;
import com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemSearch;
import com.ahmetgokhan.unicity.activities.Search.RecyclerViewMyAdapterSearch;
import com.ahmetgokhan.unicity.activities.Search.UsersProfileActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RecyclerViewListItemRequests> listItems;
    private String user_id;
    private ApiInterface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems =  new ArrayList<>();


        loadRecyclerViewData();

    }







    public void loadRecyclerViewData(){

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> call = apiInterface.getProfile(getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                user_id = response.body().getUser_id();

                Call<UniSocial> callAdverts = apiInterface.getAdvertsID(user_id);
                call.enqueue(new Callback<UniSocial>() {
                    @Override
                    public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                    }

                    @Override
                    public void onFailure(Call<UniSocial> call, Throwable t) {

                    }
                });





            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });


    }

}
