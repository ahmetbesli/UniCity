package com.ahmetgokhan.unicity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetgokhan.unicity.R;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapter;
import com.ahmetgokhan.unicity.adapters.SubscribeAdapterWithButton;
import com.ahmetgokhan.unicity.overridden.UniSocial;
import com.ahmetgokhan.unicity.retrofit.ApiClient;
import com.ahmetgokhan.unicity.retrofit.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class SubscribeActivityStepThree extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ListView listView;
    String department;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_step_three);
        department = getIntent().getStringExtra("department");

        listView = findViewById(R.id.listViewSubscription3);
        generateListContent();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               TextView text = (TextView)parent.getChildAt(position).findViewById(R.id.listViewText);
               Log.e("text bastıkkkkkk", text.getText().toString());
            }
        });




    }
    private void generateListContent() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses(null,null,department);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {
                Log.e("hello",String.valueOf(response.body().size()));
                for (int i = 0; i < response.body().size(); i++) {

                    data.add(response.body().get(i).getCourses());

                }
                listView.setAdapter(new SubscribeAdapterWithButton(getApplicationContext(),R.layout.list_item_with_button,data));

            }

            @Override
            public void onFailure(Call<ArrayList<UniSocial>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, İnternet bağlantınızı ve lokasyon servisinizi kontrol ediniz", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
