package com.ahmetgokhan.unicity.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView cover_photo;
    CircleImageView profile_photo;
    TextView name_surname,textViewUniversity;
    ApiInterface apiInterface;
    Button subscribeButton,homeButton;

    private String coverPhotoUrl;
    private String profilePhotoUrl;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RecyclerViewListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewUniversity = findViewById(R.id.textViewUniversity);

        subscribeButton = findViewById(R.id.subscribeButton);
        homeButton = findViewById(R.id.homeButton);

        name_surname = findViewById(R.id.textViewName);
        cover_photo =  findViewById(R.id.cover_photo);
        profile_photo =  findViewById(R.id.circleImageView);

        subscribeButton.setOnClickListener(this);
        cover_photo.setOnClickListener(this);
        profile_photo.setOnClickListener(this);




        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems =  new ArrayList<>();
        loadRecyclerViewData();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });


        Call<UniSocial> call = apiInterface.getProfile(getSharedPreferences(Config.APP_NAME, Context.MODE_PRIVATE).getString(Config.TOKEN,""));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial>response) {
                    name_surname.setText(response.body().getName() + " " + response.body().getSurname());
                    textViewUniversity.setText(response.body().getUniversity());

                    coverPhotoUrl = Config.BASE_URL + response.body().getCover_photo();
                    profilePhotoUrl = Config.BASE_URL + response.body().getProfile_photo();

                    AsyncTask<String, Void, Bitmap> coverTask = new BitmapTask().execute(response.body().getCover_photo());
                    AsyncTask<String, Void, Bitmap> profileTask = new BitmapTask().execute(response.body().getProfile_photo());


                try {
                    cover_photo.setImageBitmap(coverTask.get());
                    profile_photo.setImageBitmap(profileTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }



            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_photo:
                Intent fullScreenIntent = new Intent(this, FullScreenImageActivity.class);
                if (coverPhotoUrl != null) {
                    fullScreenIntent.setData(Uri.parse(coverPhotoUrl));
                    startActivity(fullScreenIntent);
                }
                break;
            case R.id.circleImageView:
                Intent fullScreenIntent2 = new Intent(this, FullScreenImageActivity.class);
                if (profilePhotoUrl != null) {
                    fullScreenIntent2.setData(Uri.parse(profilePhotoUrl));
                    startActivity(fullScreenIntent2);
                }
                break;
            case R.id.subscribeButton:
                Intent intent = new Intent(this,SubscribeActivity.class);
                startActivity(intent);
            default:
                break;

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class BitmapTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream(new URL(Config.BASE_URL + strings[0]).openStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void loadRecyclerViewData(){

        Call<ArrayList<UniSocial>>call = apiInterface.getAdverts();
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    RecyclerViewListItem listItem = new RecyclerViewListItem(
                        response.body().get(i).getAdvertName(),
                        response.body().get(i).getDescription(),
                        response.body().get(i).getNumberOfPerson(),
                        response.body().get(i).getAdvertDate()
                );

                    listItems.add(listItem);

                }

                adapter = new RecyclerViewMyAdapter(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

            }
        });



    }
}


