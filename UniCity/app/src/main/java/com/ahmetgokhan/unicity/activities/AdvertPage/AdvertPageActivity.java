package com.ahmetgokhan.unicity.activities.AdvertPage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;

import com.ahmetgokhan.unicity.activities.RequestsPage.RecyclerViewListItemRequests;
import com.ahmetgokhan.unicity.activities.RequestsPage.RecyclerViewMyAdapterRequests;
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

public class AdvertPageActivity extends AppCompatActivity {

    private TextView advertTitle;
    private TextView discription;
    private TextView numberOfPerson;
    private TextView nameCreator;
    CircleImageView profilePhoto;
    private TextView whoisworking;
    private Button applyButton;
    private ImageButton options;
    private String advertID;
    private  ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageButton deleteWorkerButton;
    RelativeLayout applyLayout;

    private List<RecyclerViewListItemAdvertPage> listItems;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_page);



        intent = getIntent();
        advertTitle = (TextView)findViewById(R.id.titleAdvert);
        discription = findViewById(R.id.description);
        applyLayout = findViewById(R.id.applyLayout);
        applyButton = findViewById(R.id.applyButton);
        options = findViewById(R.id.options);
        nameCreator = findViewById(R.id.nameCreator);
        profilePhoto = findViewById(R.id.profilePhoto);
        whoisworking = findViewById(R.id.whoisworking);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d("USER ID ",intent.getStringExtra("user_id") );



        listItems =  new ArrayList<>();
        applyButton.setText(intent.getStringExtra("buttonText"));

        if(applyButton.getText().toString().equals("Done")){

            applyLayout.setVisibility(View.INVISIBLE);
            whoisworking.setText("Who was worked on this advert");



        }

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UniSocial> callCreator = apiInterface.getProfileFromUserID(intent.getStringExtra("user_id"));
        callCreator.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {
                nameCreator.setText(response.body().getName() + " " + response.body().getSurname());

                AsyncTask<String, Void, Bitmap> profileTask =  new BitmapTask().execute(response.body().getProfile_photo());

                try {

                    profilePhoto.setImageBitmap(profileTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<UniSocial> call, Throwable t) {

            }
        });



        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(applyButton.getText().equals("Apply")){

                    new AlertDialog.Builder(AdvertPageActivity.this)
                            .setTitle("Are you want to apply this advet ?")
                            .setMessage("Click OK button if you want")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                                    Call<UniSocial> call = apiInterface.getAdvertApply(getApplicationContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),intent.getStringExtra("advert_id"));
                                    call.enqueue(new Callback<UniSocial>() {

                                        @Override
                                        public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                            if(response.body().getMessage().equals("true")){
                                                applyButton.setText("Unapply");



                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UniSocial> call, Throwable t) {
                                        }
                                    });


                                }
                            }).create().show();



                }else if(applyButton.getText().toString().equals("Your Advert")){
                    Toast.makeText(getApplicationContext(),"You can not apply your advert", Toast.LENGTH_SHORT).show();

                }
                else{
                    new AlertDialog.Builder(AdvertPageActivity.this)
                            .setTitle("Are u sure want to unapply this advert ?")
                            .setMessage("Click OK button if you want")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
                                    Call<UniSocial> call = apiInterface.unApply(getApplicationContext().getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.TOKEN,""),intent.getStringExtra("advert_id"));
                                    call.enqueue(new Callback<UniSocial>() {

                                        @Override
                                        public void onResponse(Call<UniSocial> call, retrofit2.Response<UniSocial> response) {

                                            if(response.body().getMessage().equals("true")){
                                                applyButton.setText("Apply");

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UniSocial> call, Throwable t) {
                                        }
                                    });






                                }
                            }).create().show();

                }

            }

        });




        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu




                 PopupMenu popup = new PopupMenu(getApplicationContext(), options);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_advert_page, popup.getMenu());





                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        final int situation;

                        if(item.getTitle().equals("Delete Advert")){

                            situation = 0;

                        }
                        else {

                            situation = 2;

                        }

                        Call<UniSocial> call = apiInterface.updateAdverts(String.valueOf(situation),intent.getStringExtra("advert_id"));
                        call.enqueue(new Callback<UniSocial>() {
                            @Override
                            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                                if(response.body().getMessage().equals("true")){
                                    String message;

                                    if(situation == 0){

                                        message = "Bravo ! You complated your advert";

                                    }
                                    else {
                                        message = "Your advert was deleted succesfully";
                                    }
                                    advertTitle.setText(message);
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);

                                }

                            }

                            @Override
                            public void onFailure(Call<UniSocial> call, Throwable t) {

                            }
                        });

                        return true;
                    }
                });

                popup.show();
            }


        });



        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        Call<UniSocial> call = apiInterface.getAdvertPage(intent.getStringExtra("advert_id"));
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                advertTitle.setText(response.body().getAdvertName());
                discription.setText(response.body().getDescription());
                int numberOfPersonAccepted = Integer.parseInt(response.body().getNumOfPerAccepted());
                System.out.println(numberOfPersonAccepted  + " ");
               // numberOfPerson.setText("" + (response.body().getNumberOfPerson() - numberOfPersonAccepted));
                advertID = response.body().getAdvert_id();
                getApplication().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).edit().putString(Config.CREATOR_ID,response.body().getUser_id()).apply();

                if(getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.CREATOR_ID,"")
                        .equals(getSharedPreferences(Config.APP_NAME,Context.MODE_PRIVATE).getString(Config.USER_ID,""))){






                }
                else {
                   options.setVisibility(View.INVISIBLE);


                }


            }




                @Override
                public void onFailure(Call<UniSocial> call, Throwable t) {

                }
            });
            loadRecyclerData();
    }

    public void loadRecyclerData(){
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

     Call<ArrayList<UniSocial>>call = apiInterface.getWorkingUsers(intent.getStringExtra("advert_id"));
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {
                for (int i = 0; i<response.body().size(); i++){

                    System.out.println(response.body().get(i).name + " " + response.body().get(i).getSurname());
                    System.out.println();

                    RecyclerViewListItemAdvertPage recyclerViewListItemAdvertPage = new RecyclerViewListItemAdvertPage(

                           response.body().get(i).name + " " + response.body().get(i).getSurname(),
                            response.body().get(i).getUser_id(),
                            intent.getStringExtra("advert_id"),
                            getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).getString(Config.CREATOR_ID,""),
                            response.body().get(i).getProfile_photo()

                    );

                    listItems.add(recyclerViewListItemAdvertPage);



                }


                adapter = new RecyclerViewMyAdapterAdvertPage(listItems,getApplicationContext());
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

            }
        });



    }


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
