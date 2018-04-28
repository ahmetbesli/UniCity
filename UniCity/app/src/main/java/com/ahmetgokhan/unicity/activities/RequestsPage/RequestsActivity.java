package com.ahmetgokhan.unicity.activities.RequestsPage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
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
    private RecyclerViewListItemRequests recyclerViewListItemRequests;
    private String advertName,user_id,applierName,applierSurname,advertID,applyID;

    private ApiInterface apiInterface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        recyclerView = findViewById(R.id.recyclerViewRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems =  new ArrayList<>();
        loadRecyclerViewData();
        checkToken();

    }





    public void loadRecyclerViewData(){

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<ArrayList<UniSocial>>call = apiInterface.getApplies(getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN,""),"1");
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                for (int i = 0; i<response.body().size(); i++){

                    recyclerViewListItemRequests = new RecyclerViewListItemRequests(
                            response.body().get(i).getName() + response.body().get(i).getSurname() + "was applied your below advert",
                            response.body().get(i).getAdvertName(),
                            response.body().get(i).getAdvert_id(),
                            response.body().get(i).getUser_id(),
                            response.body().get(i).getApply_id(),
                            response.body().get(i).getProfile_photo()
                    );

                    listItems.add(recyclerViewListItemRequests);
                    System.out.println( listItems.get(i).getAdvertName());
                    System.out.println(  listItems.get(i).getRequesterName());


                }
                Log.e("SIZEEEE", String.valueOf(listItems.size()));

                adapter = new RecyclerViewMyAdapterRequests(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

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
