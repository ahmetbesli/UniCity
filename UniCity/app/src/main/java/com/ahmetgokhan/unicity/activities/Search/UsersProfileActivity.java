package com.ahmetgokhan.unicity.activities.Search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.activities.Chat.ChatActivity;
import com.ahmetgokhan.unicity.activities.Homepage.HomeActivity;
import com.ahmetgokhan.unicity.activities.Login.LoginActivity;
import com.ahmetgokhan.unicity.activities.Profile.FullScreenImageActivity;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewAdapterCreated;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewAdapterDone;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewAdapterWorking;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewListItemCreated;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewListItemDone;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewListItemProfile;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewListItemWorking;
import com.ahmetgokhan.unicity.activities.Profile.RecyclerViewMyAdapterProfile;
import com.ahmetgokhan.unicity.activities.Subscribe.SubscribeActivity;
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
import android.widget.Toast;


public class UsersProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView cover_photo;
    CircleImageView profile_photo;
    TextView name_surname, textViewUniversity, textViewDepartmant, subscribedCourses;
    private RecyclerView.Adapter adapterWorking;
    private RecyclerView.Adapter adapterDone;
    ApiInterface apiInterface;
    ImageView go_back_image;

    private String coverPhotoUrl;
    private String profilePhotoUrl;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemCreated> listItems = new ArrayList<>();
    private Intent intent;
    private String username;
    private ImageView createMessageRoom;

    private List<com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemWorking> listItemsWorking = new ArrayList<>();
    private List<com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemDone> listItemsDone = new ArrayList<>();

    RecyclerView recyclerViewWorkingProjects;
    RecyclerView recyclerViewDoneProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);
        checkToken();
        intent = getIntent();
        username = intent.getStringExtra("username");

        createMessageRoom = findViewById(R.id.createMessageRoom);
        createMessageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inttent = new Intent(getApplicationContext(), ChatActivity.class);
                inttent.putExtra("name",name_surname.getText().toString());
                inttent.putExtra("username",username);
                inttent.putExtra("photo",profilePhotoUrl);
                startActivity(inttent);
            }
        });
        textViewUniversity = findViewById(R.id.textViewUniversity);



        name_surname = findViewById(R.id.textViewName);
        cover_photo = findViewById(R.id.cover_photo);
        profile_photo = findViewById(R.id.circleImageView);

        textViewDepartmant = findViewById(R.id.textViewDepartment);
        go_back_image = findViewById(R.id.go_back_home_profile_arrow);
        go_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });


        cover_photo.setOnClickListener(this);
        profile_photo.setOnClickListener(this);


        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);





        TabHost host = findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        host.setBackgroundColor(Color.BLACK);
        TabHost.TabSpec spec = host.newTabSpec("Created Projects");
        spec.setContent(R.id.tab1);

        spec.setIndicator("Created Projects");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Working Projects");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Working Projects");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Done Projects");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Done Projects");
        host.addTab(spec);

        int tabCount = host.getTabWidget().getTabCount();
        for (int i = 0; i < tabCount; i++) {
            final View view = host.getTabWidget().getChildTabViewAt(i);
            if ( view != null ) {

                view.getLayoutParams().height *= 0.96;


                final View textView = view.findViewById(android.R.id.title);
                if ( textView instanceof TextView ) {

                    ((TextView) textView).setGravity(Gravity.CENTER);

                    ((TextView) textView).setSingleLine(false);

                    textView.getLayoutParams().height = 100;
                    textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
            }
        }



        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("Working Projects")) {
                    listItemsWorking.clear();
                    loadRecyclerViewDataWorking();
                } else if (tabId.equals("Done Projects")) {
                    listItemsDone.clear();
                    loadRecyclerViewDataDone();
                }
            }
        });

        recyclerViewWorkingProjects = findViewById(R.id.recyclerViewWorkingProejctsUsers);
        recyclerViewWorkingProjects.setHasFixedSize(true);
        recyclerViewWorkingProjects.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        recyclerViewDoneProjects = findViewById(R.id.recyclerViewDoneProejctsUsers);
        recyclerViewDoneProjects.setHasFixedSize(true);
        recyclerViewDoneProjects.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerViewProjectsUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        loadRecyclerViewData();

        Call<UniSocial> call = apiInterface.getUsersProfile(username);
        call.enqueue(new Callback<UniSocial>() {
            @Override
            public void onResponse(Call<UniSocial> call, Response<UniSocial> response) {

                name_surname.setText(response.body().getName() + " " + response.body().getSurname());
                textViewUniversity.setText(response.body().getUniversity());
                textViewDepartmant.setText(response.body().getDepartment());


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

    public void loadRecyclerViewData() {

        Call<ArrayList<UniSocial>> call = apiInterface.getCreatedProjectsForProfileUsers(username);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemCreated listItem = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemCreated(
                            response.body().get(i).getAdvert_id(),
                            response.body().get(i).getAdvertName(),
                            response.body().get(i).getDescription(),
                            String.valueOf(response.body().get(i).getNumberOfPerson() - Integer.parseInt(response.body().get(i).getNumOfPerAccepted())),
                            response.body().get(i).getAdvertDate(),
                            response.body().get(i).getCourseName(),
                            response.body().get(i).getUser_id()
                    );

                    listItems.add(listItem);

                }

                adapter = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewAdapterCreated(listItems, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {

            }
        });

    }
    public void loadRecyclerViewDataWorking(){

        Call<ArrayList<UniSocial>>call0 = apiInterface.getWorkingProjectsForProfileUsers(username);
        call0.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemWorking listItem = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemWorking(
                            response.body().get(i).getAdvert_id(),
                            response.body().get(i).getAdvertName(),
                            response.body().get(i).getDescription(),
                            String.valueOf(response.body().get(i).getNumberOfPerson() - Integer.parseInt(response.body().get(i).getNumOfPerAccepted())),
                            response.body().get(i).getAdvertDate(),
                            response.body().get(i).getCourseName(),
                            response.body().get(i).getUser_id()
                    );

                    listItemsWorking.add(listItem);

                }

                adapterWorking = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewAdapterWorking(listItemsWorking,getApplicationContext());
                recyclerViewWorkingProjects.setAdapter(adapterWorking);
                adapterWorking.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                Log.e("sdkgjdlkg","sadsadasdsa");

            }
        });

    }
    public void loadRecyclerViewDataDone(){

        Call<ArrayList<UniSocial>>call2 = apiInterface.getDoneProjectsForProfileUsers(username);
        call2.enqueue(new Callback<ArrayList<UniSocial>>() {
            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, Response<ArrayList<UniSocial>> response2) {

                for (int i = 0; i < response2.body().size(); i++) {

                    com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemDone listItem = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewListItemDone(
                            response2.body().get(i).getAdvert_id(),
                            response2.body().get(i).getAdvertName(),
                            response2.body().get(i).getDescription(),
                            response2.body().get(i).getAdvertDate(),
                            response2.body().get(i).getCourseName(),
                            response2.body().get(i).getUser_id()
                    );



                    listItemsDone.add(listItem);

                }

                adapterDone = new com.ahmetgokhan.unicity.activities.Search.RecyclerViewAdapterDone(listItemsDone,getApplicationContext());
                recyclerViewDoneProjects.setAdapter(adapterDone);
                adapterDone.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                Log.e("sdkgjdlkg","sadsadasdsa");

            }
        });

    }
}