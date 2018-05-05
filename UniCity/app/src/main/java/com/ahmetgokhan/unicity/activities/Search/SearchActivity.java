package com.ahmetgokhan.unicity.activities.Search;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RecyclerViewListItemSearch> listItems;
    private EditText nameSurnameEditText;
    private ImageView searchButton;
    private String nameSurname;
    private ImageView backToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        checkToken();
        nameSurnameEditText = findViewById(R.id.nameSurnameEditText);
        backToHome = findViewById(R.id.backToHomeButon);
        searchButton = findViewById(R.id.searchBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems =  new ArrayList<>();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),UsersProfileActivity.class);
                intent.putExtra("username",listItems.get(position).getUserNameHidden());
                intent.putExtra("name",listItems.get(position).getNameSurname());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.clear();
                nameSurname = nameSurnameEditText.getText().toString();
                loadRecyclerViewData();


            }
        });

        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }



    public void loadRecyclerViewData(){

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getProfileSearch(nameSurname);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    RecyclerViewListItemSearch listItem = new RecyclerViewListItemSearch(
                            response.body().get(i).getName() + " " + response.body().get(i).getSurname(),
                            response.body().get(i).getUniversity(),
                            response.body().get(i).getDepartment(),
                            response.body().get(i).getProfile_photo(),
                            response.body().get(i).getUsername()



                    );

                    System.out.println(response.body().get(i).getUniversity());

                    listItems.add(listItem);

                }

                adapter = new RecyclerViewMyAdapterSearch(listItems,getApplicationContext());
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
