package com.ahmetgokhan.unicity.activities.ProfileLists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.config.Config;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ProjectsListActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ProjectsListTypeData> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects_list);
        listView = findViewById(R.id.profile_page_projects_list);




        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> callToken = apiInterface.getProjectsList(getApplicationContext().getSharedPreferences(Config.APP_NAME,MODE_PRIVATE).getString(Config.TOKEN,""));
        callToken.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                Log.e("sdgsdgsdITEM",response.body().get(0).getAdvertName());
                    for(int i = 0; i < response.body().size(); i++){
                        ProjectsListTypeData projectsListTypeData = new ProjectsListTypeData(response.body().get(i).getAdvert_id(),response.body().get(i).getAdvertName());
                        arrayList.add(projectsListTypeData);
                    }

                ProjectsListAdapter projectsListAdapter = new ProjectsListAdapter(getApplicationContext(),R.layout.activity_projects_list_item,arrayList);
                listView.setAdapter(projectsListAdapter);



            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                Log.e("sdgsdgsdITEM",t.getMessage());


            }

        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
