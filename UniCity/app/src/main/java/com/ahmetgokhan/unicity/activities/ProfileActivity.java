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
import android.widget.ImageView;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView cover_photo;
    CircleImageView profile_photo;
    TextView name_surname,textViewUniversity;
    ApiInterface apiInterface;

    private String coverPhotoUrl;
    private String profilePhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textViewUniversity = findViewById(R.id.textViewUniversity);

        name_surname = findViewById(R.id.textViewName);
        cover_photo =  findViewById(R.id.cover_photo);
        profile_photo =  findViewById(R.id.circleImageView);

        cover_photo.setOnClickListener(this);
        profile_photo.setOnClickListener(this);




        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);





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
}


