package com.ahmetgokhan.unicity.activities.Subscribe;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapter;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;
import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;

public class SubscribeActivity extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        listView = findViewById(R.id.listViewSubscription1);
        Toast.makeText(this, "Lets complate your profile", Toast.LENGTH_SHORT).show();
        generateListContent();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SubscribeActivityStepTwo.class);
                intent.putExtra("faculty",data.get(position));
                startActivity(intent);
            }
        });
    }


    private void generateListContent() {

        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses("Kadir Has Üniversitesi",null,null);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    data.add(response.body().get(i).getFaculty());

                }
                listView.setAdapter(new SubscribeAdapter(getApplicationContext(),R.layout.list_item,data));


            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
