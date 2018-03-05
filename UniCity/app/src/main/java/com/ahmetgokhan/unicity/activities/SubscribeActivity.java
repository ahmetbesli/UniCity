package com.ahmetgokhan.unicity.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapter;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;

public class SubscribeActivity extends AppCompatActivity {


    ExpandableListView listView;
    SubscribeAdapter listAdapter;
    private ArrayList<String> facultiesInUniversity;
    private HashMap<String,List<String>> listHash;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        facultiesInUniversity = new ArrayList<>();
        listHash = new HashMap<>();
        listView = findViewById(R.id.lvExp);
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getFaculty("Kadir Has Üniversitesi");
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                System.out.println(response.body().size());
                for (int i = 0; i < response.body().size(); i++) {

                    facultiesInUniversity.add(response.body().get(i).getFaculty());
                }
                initData();
                listAdapter = new SubscribeAdapter(getApplicationContext(), facultiesInUniversity,listHash);
                listView.setAdapter(listAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void initData() {

        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("This is Expandable ListView");

        List<String> androidStudio = new ArrayList<>();
        androidStudio.add("Expandable ListView");
        androidStudio.add("Google Map");
        androidStudio.add("Chat Application");
        androidStudio.add("Firebase ");

        List<String> xamarin = new ArrayList<>();
        xamarin.add("Xamarin Expandable ListView");
        xamarin.add("Xamarin Google Map");
        xamarin.add("Xamarin Chat Application");
        xamarin.add("Xamarin Firebase ");

        List<String> uwp = new ArrayList<>();
        uwp.add("UWP Expandable ListView");
        uwp.add("UWP Google Map");
        uwp.add("UWP Chat Application");
        uwp.add("UWP Firebase ");

        listHash.put(facultiesInUniversity.get(0),edmtDev);
        listHash.put(facultiesInUniversity.get(1),androidStudio);
        listHash.put(facultiesInUniversity.get(2),xamarin);
        listHash.put(facultiesInUniversity.get(3),uwp);
    }
}
