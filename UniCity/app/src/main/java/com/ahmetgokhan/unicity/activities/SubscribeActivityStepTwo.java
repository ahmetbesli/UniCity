package com.ahmetgokhan.unicity.activities;


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

public class SubscribeActivityStepTwo extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<>();
    private ListView listView;
    String faculty;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_step_two);
        faculty = getIntent().getStringExtra("faculty");
        listView = findViewById(R.id.listViewSubscription2);
        generateListContent();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SubscribeActivityStepThree.class);
                intent.putExtra("department",data.get(position));
                startActivity(intent);
            }
        });




    }
    private void generateListContent() {
        ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ArrayList<UniSocial>> call = apiInterface.getCourses(null,faculty,null);
        call.enqueue(new Callback<ArrayList<UniSocial>>() {

            @Override
            public void onResponse(Call<ArrayList<UniSocial>> call, retrofit2.Response<ArrayList<UniSocial>> response) {

                for (int i = 0; i < response.body().size(); i++) {

                    data.add(response.body().get(i).getDepartments());

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
